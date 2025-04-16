-- Drop and recreate the users table with updated fields
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    user_password VARCHAR(100) NOT NULL,
    user_role VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20),
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Existing cars table remains untouched
CREATE TABLE cars (
    car_id SERIAL PRIMARY KEY,
    make VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    year INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    seller_id INT NOT NULL,
    FOREIGN KEY (seller_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Existing space_fleet_memberships table
CREATE TABLE IF NOT EXISTS public.space_fleet_memberships
(
    membership_id SERIAL PRIMARY KEY,
    membership_tier VARCHAR(50) NOT NULL,
    membership_credits INTEGER NOT NULL,
    membership_log TEXT,
    date_registered DATE DEFAULT CURRENT_DATE,
    astronaut_id INTEGER NOT NULL,
    CONSTRAINT space_fleet_memberships_astronaut_fkey FOREIGN KEY (astronaut_id)
    REFERENCES public.astronauts (astronaut_id)
    ON UPDATE NO ACTION
    ON DELETE CASCADE
);

TABLESPACE pg_default;

-- Sample aggregate query for monthly membership revenue
-- This might be adapted later for your gym membership stats
SELECT
    TO_CHAR(date_purchased, 'YYYY-MM') AS month,
    SUM(membership_price) AS total_revenue
FROM public.memberships
GROUP BY month
ORDER BY month;

