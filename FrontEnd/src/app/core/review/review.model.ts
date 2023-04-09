export interface IReview {
  id: number;
  rating: number;
  review_text: string;
  published_date: Date;
  image: string;
  property_id: number;
  reviewer_email: string;
}

export interface IRating {
  rating: number;
  reviewCount: number; 
}