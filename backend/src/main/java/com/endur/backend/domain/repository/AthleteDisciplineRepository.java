package com.endur.backend.domain.repository;

import com.endur.backend.domain.common.enums.DisciplineType;
import com.endur.backend.domain.model.AthleteDiscipline;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AthleteDisciplineRepository extends JpaRepository<AthleteDiscipline, UUID> {

    List<AthleteDiscipline> findAllByAthleteProfile_Id(UUID athleteProfileId);

    boolean existsByAthleteProfile_IdAndDiscipline(UUID athleteProfileId, DisciplineType discipline);

    void deleteAllByAthleteProfile_Id(UUID athleteProfileId);
}
