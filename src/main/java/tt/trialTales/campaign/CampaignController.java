package tt.trialTales.campaign;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    // 캠페인 생성
    @PostMapping
    public ResponseEntity<Campaign> createCampaign(@RequestBody CampaignRequestDto requestDto) {
        Campaign createdCampaign = campaignService.createCampaign(requestDto);
        return ResponseEntity.ok(createdCampaign);
    }

    // 캠페인 조회
    @GetMapping("/{campaignId}")
    public ResponseEntity<Campaign> getCampaignById(@PathVariable Long campaignId) {
        Optional<Campaign> campaign = campaignService.getCampaignById(campaignId);
        return campaign.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 캠페인 수정
    @PutMapping("/{campaignId}")
    public ResponseEntity<Campaign> updateCampaign(
            @PathVariable Long campaignId,
            @RequestBody CampaignRequestDto requestDto) {
        Optional<Campaign> updatedCampaign = campaignService.updateCampaign(campaignId, requestDto);
        return updatedCampaign.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 캠페인 삭제
    @DeleteMapping("/{campaignId}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable Long campaignId) {
        try {
            campaignService.deleteCampaign(campaignId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 모집 종료된 캠페인 조회
    @GetMapping("/expired")
    public ResponseEntity<List<Campaign>> getExpiredCampaigns() {
        List<Campaign> expiredCampaigns = campaignService.getExpiredCampaigns();
        return ResponseEntity.ok(expiredCampaigns);
    }
}

//HTTP Method	URL	설명

//GET	 /campaigns	모든 캠페인 조회
//GET	 /campaigns/{id}	 특정 ID 캠페인 조회
//POST	 /campaigns	         캠페인 생성
//PUT	 /campaigns/{id}	 캠페인 수정
//DELETE /campaigns/{id}	 캠페인 삭제
//GET	 /campaigns/expired	 모집 종료된 캠페인 조회
