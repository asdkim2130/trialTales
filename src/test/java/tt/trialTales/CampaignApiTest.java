package tt.trialTales;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import tt.trialTales.campaign.CampaignRequestDto;
import tt.trialTales.campaign.CampaignResponseDto;
import tt.trialTales.member.CreateMemberRequest;
import tt.trialTales.member.LoginRequest;
import tt.trialTales.member.Role;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CampaignApiTest {

    @LocalServerPort
    int port;

    private String adminToken; // 관리자 로그인 후 얻은 JWT 토큰

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        // ✅ 회원가입 (관리자)
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateMemberRequest("admin", "password", "관리자", Role.ADMIN))
                .when()
                .post("/members")
                .then().log().all()
                .statusCode(200);

        // ✅ 로그인 후 토큰 발급
        adminToken = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("admin", "password"))
                .when()
                .post("/login")
                .then().log().all()
                .statusCode(200)
                .extract()
                .path("token"); // 응답 JSON에서 "token" 키를 추출
    }

    @DisplayName("✅ 캠페인 생성 API 테스트")
    @Test
    void 캠페인_생성_API_테스트() {
        // Given
        CampaignRequestDto requestDto = new CampaignRequestDto(
                "API 테스트 캠페인",
                "API 테스트 설명",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7).withHour(23).withMinute(59).withSecond(59),
                "모집 중",
                100,
                1L // 관리자 ID
        );

        // When
        CampaignResponseDto responseDto = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken) // JWT 토큰 추가
                .body(requestDto)
                .when()
                .post("/campaigns")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(CampaignResponseDto.class);

        // Then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.campaignName()).isEqualTo("API 테스트 캠페인");
    }

    @DisplayName("✅ 캠페인 조회 API 테스트")
    @Test
    void 캠페인_조회_API_테스트() {
        // ✅ 캠페인 생성 후 ID 가져오기
        CampaignRequestDto requestDto = new CampaignRequestDto(
                "조회 테스트 캠페인",
                "조회 테스트 설명",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7).withHour(23).withMinute(59).withSecond(59),
                "모집 중",
                50,
                1L
        );

        CampaignResponseDto createdCampaign = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                .body(requestDto)
                .when()
                .post("/campaigns")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(CampaignResponseDto.class);

        Long campaignId = createdCampaign.id(); // 생성된 캠페인 ID

        // ✅ 캠페인 조회 테스트
        CampaignResponseDto foundCampaign = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .get("/campaigns/" + campaignId)
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(CampaignResponseDto.class);

        assertThat(foundCampaign).isNotNull();
        assertThat(foundCampaign.campaignName()).isEqualTo("조회 테스트 캠페인");
    }

    @DisplayName("✅ 캠페인 수정 API 테스트")
    @Test
    void 캠페인_수정_API_테스트() {
        // ✅ 캠페인 생성 후 ID 가져오기
        CampaignRequestDto requestDto = new CampaignRequestDto(
                "수정 전 캠페인",
                "수정 전 설명",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7).withHour(23).withMinute(59).withSecond(59),
                "모집 중",
                50,
                1L
        );

        CampaignResponseDto createdCampaign = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                .body(requestDto)
                .when()
                .post("/campaigns")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(CampaignResponseDto.class);

        Long campaignId = createdCampaign.id(); // 생성된 캠페인 ID

        // ✅ 수정 요청 데이터
        CampaignRequestDto updateRequest = new CampaignRequestDto(
                "수정된 캠페인",
                "수정된 설명",
                createdCampaign.startDate(),
                createdCampaign.endDate(),
                "모집 완료",
                100,
                1L
        );

        // ✅ 캠페인 수정 요청
        CampaignResponseDto updatedCampaign = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                .body(updateRequest)
                .when()
                .put("/campaigns/" + campaignId)
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(CampaignResponseDto.class);

        // ✅ 검증
        assertThat(updatedCampaign).isNotNull();
        assertThat(updatedCampaign.campaignName()).isEqualTo("수정된 캠페인");
        assertThat(updatedCampaign.recruitmentLimit()).isEqualTo(100);
    }

    @DisplayName("✅ 캠페인 삭제 API 테스트")
    @Test
    void 캠페인_삭제_API_테스트() {
        // ✅ 캠페인 생성 후 ID 가져오기
        CampaignRequestDto requestDto = new CampaignRequestDto(
                "삭제 테스트 캠페인",
                "삭제 테스트 설명",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7).withHour(23).withMinute(59).withSecond(59),
                "모집 중",
                50,
                1L
        );

        CampaignResponseDto createdCampaign = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                .body(requestDto)
                .when()
                .post("/campaigns")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(CampaignResponseDto.class);

        Long campaignId = createdCampaign.id(); // 생성된 캠페인 ID

        // ✅ 캠페인 삭제 요청
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                .queryParam("memberId", 1L)
                .when()
                .delete("/campaigns/" + campaignId)
                .then().log().all()
                .statusCode(204); // ✅ 삭제 성공 시 204
    }
}



