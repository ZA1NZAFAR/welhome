export interface IProfile {
  email: string;
  firstName?: string;
  lastName?: string;
  birthDate?: Date;
  phone_number?: string;
  gender?: 'Male' | 'Female' | 'Non-Binary'
  registrationDate?: Date;
}