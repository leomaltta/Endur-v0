package com.endur.backend.domain.model;

import com.endur.backend.domain.common.BaseEntity;
import com.endur.backend.domain.common.enums.CoachAthleteStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "coach_athlete_links")
public class CoachAthleteLink extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "coach_profile_id", nullable = false)
    private CoachProfile coachProfile;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "athlete_profile_id", nullable = false)
    private AthleteProfile athleteProfile;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status", nullable = false, columnDefinition = "coach_athlete_status")
    private CoachAthleteStatus status = CoachAthleteStatus.ACTIVE;

    @Column(name = "is_primary", nullable = false)
    private boolean isPrimary = true;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate = LocalDate.now();

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "notes", columnDefinition = "text")
    private String notes;

    protected CoachAthleteLink() {
    }

    public static CoachAthleteLink newActivePrimary(
            CoachProfile coachProfile,
            AthleteProfile athleteProfile,
            LocalDate startDate,
            String notes
    ) {
        CoachAthleteLink link = new CoachAthleteLink();
        link.setCoachProfile(coachProfile);
        link.setAthleteProfile(athleteProfile);
        link.setStatus(CoachAthleteStatus.ACTIVE);
        link.setPrimary(true);
        link.setStartDate(startDate);
        link.setNotes(notes);
        return link;
    }

    public CoachProfile getCoachProfile() {
        return coachProfile;
    }

    public void setCoachProfile(CoachProfile coachProfile) {
        this.coachProfile = coachProfile;
    }

    public AthleteProfile getAthleteProfile() {
        return athleteProfile;
    }

    public void setAthleteProfile(AthleteProfile athleteProfile) {
        this.athleteProfile = athleteProfile;
    }

    public CoachAthleteStatus getStatus() {
        return status;
    }

    public void setStatus(CoachAthleteStatus status) {
        this.status = status;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
