export interface IProperty {
  title: string,
  description: string,
  property_catergory: 'House' | 'Apartment' | 'Room',
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