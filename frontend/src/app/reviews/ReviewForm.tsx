"use client";

import {useState} from "react";
import {createReview, fetchReviews, updateReview} from "./reviewService";
import {Review} from "./interfaces";

export default function ReviewForm({review, setReviews}: {
    review: Review | null;
    setReviews: (reviews: Review[]) => void
}) {
    const [content, setContent] = useState(review?.content || "");
    const [rating, setRating] = useState(review?.rating || 0);
    const [token, setToken] = useState<string>("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (review) {
            await updateReview(review.id, {content, rating}, token);
        } else {
            await createReview({content, rating, campaignId: 1}, token);
        }
        setReviews(await fetchReviews());
        setContent("");
        setRating(0);
    };

    return (
        <form onSubmit={handleSubmit} className="mb-4 p-4 border rounded-lg shadow-lg">
            <h3 className="text-lg font-bold">{review ? "리뷰 수정" : "리뷰 작성"}</h3>
            <textarea className="w-full p-2 border rounded" value={content} onChange={(e) => setContent(e.target.value)}
                      placeholder="리뷰 내용을 입력하세요"/>
            <input className="w-full p-2 border rounded my-2" type="number" value={rating}
                   onChange={(e) => setRating(Number(e.target.value))} placeholder="별점 (1-5)"/>
            <button type="submit">{review ? "수정하기" : "작성하기"}</button>
        </form>
    );
}
