package tt.trialTales.campaign;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tt.trialTales.member.Member;
import tt.trialTales.member.MemberRepository;
import tt.trialTales.member.MemberService;
import tt.trialTales.member.Role;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {

    private final CampaignService campaignService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    public CampaignController(CampaignService campaignService, MemberService memberService, MemberRepository memberRepository) {
        this.campaignService = campaignService;
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

    // 캠페인 생성 (관리자만 접근 가능)
    @PostMapping
    public ResponseEntity<Campaign> createCampaign(
            @RequestParam Long memberId, // 요청한 사용자 ID
            @RequestBody CampaignRequestDto requestDto) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")); // 사용자 확인
        if (!member.getRole().equals(Role.ADMIN)) {
            return ResponseEntity.status(403).build(); // 관리자 권한이 없으면 403 Forbidden
        }

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

    // 캠페인 수정 (관리자만 접근 가능)
    @PutMapping("/{campaignId}")
    @PreAuthorize("hasRole('ADMIN')") // 관리자 권한만 접근 가능
    public ResponseEntity<Campaign> updateCampaign(
            @PathVariable Long campaignId,
            @RequestBody CampaignRequestDto requestDto) {
        Optional<Campaign> updatedCampaign = campaignService.updateCampaign(campaignId, requestDto);
        return updatedCampaign.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 캠페인 삭제 (관리자만 접근 가능)
    @DeleteMapping("/{campaignId}")
    public ResponseEntity<Void> deleteCampaign(
            @RequestParam Long memberId,
            @PathVariable Long campaignId) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
        if (!member.getRole().equals(Role.ADMIN)) {
            return ResponseEntity.status(403).build(); // 관리자 권한이 없으면 403 Forbidden
        }

        campaignService.deleteCampaign(campaignId);
        return ResponseEntity.noContent().build();
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
