package com.endur.backend.domain.repository;

import com.endur.backend.domain.model.AthleteProfile;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AthleteProfileRepository extends JpaRepository<AthleteProfile, UUID> {

    Optional<AthleteProfile> findByUser_Id(UUID userId);
}
