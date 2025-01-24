package tt.trialTales.Application;

public record ApplicationRequest(Long memberId,
                                 Long campaignId,
                                 String snsUrl,
                                 Status status) {
}
