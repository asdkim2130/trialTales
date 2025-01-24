package tt.trialTales;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import tt.trialTales.campaign.Campaign;
import tt.trialTales.campaign.CampaignRepository;
import tt.trialTales.campaign.CampaignRequestDto;
import tt.trialTales.campaign.CampaignService;
import tt.trialTales.member.Member;
import tt.trialTales.member.MemberRepository;
import tt.trialTales.member.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CampaignTest {

    @LocalServerPort
    int port;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CampaignService campaignService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 캠페인_생성() {
        // Given
        Member admin = new Member("admin", "password", "Admin", Role.ADMIN);
        memberRepository.save(admin);

        CampaignRequestDto requestDto = new CampaignRequestDto(
                "캠페인 생성 테스트입니다",
                "캠페인 생성 테스트 내용입니다",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7).withHour(23).withMinute(59).withSecond(59),
                "모집 중",
                100,
                admin.getId()
        );

        // When
        Campaign createdCampaign = campaignService.createCampaign(requestDto);

        // Then
        Optional<Campaign> foundCampaign = campaignRepository.findById(createdCampaign.getId());
        assertThat(foundCampaign).isPresent();
        assertThat(foundCampaign.get().getCampaignName()).isEqualTo("캠페인 생성 테스트입니다");
        assertThat(foundCampaign.get().getDescription()).isEqualTo("캠페인 생성 테스트 내용입니다");
    }

    @Test
    void 캠페인_조회() {
        // Given
        Member admin = new Member("admin", "password", "Admin", Role.ADMIN);
        memberRepository.save(admin);

        Campaign campaign = new Campaign(
                admin,
                "Existing Campaign",
                "일산백운축산내용입니다.",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                "모집 중",
                50
        );
        campaignRepository.save(campaign);

        // When
        Optional<Campaign> foundCampaign = campaignService.getCampaignById(campaign.getId());

        // Then
        assertThat(foundCampaign).isPresent();
        assertThat(foundCampaign.get().getCampaignName()).isEqualTo("Existing Campaign");
        assertThat(foundCampaign.get().getRecruitmentLimit()).isEqualTo(50);
    }

    @Test
    void 캠페인_수정() {
        // Given
        Member admin = new Member("admin", "password", "Admin", Role.ADMIN);
        memberRepository.save(admin);

        Campaign campaign = new Campaign(
                admin,
                "캠페인 수정입니다",
                "캠페인 수정내용입니다",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                "모집 중",
                50
        );
        campaignRepository.save(campaign);

        CampaignRequestDto updateRequestDto = new CampaignRequestDto(
                "수정된 캠페인입니다",
                "수정된 캠페인 내용입니다",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                "모집 중",
                100,
                admin.getId()
        );

        // When
        Optional<Campaign> updatedCampaign = campaignService.updateCampaign(campaign.getId(), updateRequestDto);

        // Then
        assertThat(updatedCampaign).isPresent();
        assertThat(updatedCampaign.get().getCampaignName()).isEqualTo("수정된 캠페인입니다");
        assertThat(updatedCampaign.get().getRecruitmentLimit()).isEqualTo(100);
    }

    @Test
    void 캠페인_삭제() {
        // Given
        Member admin = new Member("admin", "password", "Admin", Role.ADMIN);
        memberRepository.save(admin);

        Campaign campaign = new Campaign(
                admin,
                "캠페인 삭제 입니다",
                "캠페인 삭제 내용 입니다",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                "모집 중",
                50
        );
        campaignRepository.save(campaign);

        // When
        campaignService.deleteCampaign(campaign.getId());

        // Then
        Optional<Campaign> deletedCampaign = campaignRepository.findById(campaign.getId());
        assertThat(deletedCampaign).isEmpty();
    }

    @Test
    void 모집_종료된_캠페인_조회() {
        // Given
        Member admin = new Member("admin", "password", "Admin", Role.ADMIN);
        memberRepository.save(admin);

        Campaign expiredCampaign = new Campaign(
                admin,
                "모집 종료된 캠페인입니다",
                "모집 종료",
                LocalDateTime.now().minusDays(10),
                LocalDateTime.now().minusDays(3),
                "모집 중",
                50
        );
        campaignRepository.save(expiredCampaign);

        // When
        List<Campaign> expiredCampaigns = campaignService.getExpiredCampaigns();

        // Then
        assertThat(expiredCampaigns).hasSize(1);
        assertThat(expiredCampaigns.get(0).getCampaignName()).isEqualTo("모집 종료된 캠페인입니다");
    }
}
