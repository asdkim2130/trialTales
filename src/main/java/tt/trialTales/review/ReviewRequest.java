package tt.trialTales.review;

public record ReviewRequest(
        Long userId,
        Long campaignId,
        String content,
        int rating
) {
    public ReviewRequest(Review review) {
    this(

            review.getUserId(),
            review.getCampaignId(),
            review.getContent(),
            review.getRating());
}
}
