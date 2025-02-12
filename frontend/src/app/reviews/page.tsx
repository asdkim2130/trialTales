import ReviewList from "./ReviewList";
import { fetchReviews } from "@/app/reviews/reviewService";

export default async function ReviewsPage() {
  const reviews = await fetchReviews();

  return (
    <div>
      {reviews.map((r) => (
        <div></div>
      ))}
      <ReviewList reviews={reviews} />
    </div>
  );
}
