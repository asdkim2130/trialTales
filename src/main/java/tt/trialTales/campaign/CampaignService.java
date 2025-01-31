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
                member,
                requestDto.campaignName(),
                requestDto.description(),
                now,
                endDate,
                "모집 중",
                requestDto.recruitmentLimit()
        );

        Campaign savedCampaign = campaignRepository.save(campaign);
        return mapToDto(savedCampaign);
    }

    // 캠페인 조회 (ID로 조회)
    public CampaignResponseDto getCampaignByIdOrThrow(Long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("해당 캠페인을 찾을 수 없습니다. ID: " + campaignId));
        return mapToDto(campaign);
    }

    // 캠페인 수정
    public CampaignResponseDto updateCampaignOrThrow(Long campaignId, CampaignRequestDto requestDto) {
        Campaign existingCampaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("해당 캠페인을 찾을 수 없습니다. ID: " + campaignId));

        existingCampaign.setCampaignName(requestDto.campaignName());
        existingCampaign.setDescription(requestDto.description());
        existingCampaign.setRecruitmentLimit(requestDto.recruitmentLimit());

        Campaign updatedCampaign = campaignRepository.save(existingCampaign);
        return mapToDto(updatedCampaign);
    }

    // 캠페인 삭제
    public void deleteCampaignOrThrow(Long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("해당 캠페인을 찾을 수 없습니다. ID: " + campaignId));
        campaignRepository.delete(campaign);
    }

    // 모집 종료된 캠페인 조회
    public List<CampaignResponseDto> getExpiredCampaigns() {
        LocalDateTime now = LocalDateTime.now();
        return campaignRepository.findByEndDateBefore(now)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // 스케줄링 작업: 모집 종료 상태 업데이트
    @Scheduled(cron = "0 0 0 * * *")
    public void updateExpiredCampaigns() {
        LocalDateTime now = LocalDateTime.now();
        List<Campaign> expiredCampaigns = campaignRepository.findByEndDateBefore(now);

        for (Campaign campaign : expiredCampaigns) {
            if ("모집 중".equals(campaign.getStatus())) {
                campaign.setStatus("모집 완료");
                campaignRepository.save(campaign);
            }
        }
        System.out.println("모집 종료 상태 업데이트 완료: " + expiredCampaigns.size() + "개의 캠페인");
    }

    // 엔티티를 DTO로 변환하는 메서드
    private CampaignResponseDto mapToDto(Campaign campaign) {
        return new CampaignResponseDto(
                campaign.getId(),
                campaign.getCampaignName(),
                campaign.getDescription(),
                campaign.getStartDate(),
                campaign.getEndDate(),
                campaign.getStatus(),
                campaign.getRecruitmentLimit()
        );
    }
}

//    // 캠페인 목록 조회
//    public List<Campaign> getAllCampaigns() {
//        return campaignRepository.findAll();
//    }
//dfdf

