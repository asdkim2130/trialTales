package tt.trialTales.campaign;

import org.springframework.stereotype.Service;
import tt.trialTales.member.Member;
import tt.trialTales.member.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final MemberRepository memberRepository;

    public CampaignService(CampaignRepository campaignRepository, MemberRepository memberRepository) {
        this.campaignRepository = campaignRepository;
        this.memberRepository = memberRepository;
    }

    // 캠페인 생성
    public Campaign createCampaign(CampaignRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.memberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다: " + requestDto.memberId()));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endDate = now.plusDays(7);

        Campaign campaign = new Campaign(
                member, // 캠페인을 생성한 멤버
                requestDto.campaignName(),
                requestDto.description(),
                now, // 시작 날짜
                endDate, // 종료 날짜
                "모집 중",
                requestDto.recruitmentLimit()
        );

        return campaignRepository.save(campaign);
    }

    // 캠페인 조회 (ID로 조회)
    public Optional<Campaign> getCampaignById(Long campaignId) {
        return campaignRepository.findById(campaignId);
    }

    // 캠페인 수정
    public Optional<Campaign> updateCampaign(Long campaignId, CampaignRequestDto requestDto) {
        return campaignRepository.findById(campaignId).map(existingCampaign -> {
            existingCampaign.setCampaignName(requestDto.campaignName());
            existingCampaign.setDescription(requestDto.description());
            existingCampaign.setRecruitmentLimit(requestDto.recruitmentLimit());
            return campaignRepository.save(existingCampaign);
        });
    }

    // 캠페인 삭제
    public void deleteCampaign(Long campaignId) {
        if (!campaignRepository.existsById(campaignId)) {
            throw new IllegalArgumentException("해당 캠페인이 없습니다.: " + campaignId);
        }
        campaignRepository.deleteById(campaignId);
    }

    // 모집 종료된 캠페인 조회
    public List<Campaign> getExpiredCampaigns() {
        LocalDateTime now = LocalDateTime.now();
        return campaignRepository.findByEndDateBefore(now);
    }
}
