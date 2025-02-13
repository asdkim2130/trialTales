"use client";
import React, { useState } from "react";
import { Review } from "./interfaces";
import styles from "./reviews.module.css";

interface EditReviewProps {
    review: Review;
    onClose: () => void;
}

const EditReview: React.FC<EditReviewProps> = ({ review, onClose }) => {
    const [content, setContent] = useState(review.content);
    const [rating, setRating] = useState(review.rating);

    const handleUpdate = () => {
        review.content = content;
        review.rating = rating;
        review.updatedAt = new Date().toISOString(); // 자동 업데이트
        alert("리뷰가 수정되었습니다.");
        onClose();
    };

    return (
        <div className={styles["modal-overlay"]}>
            <div className={styles["modal-content"]}>
                <h1 className={styles["modal-title"]}>리뷰 수정</h1>
                <textarea
                    value={content}
                    onChange={(e) => setContent(e.target.value)}
                    maxLength={100}
                    className={styles["review-textarea"]}
                />
                <br />
                <label>별점:</label>
                <select
                    value={rating}
                    onChange={(e) => setRating(Number(e.target.value))}
                >
                    {[1, 2, 3, 4, 5].map((num) => (
                        <option key={num} value={num}>{"⭐".repeat(num)}</option>
                    ))}
                </select>
                <br />
                <div className={styles["button-container"]}>
                    <button className={styles["edit-button"]} onClick={handleUpdate}>저장</button>
                    <button className={styles["cancel-button"]} onClick={onClose}>취소</button>
                </div>
            </div>
        </div>
    );
};

export default EditReview;
