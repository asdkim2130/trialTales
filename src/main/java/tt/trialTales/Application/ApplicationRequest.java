package tt.trialTales.Application;

public record ApplicationRequest(Long userId,
                                 Long campaignId,
                                 String snsUrl,
                                 Boolean isApproved) {
}
