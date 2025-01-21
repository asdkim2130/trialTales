package tt.trialTales.campaign;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;

    public CampaignService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    // 캠페인 생성
    public Campaign createCampaign(CampaignRequestDto requestDto) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endDate = now.plusDays(7);

        Campaign campaign = new Campaign(
                requestDto.campaignName(),
                requestDto.description(),
                now, // 시작 날짜는 등록 날짜
                endDate, // 종료 날짜는 등록 날짜 + 7일
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

    // 상태 자동 변경
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    public void updateCampaignStatuses() {
        LocalDateTime now = LocalDateTime.now();
        campaignRepository.findAll().forEach(campaign -> {
            if (campaign.getEndDate().isBefore(now) && campaign.getStatus().equals("모집 중")) {
                campaign.setStatus("모집 완료");
                campaignRepository.save(campaign);
            }
        });
    }
}
