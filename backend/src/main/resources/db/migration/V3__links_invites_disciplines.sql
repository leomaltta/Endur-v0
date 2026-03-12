CREATE TABLE coach_athlete_links (
                                     id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                     coach_profile_id UUID NOT NULL REFERENCES coach_profiles(id),
                                     athlete_profile_id UUID NOT NULL REFERENCES athlete_profiles(id),
                                     status coach_athlete_status NOT NULL DEFAULT 'ACTIVE',
                                     is_primary BOOLEAN NOT NULL DEFAULT TRUE,
                                     start_date DATE NOT NULL DEFAULT CURRENT_DATE,
                                     end_date DATE,
                                     notes TEXT,
                                     created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                                     updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE athlete_signup_invites (
                                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                        coach_profile_id UUID NOT NULL REFERENCES coach_profiles(id),
                                        code VARCHAR(50) NOT NULL UNIQUE,
                                        athlete_email CITEXT,
                                        expires_at TIMESTAMPTZ,
                                        status invite_status NOT NULL DEFAULT 'PENDING',
                                        used_by_user_id UUID REFERENCES users(id),
                                        created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                                        updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE athlete_disciplines (
                                     id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                     athlete_profile_id UUID NOT NULL REFERENCES athlete_profiles(id),
                                     discipline discipline_type NOT NULL,
                                     created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                                     CONSTRAINT ux_athlete_discipline UNIQUE (athlete_profile_id, discipline)
);
