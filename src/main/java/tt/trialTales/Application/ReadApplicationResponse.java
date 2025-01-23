package tt.trialTales.Application;

import tt.trialTales.campaign.Campaign;

import java.time.LocalDateTime;

public record ReadApplicationResponse(Long id,
                                      Campaign campaign,
                                      String snsUrl,
                                      LocalDateTime applicationDate,
                                      Boolean isApproved) {
}
