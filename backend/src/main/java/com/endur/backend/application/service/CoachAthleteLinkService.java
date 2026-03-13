package com.endur.backend.application.service;

import com.endur.backend.application.exception.BusinessRuleException;
import com.endur.backend.application.exception.ErrorCodes;
import com.endur.backend.domain.common.enums.CoachAthleteStatus;
import com.endur.backend.domain.model.AthleteProfile;
import com.endur.backend.domain.model.CoachAthleteLink;
import com.endur.backend.domain.model.CoachProfile;
import com.endur.backend.domain.repository.CoachAthleteLinkRepository;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CoachAthleteLinkService {

    private final CoachAthleteLinkRepository coachAthleteLinkRepository;

    public CoachAthleteLinkService(CoachAthleteLinkRepository coachAthleteLinkRepository) {
        this.coachAthleteLinkRepository = coachAthleteLinkRepository;
    }

    @Transactional
    public CoachAthleteLink createPrimaryActiveLink(
            CoachProfile coachProfile,
            AthleteProfile athleteProfile,
            LocalDate startDate,
            String notes
    ) {
        if (!coachProfile.isAcceptingNewAthletes()) {
            throw new BusinessRuleException(
                    ErrorCodes.COACH_NOT_ACCEPTING_NEW_ATHLETES,
                    "Coach is not accepting new athletes."
            );
        }

        boolean activeLinkAlreadyExists = coachAthleteLinkRepository
                .findByCoachProfile_IdAndAthleteProfile_IdAndStatusNot(
                        coachProfile.getId(),
                        athleteProfile.getId(),
                        CoachAthleteStatus.ENDED
                )
                .isPresent();

        if (activeLinkAlreadyExists) {
            throw new BusinessRuleException(
                    ErrorCodes.ACTIVE_LINK_ALREADY_EXISTS,
                    "An active coach-athlete link already exists for this pair."
            );
        }

        boolean athleteAlreadyHasPrimary = coachAthleteLinkRepository
                .existsByAthleteProfile_IdAndIsPrimaryTrueAndStatusNot(
                        athleteProfile.getId(),
                        CoachAthleteStatus.ENDED
                );

        if (athleteAlreadyHasPrimary) {
            throw new BusinessRuleException(
                    ErrorCodes.PRIMARY_LINK_ALREADY_EXISTS,
                    "Athlete already has a primary active coach."
            );
        }

        LocalDate effectiveStartDate = startDate != null ? startDate : LocalDate.now();

        CoachAthleteLink link = CoachAthleteLink.newActivePrimary(
                coachProfile,
                athleteProfile,
                effectiveStartDate,
                notes
        );

        return coachAthleteLinkRepository.save(link);
    }
}
