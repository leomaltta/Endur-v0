CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       email CITEXT NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       role user_role NOT NULL,
                       status user_status NOT NULL DEFAULT 'ACTIVE',
                       full_name VARCHAR(150) NOT NULL,
                       phone VARCHAR(30),
                       created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                       updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE coach_profiles (
                                id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                user_id UUID NOT NULL UNIQUE REFERENCES users(id),
                                public_code VARCHAR(30) NOT NULL UNIQUE,
                                display_name VARCHAR(150),
                                bio TEXT,
                                is_accepting_new_athletes BOOLEAN NOT NULL DEFAULT TRUE,
                                created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                                updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE athlete_profiles (
                                  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                  user_id UUID NOT NULL UNIQUE REFERENCES users(id),
                                  status athlete_status NOT NULL DEFAULT 'ACTIVE',
                                  height_cm NUMERIC(6,2),
                                  weight_kg NUMERIC(6,2),
                                  program_type athlete_program_type, -- nullable até concluir onboarding
                                  resting_heart_rate INT,
                                  max_heart_rate INT,
                                  notes TEXT,
                                  onboarding_completed BOOLEAN NOT NULL DEFAULT FALSE,
                                  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                                  updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
