package tt.trialTales.campaign;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tt.trialTales.member.Member;
import tt.trialTales.member.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final MemberRepository memberRepository;

    public CampaignService(CampaignRepository campaignRepository, MemberRepository memberRepository) {
        this.campaignRepository = campaignRepository;
        this.memberRepository = memberRepository;
    }

    // 캠페인 생성
    public CampaignResponseDto createCampaign(Member member, CampaignRequestDto requestDto) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endDate = now.plusDays(7).withHour(23).withMinute(59).withSecond(59);

        Campaign campaign = new Campaign(
                member, requestDto.campaignName(), requestDto.description(),
                now, endDate, "모집 중", requestDto.recruitmentLimit()
        );

        Campaign savedCampaign = campaignRepository.save(campaign);
        return mapToDto(savedCampaign);
    }

    // Soft Delete 적용: 삭제된 캠페인은 조회되지 않음
    public CampaignResponseDto getCampaignByIdOrThrow(Long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .filter(c -> !c.isDeleted()) // 삭제되지 않은 캠페인만 조회
                .orElseThrow(() -> new IllegalArgumentException("해당 캠페인을 찾을 수 없습니다. ID: " + campaignId));
        return mapToDto(campaign);
    }

    // 캠페인 수정
    public CampaignResponseDto updateCampaignOrThrow(Long campaignId, CampaignRequestDto requestDto) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .filter(c -> !c.isDeleted())
                .orElseThrow(() -> new IllegalArgumentException("해당 캠페인을 찾을 수 없습니다. ID: " + campaignId));

        campaign.updateCampaign(requestDto.campaignName(), requestDto.description(), requestDto.recruitmentLimit());
        campaignRepository.save(campaign);

        return mapToDto(campaign);
    }

    // Soft Delete 적용
    public void deleteCampaignOrThrow(Long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("해당 캠페인을 찾을 수 없습니다. ID: " + campaignId));
        campaign.delete();
        campaignRepository.save(campaign);
    }

    // 캠페인 복구 기능: 복구 후 DTO 반환
    public CampaignResponseDto restoreCampaignOrThrow(Long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("해당 캠페인을 찾을 수 없습니다. ID: " + campaignId));

        campaign.restore();
        campaignRepository.save(campaign);

        return mapToDto(campaign);
    }

    // 기존 모집 종료된 캠페인 조회 기능 유지 (Soft Delete 반영)
    public List<CampaignResponseDto> getExpiredCampaigns() {
        LocalDateTime now = LocalDateTime.now();
        return campaignRepository.findByEndDateBefore(now)
                .stream()
                .filter(c -> !c.isDeleted())
                .map(this::mapToDto)
                .toList();
    }

    // 기존 스케줄러 기능 유지 (Soft Delete 반영)
    @Scheduled(cron = "0 0 0 * * *")
    public void updateExpiredCampaigns() {
        LocalDateTime now = LocalDateTime.now();
        List<Campaign> expiredCampaigns = campaignRepository.findByEndDateBefore(now);

        for (Campaign campaign : expiredCampaigns) {
            if (!campaign.isDeleted() && "모집 중".equals(campaign.getStatus())) {
                campaign.setStatus("모집 완료");
                campaignRepository.save(campaign);
            }
        }
    }

    private CampaignResponseDto mapToDto(Campaign campaign) {
        return new CampaignResponseDto(
                campaign.getId(), campaign.getCampaignName(), campaign.getDescription(),
                campaign.getStartDate(), campaign.getEndDate(),
                campaign.getStatus(), campaign.getRecruitmentLimit()
        );
    }

    // 모집 종료된 캠페인 조회 시 Soft Delete 적용
    public List<CampaignResponseDto> getAllCampaigns() {
        return campaignRepository.findByDeletedFalse()
                .stream()
                .map(this::mapToDto)
                .toList();
    }
}
