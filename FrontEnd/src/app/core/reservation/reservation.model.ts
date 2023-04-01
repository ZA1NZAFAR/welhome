import { IGuest } from "../guest/guest.model";
import { IProperty } from "../property/property.model";

export interface IReservation {
    property :  IProperty | undefined,
    guest : IGuest | undefined,
    state : 'En cours' | 'Validé' | 'Refusé' | 'Terminé'|'Annulé',
    nbr_person : number,
    check_in : Date,
    check_out : Date,
    total_price : number


}