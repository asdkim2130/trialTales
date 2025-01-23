package tt.trialTales.review;

import tt.trialTales.campaign.Campaign;

public record ReviewResponse(
        Long id,
        Long userId,
        String Content,
        int rating,
        Campaign campaign
) {
    public ReviewResponse(Review review) {
        this(
                review.getId(),
                review.getUserId(),
                review.getContent(),
                review.getRating(),
                review.getCampaign());
    }
}
