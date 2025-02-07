package tt.trialTales.Application;

import java.time.LocalDateTime;

public record ApplicationRequest(Long memberId,
                                 Long campaignId,
                                 String email,
                                 String snsUrl,
                                 LocalDateTime applicationDate,
                                 Status status) {
}
