export interface IProfile {
  email: string;
  firstName?: string;
  lastName?: string;
  birthDate?: Date;
  phone_number?: string;
  gender?: 'Male' | 'Female' | 'Non-Binary'
  registrationDate?: Date;
}

/**
 * CREATE TABLE profile
(
    email             VARCHAR(100) UNIQUE NOT NULL PRIMARY KEY,
    first_name        VARCHAR(50)         NOT NULL,
    last_name         VARCHAR(50)         NOT NULL,
    birth_date        DATE                NOT NULL,
    phone_number      VARCHAR(20)         NOT NULL,
    gender            VARCHAR(20)         NOT NULL CHECK (gender IN ('Male', 'Female', 'Non-Binary')),
    registration_date DATE DEFAULT CURRENT_DATE
);
 */