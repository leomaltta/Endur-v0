package com.endur.backend.application.service;

import com.endur.backend.application.exception.BusinessRuleException;
import com.endur.backend.application.exception.ErrorCodes;
import com.endur.backend.domain.common.enums.InviteStatus;
import com.endur.backend.domain.model.AthleteSignupInvite;
import com.endur.backend.domain.model.User;
import com.endur.backend.domain.repository.AthleteSignupInviteRepository;
import java.time.Instant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InviteService {

    private final AthleteSignupInviteRepository inviteRepository;

    public InviteService(AthleteSignupInviteRepository inviteRepository) {
        this.inviteRepository = inviteRepository;
    }

    @Transactional(readOnly = true)
    public AthleteSignupInvite getValidInviteForView(String code) {
        AthleteSignupInvite invite = inviteRepository.findByCode(code)
                .orElseThrow(() -> new BusinessRuleException(
                        ErrorCodes.INVITE_NOT_FOUND,
                        "Invite code was not found."
                ));

        ensureInviteIsUsable(invite);
        return invite;
    }

    @Transactional
    public AthleteSignupInvite getValidInviteForConsumption(String code) {
        AthleteSignupInvite invite = inviteRepository.findByCodeForUpdate(code)
                .orElseThrow(() -> new BusinessRuleException(
                        ErrorCodes.INVITE_NOT_FOUND,
                        "Invite code was not found."
                ));

        if (isExpired(invite)) {
            invite.setStatus(InviteStatus.EXPIRED);
            inviteRepository.save(invite);
            throw new BusinessRuleException(
                    ErrorCodes.INVITE_EXPIRED,
                    "Invite is expired."
            );
        }

        ensureInviteStatusIsUsable(invite.getStatus());
        return invite;
    }

    @Transactional
    public AthleteSignupInvite markAsAccepted(AthleteSignupInvite invite, User usedByUser) {
        if (isExpired(invite)) {
            invite.setStatus(InviteStatus.EXPIRED);
            inviteRepository.save(invite);
            throw new BusinessRuleException(
                    ErrorCodes.INVITE_EXPIRED,
                    "Invite is expired."
            );
        }

        ensureInviteStatusIsUsable(invite.getStatus());
        invite.setStatus(InviteStatus.ACCEPTED);
        invite.setUsedByUser(usedByUser);
        return inviteRepository.save(invite);
    }

    private void ensureInviteIsUsable(AthleteSignupInvite invite) {
        if (isExpired(invite)) {
            throw new BusinessRuleException(
                    ErrorCodes.INVITE_EXPIRED,
                    "Invite is expired."
            );
        }
        ensureInviteStatusIsUsable(invite.getStatus());
    }

    private void ensureInviteStatusIsUsable(InviteStatus status) {
        if (status == InviteStatus.ACCEPTED) {
            throw new BusinessRuleException(
                    ErrorCodes.INVITE_ALREADY_USED,
                    "Invite was already used."
            );
        }

        if (status != InviteStatus.PENDING) {
            throw new BusinessRuleException(
                    ErrorCodes.INVITE_NOT_AVAILABLE,
                    "Invite is not available."
            );
        }
    }

    private boolean isExpired(AthleteSignupInvite invite) {
        return invite.getExpiresAt() != null && invite.getExpiresAt().isBefore(Instant.now());
    }
}
