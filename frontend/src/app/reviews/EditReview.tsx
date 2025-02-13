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
            <button onClick={onClose}>취소</button>
        </div>
    );
};

export default EditReview;
