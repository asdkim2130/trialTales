package tt.trialTales.review;

public record ReviewRequest(
        Long userId,
        String content,
        int rating,
        Long campaignId
) {
}


