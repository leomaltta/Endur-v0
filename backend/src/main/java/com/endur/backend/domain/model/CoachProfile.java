package com.endur.backend.domain.model;

import com.endur.backend.domain.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "coach_profiles")
public class CoachProfile extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "public_code", nullable = false, unique = true, length = 30)
    private String publicCode;

    @Column(name = "display_name", length = 150)
    private String displayName;

    @Column(name = "bio", columnDefinition = "text")
    private String bio;

    @Column(name = "is_accepting_new_athletes", nullable = false)
    private boolean isAcceptingNewAthletes = true;

    protected CoachProfile() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPublicCode() {
        return publicCode;
    }

    public void setPublicCode(String publicCode) {
        this.publicCode = publicCode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public boolean isAcceptingNewAthletes() {
        return isAcceptingNewAthletes;
    }

    public void setAcceptingNewAthletes(boolean acceptingNewAthletes) {
        isAcceptingNewAthletes = acceptingNewAthletes;
    }
}
