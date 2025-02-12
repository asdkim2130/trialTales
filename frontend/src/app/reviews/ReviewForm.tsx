"use client";

import { useState } from "react";
import { createReview, fetchReviews, updateReview } from "./reviewService";
import { Review } from "./interfaces";
import Link from "next/link";

export default function ReviewForm({
  review,
  setReviews,
  campaignId,
  reviewId,
}: {
  review: Review | null;
  setReviews: (reviews: Review[]) => void;
  campaignId?: string | string[];
  reviewId?: string | string[];
}) {
  const [content, setContent] = useState(review?.content || "");
  const [rating, setRating] = useState(review?.rating || 0);
  const [token, setToken] = useState<string>("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (review) {
      await updateReview(review.id, { content, rating }, token);
    } else {
      await createReview({ content, rating, campaignId: 1 }, token);
    }
    setReviews(await fetchReviews());
    setContent("");
    setRating(0);
  };

  return (
    <form
      onSubmit={handleSubmit}
      className="mb-4 p-4 border rounded-lg shadow-lg"
    >
      <h3 className="text-lg font-bold text-black ">
        {review ? "📜Review" : "📜Review"}
      </h3>
      <div className="flex flex-col gap-2">
        <textarea
          className="w-full max-w-[800px] p-2 border rounded min-h-[300px] resize-y text-black"
          value={content}
          onChange={(e) => setContent(e.target.value)}
          placeholder="리뷰 내용을 입력하세요"
          rows={20}
        />
        <input
          className="w-full p-2 border rounded my-2 text-black"
          type="number"
          value={rating}
          onChange={(e) => setRating(Number(e.target.value))}
          placeholder="별점 (1-5)"
        />
        <Link href="/reviews">
          <button
            type="submit"
            className="bg-gray-200 hover:bg-gray-300 text-black font-extrabold px-6 py-2 rounded shadow-md transition"
          >
            {review ? "수정완료" : "작성하기"}
          </button>
        </Link>
      </div>
    </form>
  );
}
