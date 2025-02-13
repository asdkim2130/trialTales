package tt.trialTales.Application;

import tt.trialTales.campaign.Campaign;

import java.time.LocalDateTime;

public record ReadApplicationResponse(Long id,
                                      Campaign campaign,
                                      String email,
                                      String snsUrl,
                                      LocalDateTime applicationDate,
                                      Status status,
                                      LocalDateTime deletedAt,
                                      Boolean isDeleted) {
}
