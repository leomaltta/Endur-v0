package com.endur.backend.domain.model;

import com.endur.backend.domain.common.CreatedEntity;
import com.endur.backend.domain.common.enums.DisciplineType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(
        name = "athlete_disciplines",
        uniqueConstraints = @UniqueConstraint(
                name = "ux_athlete_discipline",
                columnNames = {"athlete_profile_id", "discipline"}
        )
)
public class AthleteDiscipline extends CreatedEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "athlete_profile_id", nullable = false)
    private AthleteProfile athleteProfile;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "discipline", nullable = false, columnDefinition = "discipline_type")
    private DisciplineType discipline;

    protected AthleteDiscipline() {
    }

    public static AthleteDiscipline of(AthleteProfile athleteProfile, DisciplineType discipline) {
        AthleteDiscipline athleteDiscipline = new AthleteDiscipline();
        athleteDiscipline.setAthleteProfile(athleteProfile);
        athleteDiscipline.setDiscipline(discipline);
        return athleteDiscipline;
    }

    public AthleteProfile getAthleteProfile() {
        return athleteProfile;
    }

    public void setAthleteProfile(AthleteProfile athleteProfile) {
        this.athleteProfile = athleteProfile;
    }

    public DisciplineType getDiscipline() {
        return discipline;
    }

    public void setDiscipline(DisciplineType discipline) {
        this.discipline = discipline;
    }
}
