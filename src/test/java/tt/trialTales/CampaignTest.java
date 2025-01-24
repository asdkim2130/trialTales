package tt.trialTales;

import io.restassured.RestAssured;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@Transactional
public class CampaignTest {

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member adminMember;

    @BeforeEach
    void setUp() {
        // 관리자 멤버 생성
        adminMember = new Member("admin", "password", "Admin", Role.ADMIN);
        memberRepository.save(adminMember);
    }

    @Test
    void 캠페인_생성_테스트() {
        // Given
        CampaignRequestDto requestDto = new CampaignRequestDto(
                "캠페인 이름",
                "캠페인 설명",
                null,
                null,
                "모집 중",
                10,
                adminMember.getId()
        );

        // When
        Campaign createdCampaign = campaignService.createCampaign(adminMember, requestDto);

        // Then
        assertThat(createdCampaign.getCampaignName()).isEqualTo("캠페인 이름");
        assertThat(createdCampaign.getDescription()).isEqualTo("캠페인 설명");
        assertThat(createdCampaign.getStatus()).isEqualTo("모집 중");
        assertThat(createdCampaign.getRecruitmentLimit()).isEqualTo(10);
    }

    @Test
    void 캠페인_ID_조회_테스트() {
        // Given
        Campaign campaign = new Campaign(
                adminMember,
                "캠페인 이름",
                "캠페인 설명",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                "모집 중",
                10
        );
        campaignRepository.save(campaign);

        // When
        Campaign foundCampaign = campaignService.getCampaignById(campaign.getId());

        // Then
        assertThat(foundCampaign.getCampaignName()).isEqualTo("캠페인 이름");
        assertThat(foundCampaign.getDescription()).isEqualTo("캠페인 설명");
    }

    @Test
    void 캠페인_ID_조회_예외_테스트() {
        // When & Then
        assertThatThrownBy(() -> campaignService.getCampaignById(999L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 캠페인이 존재하지 않습니다");
    }

    @Test
    void 캠페인_수정_테스트() {
        // Given
        Campaign campaign = new Campaign(
                adminMember,
                "캠페인 이름",
                "캠페인 설명",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                "모집 중",
                10
        );
        campaignRepository.save(campaign);

        CampaignRequestDto updateDto = new CampaignRequestDto(
                "수정된 이름",
                "수정된 설명",
                null,
                null,
                null,
                15,
                adminMember.getId()
        );

        // When
        Campaign updatedCampaign = campaignService.updateCampaign(campaign.getId(), updateDto);

        // Then
        assertThat(updatedCampaign.getCampaignName()).isEqualTo("수정된 이름");
        assertThat(updatedCampaign.getDescription()).isEqualTo("수정된 설명");
        assertThat(updatedCampaign.getRecruitmentLimit()).isEqualTo(15);
    }

    @Test
    void 캠페인_삭제_테스트() {
        // Given
        Campaign campaign = new Campaign(
                adminMember,
                "캠페인 이름",
                "캠페인 설명",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                "모집 중",
                10
        );
        campaignRepository.save(campaign);

        // When
        campaignService.deleteCampaign(campaign.getId());

        // Then
        assertThat(campaignRepository.findById(campaign.getId())).isEmpty();
    }

    @Test
    void 모집_종료된_캠페인_조회_테스트() {
        // Given
        Campaign expiredCampaign = new Campaign(
                adminMember,
                "종료된 캠페인",
                "종료된 설명",
                LocalDateTime.now().minusDays(10),
                LocalDateTime.now().minusDays(3),
                "모집 중",
                10
        );
        campaignRepository.save(expiredCampaign);

        Campaign activeCampaign = new Campaign(
                adminMember,
                "활성 캠페인",
                "활성 설명",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(5),
                "모집 중",
                15
        );
        campaignRepository.save(activeCampaign);

        // When
        List<Campaign> expiredCampaigns = campaignService.getExpiredCampaigns();

        // Then
        assertThat(expiredCampaigns).hasSize(1);
        assertThat(expiredCampaigns.get(0).getCampaignName()).isEqualTo("종료된 캠페인");
    }
}
