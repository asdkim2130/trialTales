export interface Review {
  id: number;
  userId: string;
  content: string;
  rating: number;
  reviewDate: string;
  updatedAt: string;
  campaign: {
    id: number;
    name: string;
  };
}
