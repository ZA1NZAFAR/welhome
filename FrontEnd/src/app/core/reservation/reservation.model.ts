export interface IReservation {
    id: number,
    propertyId :  number,
    renterEmail : string,
    startDate : Date,
    endDate : Date,
    confirmedOwner : boolean,
    confirmedRenter : boolean,
    totalPrice : number
}