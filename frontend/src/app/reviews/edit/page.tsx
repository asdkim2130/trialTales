import ReviewForm from "@/app/reviews/ReviewForm";
import styles from "@/app/reviews/reviews.module.css";
import Link from "next/link";

// /reviews/edit?campaginId=13213&reviewId=123132
export default function NewReviewPage() {
  return (
    <div className={styles.container}>
      <div className={"text-black font-extrabold"}>✏️Edit Review📝</div>
      <ReviewForm />
      <Link href="/reviews" className={"text-black font-extrabold"}>
        📜ReviewList
      </Link>
    </div>
  );
}
