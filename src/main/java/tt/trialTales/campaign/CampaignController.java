package tt.trialTales.campaign;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tt.trialTales.member.Member;
import tt.trialTales.member.MemberRepository;
import tt.trialTales.member.Role;

import java.util.List;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {

    private final CampaignService campaignService;
    private final MemberRepository memberRepository;

    public CampaignController(CampaignService campaignService, MemberRepository memberRepository) {
        this.campaignService = campaignService;
        this.memberRepository = memberRepository;
    }

    // 캠페인 생성 (관리자만 가능)
    @PostMapping
    public ResponseEntity<CampaignResponseDto> createCampaign(@RequestBody CampaignRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.memberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
        if (!member.getRole().equals(Role.ADMIN)) {
            return ResponseEntity.status(403).build(); // 관리자 권한 없음
        }
        CampaignResponseDto createdCampaign = campaignService.createCampaign(member, requestDto);
        return ResponseEntity.ok(createdCampaign);
    }

    // 캠페인 조회 (삭제된 캠페인은 조회되지 않음)
    @GetMapping("/{campaignId}")
    public ResponseEntity<CampaignResponseDto> getCampaignById(@PathVariable Long campaignId) {
        CampaignResponseDto campaign = campaignService.getCampaignByIdOrThrow(campaignId);
        return ResponseEntity.ok(campaign);
    }

    // 캠페인 수정 (관리자만 가능)
    @PutMapping("/{campaignId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CampaignResponseDto> updateCampaign(
            @PathVariable Long campaignId, @RequestBody CampaignRequestDto requestDto) {
        CampaignResponseDto updatedCampaign = campaignService.updateCampaignOrThrow(campaignId, requestDto);
        return ResponseEntity.ok(updatedCampaign);
    }

    // 캠페인 삭제 (Soft Delete 적용)
    @DeleteMapping("/{campaignId}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable Long campaignId) {
        campaignService.deleteCampaignOrThrow(campaignId);
        return ResponseEntity.noContent().build();
    }

    // 캠페인 복구 API
    @PostMapping("/{campaignId}/restore")
    public ResponseEntity<CampaignResponseDto> restoreCampaign(@PathVariable Long campaignId) {
        CampaignResponseDto restoredCampaign = campaignService.restoreCampaignOrThrow(campaignId);
        return ResponseEntity.ok(restoredCampaign);
    }

    // 모집 종료된 캠페인 조회 (Soft Delete 반영)
    @GetMapping("/expired")
    public ResponseEntity<List<CampaignResponseDto>> getExpiredCampaigns() {
        List<CampaignResponseDto> expiredCampaigns = campaignService.getExpiredCampaigns();
        return ResponseEntity.ok(expiredCampaigns);
    }
    // 캠페인 목록 조회 (Soft Delete 반영)
    @GetMapping
    public ResponseEntity<List<CampaignResponseDto>> getAllCampaigns() {
        List<CampaignResponseDto> campaigns = campaignService.getAllCampaigns();
        return ResponseEntity.ok(campaigns);
    }
}
