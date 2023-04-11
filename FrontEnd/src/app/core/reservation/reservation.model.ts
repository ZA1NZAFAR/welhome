export interface IReservation {
    id: number,
    propertyId :  number,
    renterEmail : string,
    startDate : Date,
    endDate : Date,
    confirmedOwner : boolean,
    confirmedRenter : boolean
}

/**
 * CREATE TABLE reservation
(
    id               BIGSERIAL PRIMARY KEY,
    property_id      BIGSERIAL    NOT NULL,
    renter_email     VARCHAR(100) NOT NULL,
    start_date       DATE         NOT NULL,
    end_date         DATE         NOT NULL,
    confirmed_owner  BOOLEAN      NOT NULL,
    confirmed_renter BOOLEAN      NOT NULL,
    CONSTRAINT reservation_property_fk FOREIGN KEY (property_id) REFERENCES property (id) ON DELETE SET NULL,
    CONSTRAINT reservation_email_fk FOREIGN KEY (renter_email) REFERENCES profile (email) ON DELETE SET NULL,
    CONSTRAINT reservation_dates_ck CHECK (end_date >= start_date)
);
 */