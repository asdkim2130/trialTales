Review (리뷰)
URL: /api/reviews

GET /api/reviews/{userId} - 사용자별 리뷰 조회
설명: 특정 사용자가 작성한 리뷰 목록을 조회합니다.
URL 파라미터:
userId: 사용자의 ID
Response:
json
복사
[
{
"id": 1,
"userId": 1,
"campaignId": 1,
"reviewContent": "정말 좋았습니다!",
"rating": 5,
"reviewDate": "2025-02-10"
}
]


