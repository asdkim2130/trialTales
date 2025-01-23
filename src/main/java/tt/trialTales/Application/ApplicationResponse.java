package tt.trialTales.Application;

import tt.trialTales.campaign.Campaign;
import tt.trialTales.member.Member;

import java.time.LocalDateTime;

public record ApplicationResponse(Long id,
                                  Member member,
                                  Campaign campaign,
                                  String snsUrl,
                                  LocalDateTime applicationDate,
                                  Status status) {
}
