import {dummyReviews} from "@/app/reviews/dummyReviews";

export const updateReview = (id: number, content: string, rating: number) => {
  const reviewIndex = dummyReviews.findIndex((r) => r.id === id);
  if (reviewIndex !== -1) {
    dummyReviews[reviewIndex].content = content;
    dummyReviews[reviewIndex].rating = rating;
    dummyReviews[reviewIndex].updatedAt = new Date().toISOString();
  }
};
