package tt.trialTales.Application;

import java.time.LocalDateTime;

public record ApplicationResponse(Long id,
                                  Long userId,
                                  Long campaignId,
                                  String snsUrl,
                                  LocalDateTime applicationDate,
                                  Boolean isApproved) {
}
