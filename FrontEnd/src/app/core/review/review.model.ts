export interface IReview {
  id: number;
  rating: number;
  reviewText: string;
  publishedDate: Date;
  image: string;
  propertyId: number;
  reviewerEmail: string;
}