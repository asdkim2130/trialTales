"use client";

import React, { useState, useEffect } from "react";
import { Review } from "./interfaces";
import ReviewBox from "./ReviewBox";
import { dummyReviews } from "./dummyReviews";
import { useSearchParams } from 'next/navigation';

const ReviewList: React.FC = () => {
  const searchParams = useSearchParams();
  const campaignId = searchParams.get('campaignId');
  const [reviews, setReviews] = useState<Review[]>([]);

  useEffect(() => {
    if (campaignId) {
      setReviews(dummyReviews.filter(review => review.campaign.id === Number(campaignId)));
    } else {
      setReviews(dummyReviews);
    }
  }, [campaignId]);

  const handleDelete = (id: number) => {
    setReviews(reviews.filter((review) => review.id !== id));
  };

  return (
      <div>
        {reviews.length > 0 ? (
            reviews.map((review) => (
                <ReviewBox key={review.id} review={review} onDelete={handleDelete} />
            ))
        ) : (
            <p>리뷰가 없습니다.</p>
        )}
      </div>
  );
};

export default ReviewList;