"use client";

import React, { useState } from "react";
import { useSearchParams, useRouter } from "next/navigation";
import { dummyReviews } from "../dummyReviews";
import { updateReview } from "../reviewService";
import styles from "../reviews.module.css";

const EditReviewPage: React.FC = () => {
    const searchParams = useSearchParams();
    const router = useRouter();
    const reviewId = searchParams.get("reviewId");
    const reviewIndex = dummyReviews.findIndex((r) => r.id === Number(reviewId));

    if (reviewIndex === -1) return <p>리뷰를 찾을 수 없습니다.</p>;

    const [content, setContent] = useState(dummyReviews[reviewIndex].content);
    const [rating, setRating] = useState(dummyReviews[reviewIndex].rating);

    const handleUpdate = () => {
        updateReview(Number(reviewId), content, rating);
        alert("리뷰가 수정되었습니다.");
        router.push("/reviews");
    };

    return (
        <div className={styles["review-box"]}>
            <h1>리뷰 수정</h1>
            <textarea value={content} onChange={(e) => setContent(e.target.value)} />
            <br />
            <label>별점:</label>
            <select value={rating} onChange={(e) => setRating(Number(e.target.value))}>
                {[1, 2, 3, 4, 5].map((num) => (
                    <option key={num} value={num}>{num}점</option>
                ))}
            </select>
            <br />
            <button className={styles["edit-button"]} onClick={handleUpdate}>리뷰 수정 저장</button>
        </div>
    );
};

export default EditReviewPage;
