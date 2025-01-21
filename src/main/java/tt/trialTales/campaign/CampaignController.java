package tt.trialTales.campaign;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("campaigns")
public class CampaignController {

    private CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }


    // 캠페인 생성
    @PostMapping
    public ResponseEntity<Campaign> createCampaign(@RequestBody CampaignRequestDto requestDto) {
        Campaign createdCampaign = campaignService.createCampaign(requestDto);
        return ResponseEntity.ok(createdCampaign);
    }



}
