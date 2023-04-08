export interface IProperty {
  id:number,
  title: string,
  description: string,
  property_category: PropertyCategory,
  address: string,
  city: string,
  state?: string,
  country: string,
  price: number,
  surface_area: number,
  floors: number,
  capacity: number,
  construction_date?: Date,
  publish_date?: Date,
  owner_email: string,
  image_url?: string

}

export const propertyCategory = ['House', 'Apartment', 'Room'];
export type PropertyCategory = typeof propertyCategory[number];

/**
 * CREATE TABLE property
(
    id                BIGSERIAL PRIMARY KEY,
    title             VARCHAR(200)   NOT NULL,
    description       VARCHAR(500)   NOT NULL,
    property_category VARCHAR(100)   NOT NULL CHECK (property_category IN ('House', 'Apartment', 'Room')),
    address           VARCHAR(200)   NOT NULL,
    city              VARCHAR(50)    NOT NULL,
    state             VARCHAR(50),
    country           VARCHAR(50)    NOT NULL,
    price             NUMERIC(10, 2) NOT NULL CHECK (price > 0),
    surface_area      REAL           NOT NULL CHECK (surface_area > 0),
    floors            INT            NOT NULL,
    capacity          INT            NOT NULL CHECK (capacity > 0),
    construction_date DATE CHECK (construction_date <= CURRENT_DATE),
    publish_date      DATE DEFAULT CURRENT_DATE,
    owner_email       VARCHAR(100)   NOT NULL,
    image_url         VARCHAR(1500),
    CONSTRAINT property_email_fk FOREIGN KEY (owner_email) REFERENCES profile (email) ON DELETE CASCADE
);
 */