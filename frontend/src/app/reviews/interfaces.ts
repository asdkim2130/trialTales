export interface Review {
    id: number;
    userId: number;
    content: string;
    rating: number;
    reviewDate: string;
    updatedAt: string;
    campaign: {
        id: number;
        name: string;
    };
}
