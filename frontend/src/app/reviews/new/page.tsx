import ReviewForm from "@/app/reviews/ReviewForm";
import styles from "@/app/reviews/reviews.module.css";
import Link from "next/link";

export default function NewReviewPage() {
  return (
    <div className={styles.container}>
      <div className={"text-black font-extrabold"}>✏️New Review📝</div>
      <ReviewForm />
    </div>
  );
}
