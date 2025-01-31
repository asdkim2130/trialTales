package tt.trialTales;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import tt.trialTales.campaign.*;
import tt.trialTales.member.Member;
import tt.trialTales.member.MemberRepository;
import tt.trialTales.member.Role;

import java.time.LocalDateTime;
import java.util.List;


import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class CampaignTest {

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
        adminMember = new Member("admin", "password", "관리자", Role.ADMIN);
        memberRepository.save(adminMember);
    }

    @DisplayName("✅ 캠페인 생성 테스트")
    @Test
    void 캠페인_생성_성공() {
        // Given
        CampaignRequestDto requestDto = new CampaignRequestDto(
                "테스트 캠페인",
                "테스트 설명",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7).withHour(23).withMinute(59).withSecond(59),
                "모집 중",
                50,
                adminMember.getId()
        );

        // When
        CampaignResponseDto createdCampaign = campaignService.createCampaign(adminMember, requestDto);
        List<Campaign> allCampaigns = campaignRepository.findAll();

        // Then
        assertThat(createdCampaign).isNotNull();
        assertThat(allCampaigns).hasSize(1);
        assertThat(createdCampaign.campaignName()).isEqualTo("테스트 캠페인");
        assertThat(createdCampaign.status()).isEqualTo("모집 중");
    }

    @DisplayName("✅ 캠페인 조회 테스트")
    @Test
    void 캠페인_조회_성공() {
        // Given
        Campaign campaign = campaignRepository.save(new Campaign(
                adminMember,
                "조회 테스트 캠페인",
                "조회 테스트 설명",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7).withHour(23).withMinute(59).withSecond(59),
                "모집 중",
                30
        ));

        // When
        CampaignResponseDto foundCampaign = campaignService.getCampaignByIdOrThrow(campaign.getId());

        // Then
        assertThat(foundCampaign).isNotNull();
        assertThat(foundCampaign.campaignName()).isEqualTo("조회 테스트 캠페인");
        assertThat(foundCampaign.recruitmentLimit()).isEqualTo(30);
    }

    @DisplayName("✅ 존재하지 않는 캠페인 조회 시 예외 발생")
    @Test
    void 캠페인_조회_실패_예외() {
        // Given
        Long invalidId = 999L;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            campaignService.getCampaignByIdOrThrow(invalidId);
        });
    }

    @DisplayName("✅ 캠페인 수정 테스트")
    @Test
    void 캠페인_수정_성공() {
        // Given
        Campaign campaign = campaignRepository.save(new Campaign(
                adminMember,
                "수정 전 캠페인",
                "수정 전 설명",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7).withHour(23).withMinute(59).withSecond(59),
                "모집 중",
                20
        ));

        CampaignRequestDto updateRequest = new CampaignRequestDto(
                "수정된 캠페인",
                "수정된 설명",
                campaign.getStartDate(),
                campaign.getEndDate(),
                "모집 완료",
                100,
                adminMember.getId()
        );

        // When
        CampaignResponseDto updatedCampaign = campaignService.updateCampaignOrThrow(campaign.getId(), updateRequest);

        // Then
        assertThat(updatedCampaign).isNotNull();
        assertThat(updatedCampaign.campaignName()).isEqualTo("수정된 캠페인");
        assertThat(updatedCampaign.recruitmentLimit()).isEqualTo(100);
        assertThat(updatedCampaign.status()).isEqualTo("모집 완료");
    }

    @DisplayName("✅ 존재하지 않는 캠페인 수정 시 예외 발생")
    @Test
    void 캠페인_수정_실패_예외() {
        // Given
        Long invalidId = 999L;
        CampaignRequestDto updateRequest = new CampaignRequestDto(
                "업데이트 캠페인",
                "업데이트 설명",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7).withHour(23).withMinute(59).withSecond(59),
                "모집 중",
                50,
                adminMember.getId()
        );

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            campaignService.updateCampaignOrThrow(invalidId, updateRequest);
        });
    }

    @DisplayName("✅ 캠페인 삭제 테스트")
    @Test
    void 캠페인_삭제_성공() {
        // Given
        Campaign campaign = campaignRepository.save(new Campaign(
                adminMember,
                "삭제 테스트 캠페인",
                "삭제 테스트 설명",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7).withHour(23).withMinute(59).withSecond(59),
                "모집 중",
                15
        ));

        // When
        campaignService.deleteCampaignOrThrow(campaign.getId());
        List<Campaign> allCampaigns = campaignRepository.findAll();

        // Then
        assertThat(allCampaigns).isEmpty();
    }

    @DisplayName("✅ 존재하지 않는 캠페인 삭제 시 예외 발생")
    @Test
    void 캠페인_삭제_실패_예외() {
        // Given
        Long invalidId = 999L;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            campaignService.deleteCampaignOrThrow(invalidId);
        });
    }

    @DisplayName("✅ 모집 종료된 캠페인 조회 테스트")
    @Test
    void 모집_종료된_캠페인_조회_성공() {
        // Given
        campaignRepository.save(new Campaign(
                adminMember,
                "종료된 캠페인",
                "종료된 캠페인 설명",
                LocalDateTime.now().minusDays(10),
                LocalDateTime.now().minusDays(3).withHour(23).withMinute(59).withSecond(59),
                "모집 중",
                10
        ));

        campaignRepository.save(new Campaign(
                adminMember,
                "진행 중 캠페인",
                "진행 중 캠페인 설명",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7).withHour(23).withMinute(59).withSecond(59),
                "모집 중",
                20
        ));

        // When
        List<CampaignResponseDto> expiredCampaigns = campaignService.getExpiredCampaigns();

        // Then
        assertThat(expiredCampaigns).isNotEmpty();
        assertThat(expiredCampaigns).hasSize(1);
        assertThat(expiredCampaigns.get(0).campaignName()).isEqualTo("종료된 캠페인");
    }
}
