package tt.review;

public record ReviewResponse(
        Long id,
        Long userId,
        Long campaignId,
        String reviewContent,
        int rating
) {
    public ReviewResponse(Review review) {
        this(
                review.getId(),
                review.getUserId(),
                review.getCampaignId(),
                review.getContent(),
                review.getRating());
    }
}
