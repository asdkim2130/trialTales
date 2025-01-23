package tt.trialTales.review;

import tt.trialTales.campaign.Campaign;

public record ReviewResponse(
        Long id,
        Long userId,
        Long campaignId,
        String reviewContent,
        int rating,
        Campaign campaign
) {
    public ReviewResponse(Review review) {
        this(
                review.getId(),
                review.getUserId(),
                review.getCampaignId(),
                review.getContent(),
                review.getRating(),
                review.getCampaign());
    }
}
