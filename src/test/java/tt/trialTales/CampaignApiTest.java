package tt.trialTales;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import tt.DatabaseCleanup;
import tt.trialTales.campaign.CampaignRequestDto;
import tt.trialTales.campaign.CampaignResponseDto;
import tt.trialTales.member.CreateMemberRequest;
import tt.trialTales.member.LoginRequest;
import tt.trialTales.member.Role;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CampaignApiTest {

    @LocalServerPort
    int port;

    private String adminToken; // 관리자 로그인 후 얻은 JWT 토큰

    @Autowired
    DatabaseCleanup databaseCleanup;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        databaseCleanup.execute();

        // 관리자 회원가입
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateMemberRequest("admin", "password", "관리자", Role.ADMIN))
                .when()
                .post("/members")
                .then().log().all()
                .statusCode(200);

        // 로그인 후 토큰 발급
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

    @DisplayName("캠페인 생성 API 테스트")
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

    @DisplayName("캠페인 목록 조회 API 테스트 (Soft Delete 반영)")
    @Test
    void 캠페인_목록_조회_API_테스트() {
        // 캠페인 2개 생성 (1개는 삭제)
        CampaignRequestDto request1 = new CampaignRequestDto(
                "캠페인1", "설명1",
                LocalDateTime.now(), LocalDateTime.now().plusDays(7),
                "모집 중", 50, 1L
        );

        CampaignRequestDto request2 = new CampaignRequestDto(
                "캠페인2", "설명2",
                LocalDateTime.now(), LocalDateTime.now().plusDays(7),
                "모집 중", 50, 1L
        );

        CampaignResponseDto campaign1 = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                .body(request1)
                .when()
                .post("/campaigns")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(CampaignResponseDto.class);

        CampaignResponseDto campaign2 = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                .body(request2)
                .when()
                .post("/campaigns")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(CampaignResponseDto.class);

        // 캠페인 1개 삭제
        RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                .when()
                .delete("/campaigns/" + campaign1.id())
                .then().log().all()
                .statusCode(204);

        // 조회 요청 (삭제된 캠페인은 조회되지 않아야 함)
        List<CampaignResponseDto> campaigns = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .get("/campaigns")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath().getList(".", CampaignResponseDto.class);

        assertThat(campaigns).hasSize(1); // 삭제된 캠페인은 보이지 않아야 함
        assertThat(campaigns.get(0).campaignName()).isEqualTo("캠페인2");
    }

    @DisplayName("✅ 캠페인 복구 API 테스트")
    @Test
    void 캠페인_복구_API_테스트() {
        // ✅ 캠페인 생성 후 삭제
        CampaignRequestDto request = new CampaignRequestDto(
                "삭제 테스트 캠페인",
                "삭제 테스트 설명",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                "모집 중",
                50,
                1L
        );

        CampaignResponseDto createdCampaign = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                .body(request)
                .when()
                .post("/campaigns")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(CampaignResponseDto.class);

        Long campaignId = createdCampaign.id();

        // ✅ 캠페인 삭제
        RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                .when()
                .delete("/campaigns/" + campaignId)
                .then().log().all()
                .statusCode(204);

        // ✅ 캠페인 복구 (기대: 200 OK)
        CampaignResponseDto restoredCampaign = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                .when()
                .post("/campaigns/" + campaignId + "/restore")
                .then().log().all()
                .statusCode(200) // ✅ 수정: 204 → 200
                .extract()
                .as(CampaignResponseDto.class);

        // ✅ 복구된 캠페인 검증
        assertThat(restoredCampaign).isNotNull();
        assertThat(restoredCampaign.campaignName()).isEqualTo("삭제 테스트 캠페인");

        //
    }
}
