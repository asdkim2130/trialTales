package tt.trialTales.review;

public record ReviewResponse(
        Long userId,
        Long campaignId,
        String reviewContent,
        int rating
) {
    public ReviewResponse(Review review) {
        this(
                review.getUserId(),
                review.getCampaignId(),
                review.getContent(),
                review.getRating());
    }
}
