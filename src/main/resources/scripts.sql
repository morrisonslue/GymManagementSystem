-- drop old tables
DROP TABLE IF EXISTS memberships;

DROP TABLE IF EXISTS workout_classes;

DROP TABLE IF EXISTS users;

-- user table
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

-- workout classes table
CREATE TABLE workout_classes (
    id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    description TEXT,
    trainer_id INT NOT NULL,
    FOREIGN KEY (trainer_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- membership table
CREATE TABLE memberships (
    membership_id SERIAL PRIMARY KEY,
    membership_type VARCHAR(50) NOT NULL,
    membership_description TEXT,
    membership_price DECIMAL(8, 2) NOT NULL,
    user_id INT NOT NULL,
    purchased_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- sample users 
INSERT INTO
    users (
        username,
        user_password,
        user_role,
        email,
        phone_number,
        address
    )
VALUES
    (
        'bossman',
        '$2a$10$HashedExample1',
        'Admin',
        'bossman@gymcorp.com',
        '709-111-1111',
        '100 Gym Blvd'
    ),
    (
        'beastmode',
        '$2a$10$HashedExample2',
        'Trainer',
        'dwayne@ironhaven.com',
        '709-222-2222',
        '456 Dumbbell Drive'
    ),
    (
        'zenmaster',
        '$2a$10$HashedExample3',
        'Trainer',
        'li.yoga@flowzone.com',
        '709-333-3333',
        '789 Lois Lane'
    ),
    (
        'gainsqueen',
        '$2a$10$HashedExample4',
        'Member',
        'lola.lifts@gmail.com',
        '709-444-4444',
        '23 Deadlift Dr'
    ),
    (
        'cardioking',
        '$2a$10$HashedExample5',
        'Member',
        'tommy.runner@yahoo.com',
        '709-555-5555',
        '55 Sprint St'
    ),
    (
        'corecrusher',
        '$2a$10$HashedExample6',
        'Member',
        'abby.abs@abmail.net',
        '709-666-6666',
        '6 Plank Path'
    );

-- sample workout classes
INSERT INTO
    workout_classes (type, description, trainer_id)
VALUES
    (
        'Power Pump',
        'Heavy lifting for strength and mass',
        2
    ),
    (
        'Zen Flow',
        'Slow-paced yoga to improve flexibility',
        3
    ),
    (
        'Bootcamp Blitz',
        'Full body circuit with battle ropes',
        2
    ),
    (
        'Stretch & Reset',
        'Deep stretching and breath work',
        3
    ),
    (
        'Abs Inferno',
        'High intensity ab-focused burn',
        2
    );

-- sample memberships
INSERT INTO
    memberships (
        membership_type,
        membership_description,
        membership_price,
        user_id
    )
VALUES
    (
        'Elite',
        'Unlimited classes, VIP lounge, personal locker',
        99.99,
        4
    ),
    (
        'Sweat Pass',
        '5 classes per week and sauna access',
        59.99,
        5
    ),
    (
        'Flex Fit',
        'Drop-in 2x/week and 1 guest pass/month',
        39.99,
        6
    ),
    (
        'Elite',
        'Same plan as GainsQueen',
        99.99,
        5
    ),
    (
        'Weekend Warrior',
        'Sat-Sun access + 1 weekday class',
        29.99,
        6
    );