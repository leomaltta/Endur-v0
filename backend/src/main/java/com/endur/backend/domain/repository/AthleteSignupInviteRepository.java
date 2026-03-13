package com.endur.backend.domain.repository;

import com.endur.backend.domain.common.enums.InviteStatus;
import com.endur.backend.domain.model.AthleteSignupInvite;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AthleteSignupInviteRepository extends JpaRepository<AthleteSignupInvite, UUID> {

    Optional<AthleteSignupInvite> findByCode(String code);

    Optional<AthleteSignupInvite> findByCodeAndStatus(String code, InviteStatus status);

    boolean existsByCode(String code);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select i from AthleteSignupInvite i where i.code = :code")
    Optional<AthleteSignupInvite> findByCodeForUpdate(@Param("code") String code);
}