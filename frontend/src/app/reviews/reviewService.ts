import { Review } from "@/app/reviews/interfaces";

const API_URL = "http://localhost:8080/reviews"; // Spring Boot 서버 주소

export const fetchReviews = async (): Promise<Review[]> => {
  // const response = await fetch(API_URL);
  // return response.json();
  return [
    {
      id: 1,
      userId: "무강아보고싶어",
      content: "리뷰 내용",
      rating: 5,
      reviewDate: "2025-02-11",
      updatedAt: "",
      campaign: {
        id: 1,
        name: "XXX 체험",
      },
    },
    {
      id: 1,
      userId: "무강아보고싶어",
      content: "리뷰 내용",
      rating: 5,
      reviewDate: "2025-02-11",
      updatedAt: "",
      campaign: {
        id: 1,
        name: "XXX 체험",
      },
    },

    {
      id: 2,
      userId: "집에가고싶어",
      content: "리뷰 내용2",
      rating: 4,
      reviewDate: "",
      updatedAt: "",
      campaign: {
        id: 2,
        name: "XXX 체험2",
      },
    },
    {
      id: 2,
      userId: "집에가고싶어",
      content: "리뷰 내용2",
      rating: 4,
      reviewDate: "",
      updatedAt: "",
      campaign: {
        id: 2,
        name: "XXX 체험2",
      },
    },
    {
      id: 2,
      userId: "집에가고싶어",
      content: "리뷰 내용2",
      rating: 4,
      reviewDate: "",
      updatedAt: "",
      campaign: {
        id: 2,
        name: "XXX 체험2",
      },
    },
    {
      id: 2,
      userId: "집에가고싶어",
      content: "리뷰 내용2",
      rating: 4,
      reviewDate: "",
      updatedAt: "",
      campaign: {
        id: 2,
        name: "XXX 체험2",
      },
    },
  ];
};

export const createReview = async (
  data: { content: string; rating: number; campaignId: number },
  token: string,
) => {
  await fetch(API_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify(data),
  });
};

export const updateReview = async (
  reviewId: number,
  data: { content: string; rating: number },
  token: string,
) => {
  await fetch(`${API_URL}/${reviewId}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify(data),
  });
};

export const deleteReview = async (reviewId: number, token: string) => {
  await fetch(`${API_URL}/${reviewId}`, {
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};
