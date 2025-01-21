package tt.trialTales.campaign;

import org.springframework.stereotype.Service;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;

    public CampaignService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }


    // 캠페인 생성
    public Campaign createCampaign(CampaignRequestDto requestDto) {
        Campaign campaign = new Campaign(requestDto.campaignName(),
                requestDto.description(),
                requestDto.startDate(),
                requestDto.endDate(),
                requestDto.status(),
                requestDto.recruitmentLimit());

        return campaignRepository.save(campaign);
    }


}
