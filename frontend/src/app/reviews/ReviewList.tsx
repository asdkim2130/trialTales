"use client";

import { useEffect, useState } from "react";
import { fetchReviews, deleteReview } from "./reviewService";
import { Review } from "./interfaces";
import ReviewForm from "./ReviewForm";
import styles from "./reviews.module.css";
import Link from "next/link";
export default function ReviewList(props: { reviews: Review[] }) {
  const [reviews, setReviews] = useState<Review[]>([]);
  const [token, setToken] = useState<string>("");

  useEffect(() => {
    async function loadReviews() {
      const data = await fetchReviews();
      setReviews(data);
    }
    loadReviews();
  }, []);

  const handleDelete = async (reviewId: number) => {
    await deleteReview(reviewId, token);
    setReviews(reviews.filter((review) => review.id !== reviewId));
  };

  // const reviewList = await fetchReviews();

  return (
    <div className={styles.container}>
      <h2 className={styles.title}>📜ReviewList</h2>
      <div className={styles.grid}>
        {props.reviews.map((review) => (
          <div key={review.id} className={styles.reviewBox}>
            <p className={styles.campaignName}>
              {review.campaign?.name || "캠페인 없음"}
            </p>
            <p className={styles.content}>{review.content}</p>
            <p className={styles.rating}>⭐ {review.rating}점</p>
            <p className={styles.date}>
              🕒 {new Date(review.reviewDate).toLocaleDateString()}
            </p>
            <div className={styles.buttonContainer}>
              <Link href="/reviews/edit">
                <button className={styles.editButton}>✏️ 수정</button>
              </Link>
              <button
                className={styles.deleteButton}
                onClick={() => handleDelete(review.id)}
              >
                ❌ 삭제
              </button>
            </div>
            {/*<ReviewUpdateDeleteButtons*/}
            {/*  review={review}*/}
            {/*  setRevies={setReviews}*/}
            {/*  setEditingReview={setEditingReview}*/}
            {/*/>*/}
          </div>
        ))}
      </div>
    </div>
  );
}
