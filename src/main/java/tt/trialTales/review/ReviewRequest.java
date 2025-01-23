package tt.trialTales.review;

import tt.trialTales.campaign.Campaign;

public record ReviewRequest(
        Long userId,
        String content,
        int rating,
        Campaign campaign
) {
    public ReviewRequest(Review review) {
    this(

            review.getUserId(),
            review.getContent(),
            review.getRating(),
            review.getCampaign());
}
}


