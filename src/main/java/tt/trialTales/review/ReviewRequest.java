package tt.trialTales.review;

import tt.trialTales.campaign.Campaign;

public record ReviewRequest(
        Long userId,
        Long campaignId,
        String content,
        int rating,
        Campaign campaign
) {
    public ReviewRequest(Review review) {
    this(

            review.getUserId(),
            review.getCampaignId(),
            review.getContent(),
            review.getRating(),
            review.getCampaign());
}
}


