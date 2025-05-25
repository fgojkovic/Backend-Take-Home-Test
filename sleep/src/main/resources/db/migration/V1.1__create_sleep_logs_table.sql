CREATE TABLE sleep_logs (
    id SERIAL PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    sleep_date DATE NOT NULL,
    time_in_bed_start TIMESTAMP NOT NULL,
    time_in_bed_end TIMESTAMP NOT NULL,
    total_time_in_bed INTEGER NOT NULL,
    morning_feeling VARCHAR(10) NOT NULL CHECK (morning_feeling IN ('BAD', 'OK', 'GOOD')),
    CONSTRAINT unique_sleep_log_per_user_date UNIQUE (user_id, sleep_date)
); 