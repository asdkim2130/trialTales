package tt.trialTales.Application;

public record ApplicationRequest(Long userId,
                                 Long memberId,
                                 Long campaignId,
                                 String snsUrl,
                                 Boolean isApproved) {
}
