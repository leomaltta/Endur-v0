package com.endur.backend.domain.repository;

import com.endur.backend.domain.common.enums.CoachAthleteStatus;
import com.endur.backend.domain.model.CoachAthleteLink;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachAthleteLinkRepository extends JpaRepository<CoachAthleteLink, UUID> {

    List<CoachAthleteLink> findAllByCoachProfile_IdAndStatus(UUID coachProfileId, CoachAthleteStatus status);

    Optional<CoachAthleteLink> findByCoachProfile_IdAndAthleteProfile_IdAndStatusNot(
            UUID coachProfileId,
            UUID athleteProfileId,
            CoachAthleteStatus status
    );

    boolean existsByAthleteProfile_IdAndIsPrimaryTrueAndStatusNot(UUID athleteProfileId, CoachAthleteStatus status);
}
