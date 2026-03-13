package com.endur.backend.domain.repository;

import com.endur.backend.domain.model.CoachProfile;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachProfileRepository extends JpaRepository<CoachProfile, UUID> {

    Optional<CoachProfile> findByUser_Id(UUID userId);

    Optional<CoachProfile> findByPublicCode(String publicCode);

    boolean existsByPublicCode(String publicCode);
}
