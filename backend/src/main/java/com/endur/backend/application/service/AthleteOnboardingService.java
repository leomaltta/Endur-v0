package com.endur.backend.application.service;

import com.endur.backend.application.exception.BusinessRuleException;
import com.endur.backend.application.exception.ErrorCodes;
import com.endur.backend.domain.common.enums.AthleteProgramType;
import com.endur.backend.domain.common.enums.DisciplineType;
import com.endur.backend.domain.model.AthleteDiscipline;
import com.endur.backend.domain.model.AthleteProfile;
import com.endur.backend.domain.repository.AthleteDisciplineRepository;
import com.endur.backend.domain.repository.AthleteProfileRepository;
import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AthleteOnboardingService {

    private static final Set<DisciplineType> TRIATHLON_REQUIRED_DISCIPLINES =
            EnumSet.of(DisciplineType.RUN, DisciplineType.BIKE, DisciplineType.SWIM);

    private final AthleteProfileRepository athleteProfileRepository;
    private final AthleteDisciplineRepository athleteDisciplineRepository;

    public AthleteOnboardingService(
            AthleteProfileRepository athleteProfileRepository,
            AthleteDisciplineRepository athleteDisciplineRepository
    ) {
        this.athleteProfileRepository = athleteProfileRepository;
        this.athleteDisciplineRepository = athleteDisciplineRepository;
    }

    @Transactional
    public AthleteProfile completeOnboarding(
            UUID athleteProfileId,
            AthleteProgramType programType,
            Set<DisciplineType> disciplines,
            BigDecimal heightCm,
            BigDecimal weightKg,
            Integer restingHeartRate,
            Integer maxHeartRate,
            String notes
    ) {
        AthleteProfile athleteProfile = athleteProfileRepository.findById(athleteProfileId)
                .orElseThrow(() -> new BusinessRuleException(
                        ErrorCodes.ATHLETE_PROFILE_NOT_FOUND,
                        "Athlete profile was not found."
                ));

        if (programType == null) {
            throw new BusinessRuleException(
                    ErrorCodes.PROGRAM_TYPE_REQUIRED,
                    "Program type is required."
            );
        }

        if (disciplines == null || disciplines.isEmpty()) {
            throw new BusinessRuleException(
                    ErrorCodes.DISCIPLINES_REQUIRED,
                    "At least one discipline is required."
            );
        }

        Set<DisciplineType> normalizedDisciplines = EnumSet.copyOf(disciplines);
        ensureTriathlonConsistency(programType, normalizedDisciplines);

        athleteProfile.setProgramType(programType);
        athleteProfile.setHeightCm(heightCm);
        athleteProfile.setWeightKg(weightKg);
        athleteProfile.setRestingHeartRate(restingHeartRate);
        athleteProfile.setMaxHeartRate(maxHeartRate);
        athleteProfile.setNotes(notes);
        athleteProfile.setOnboardingCompleted(true);

        AthleteProfile savedProfile = athleteProfileRepository.save(athleteProfile);
        replaceDisciplines(savedProfile, normalizedDisciplines);

        return savedProfile;
    }

    private void ensureTriathlonConsistency(AthleteProgramType programType, Set<DisciplineType> disciplines) {
        if (programType == AthleteProgramType.TRIATHLON
                && !disciplines.containsAll(TRIATHLON_REQUIRED_DISCIPLINES)) {
            throw new BusinessRuleException(
                    ErrorCodes.TRIATHLON_DISCIPLINES_REQUIRED,
                    "TRIATHLON program requires RUN, BIKE and SWIM disciplines."
            );
        }
    }

    private void replaceDisciplines(AthleteProfile athleteProfile, Set<DisciplineType> disciplines) {
        athleteDisciplineRepository.deleteAllByAthleteProfile_Id(athleteProfile.getId());

        Set<AthleteDiscipline> athleteDisciplines = disciplines.stream()
                .map(discipline -> AthleteDiscipline.of(athleteProfile, discipline))
                .collect(Collectors.toSet());

        athleteDisciplineRepository.saveAll(athleteDisciplines);
    }
}
