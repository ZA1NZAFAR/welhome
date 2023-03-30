import { JwtPayload } from 'jwt-decode'

export interface IProfile {
  email: string;
  first_name: string;
  last_name: string;
  birth_date: Date;
  phone_number: string;
  gender: 'Male' | 'Female' | 'Non-Binary'
  registration_date?: Date;
}

export type ITokenPayload = IProfile & JwtPayload;