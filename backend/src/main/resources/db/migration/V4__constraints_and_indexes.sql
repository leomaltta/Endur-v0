-- evita 2 vínculos "abertos" para o mesmo par coach-atleta
CREATE UNIQUE INDEX ux_coach_athlete_single_open_link
    ON coach_athlete_links (coach_profile_id, athlete_profile_id)
    WHERE status <> 'ENDED';

-- garante somente 1 vínculo primário aberto por atleta
CREATE UNIQUE INDEX ux_athlete_single_primary_open_link
    ON coach_athlete_links (athlete_profile_id)
    WHERE is_primary = TRUE AND status <> 'ENDED';

ALTER TABLE coach_athlete_links
    ADD CONSTRAINT ck_link_end_date
        CHECK (end_date IS NULL OR end_date >= start_date);

ALTER TABLE athlete_profiles
    ADD CONSTRAINT ck_athlete_hr_values
        CHECK (
            (resting_heart_rate IS NULL OR resting_heart_rate > 0)
                AND (max_heart_rate IS NULL OR max_heart_rate > 0)
                AND (resting_heart_rate IS NULL OR max_heart_rate IS NULL OR resting_heart_rate <= max_heart_rate)
            );
