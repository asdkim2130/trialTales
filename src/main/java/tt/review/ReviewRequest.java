package tt.review;

public record ReviewRequest(
        Long userId,
        Long campaignId,
        String reviewContent,
        int rating
) {
}
