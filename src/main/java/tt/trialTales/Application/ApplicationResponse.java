package tt.trialTales.Application;

import tt.trialTales.member.Member;

import java.time.LocalDateTime;

public record ApplicationResponse(Long id,
                                  Member member,
                                  Long campaignId,
                                  String snsUrl,
                                  LocalDateTime applicationDate,
                                  Boolean isApproved) {
}
