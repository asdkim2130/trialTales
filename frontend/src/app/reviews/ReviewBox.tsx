"use client";

import React, { useState } from "react";
import { Review } from "./interfaces";
import { useRouter } from "next/navigation";
import styles from "./reviews.module.css";
import EditReview from "./EditReview";

interface ReviewBoxProps {
    review: Review;
    onDelete: (id: number) => void;
}

const ReviewBox: React.FC<ReviewBoxProps> = ({ review, onDelete }) => {
    const router = useRouter();
    const [isEditing, setIsEditing] = useState(false);

    const handleDelete = () => {
        onDelete(review.id);
        router.push("/reviews"); // 삭제 후 리뷰 목록으로 이동
    };

    // 별점 변환: 숫자를 별 이모티콘으로 변경
    const renderStars = (rating: number) => "⭐".repeat(rating);

    return (
        <div className={styles["review-box"]}>
            <h3>{review.userId}</h3>
            <p className={styles["review-content"]}>{review.content}</p>
            <small>{renderStars(review.rating)}</small>
            <div className={styles["button-container"]}>
                <button className={styles["edit-button"]} onClick={() => setIsEditing(true)}>수정</button>
                <button className={styles["delete-button"]} onClick={handleDelete}>삭제</button>
            </div>
            {isEditing && <EditReview review={review} onClose={() => setIsEditing(false)} />}
        </div>
    );
};

export default ReviewBox;
