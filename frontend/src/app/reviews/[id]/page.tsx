"use client";
import React from "react";
import { useSearchParams } from "next/navigation";
import { dummyReviews } from "../dummyReviews";

const ReviewDetailPage: React.FC = () => {
    const searchParams = useSearchParams();
    const reviewId = searchParams.get("reviewId");
    const review = dummyReviews.find((r) => r.id === Number(reviewId));

    if (!review) return <p>리뷰를 찾을 수 없습니다.</p>;

    return (
        <div>
            <h1>리뷰 상세</h1>
            <h2>{review.userId}</h2>
            <p>{review.content}</p>
            <small>⭐ {review.rating}점</small>
            <p>작성 날짜: {review.reviewDate}</p>
            <p>수정된 날짜: {review.updatedAt ? review.updatedAt : "수정되지 않음"}</p>
        </div>
    );
};

export default ReviewDetailPage;