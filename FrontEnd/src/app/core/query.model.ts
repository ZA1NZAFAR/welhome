import { IProfile } from "./auth/auth.model";
import { IProperty } from "./property/property.model";
import { IReservation } from "./reservation/reservation.model";

export interface IQuery{
  owner: IProfile,
  properties: IProperty[],
  bookings: IReservation[],
}