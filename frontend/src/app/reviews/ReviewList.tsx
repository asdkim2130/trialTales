"use client";

import { useEffect, useState } from "react";
import { fetchReviews, deleteReview } from "./reviewService";
import { Review } from "./interfaces";
import ReviewForm from "./ReviewForm";
import styles from "./reviews.module.css";

export default function ReviewList() {
    const [reviews, setReviews] = useState<Review[]>([]);
    const [editingReview, setEditingReview] = useState<Review | null>(null);
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

    return (
        <div className={styles.container}>
            <h2 className={styles.title}>📜 리뷰 목록</h2>
            <ReviewForm review={editingReview} setReviews={setReviews} />
            <div className={styles.grid}>
                {reviews.map((review) => (
                    <div key={review.id} className={styles.reviewBox}>
                        <p className={styles.campaignName}>{review.campaign?.name || "캠페인 없음"}</p>
                        <p className={styles.content}>{review.content}</p>
                        <p className={styles.rating}>⭐ {review.rating}점</p>
                        <p className={styles.date}>🕒 {new Date(review.reviewDate).toLocaleDateString()}</p>
                        <div className={styles.buttonContainer}>
                            <button className={styles.editButton} onClick={() => setEditingReview(review)}>
                                ✏️ 수정
                            </button>
                            <button className={styles.deleteButton} onClick={() => handleDelete(review.id)}>
                                ❌ 삭제
                            </button>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}