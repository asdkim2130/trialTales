package tt.trialTales;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tt.trialTales.Application.Application;
import tt.trialTales.Application.ApplicationRequest;
import tt.trialTales.Application.ApplicationResponse;
import tt.trialTales.Application.Status;
import tt.trialTales.campaign.*;
import tt.trialTales.member.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

    @Autowired
    private MemberRepository memberRepository;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }


    @DisplayName("Application 생성")
    @Test
    //회원가입
    public void application생성테스트(){

        final String adminUsername = "admin";
        final String adminPassword = "admin123";
        final String memberUsername = "memberToDelete";
        final String memberPassword = "member123";
        final Long memberId = 1L;

        //회원가입
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateMemberRequest(adminUsername, adminPassword, "nickname", Role.ADMIN))
                .when()
                .post("members")
                .then().log().all()
                .statusCode(200);

        //로그인
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(adminUsername, adminPassword))
                .when()
                .post("login")
                .then().log().all()
                .statusCode(200);

        // 관리자 로그인 후 토큰 얻기
        LoginResponse adminLoginResponse = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(adminUsername, adminPassword))
                .when()
                .post("/login")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);

        String adminToken = adminLoginResponse.accessToken();


        //캠페인 생성
        Campaign campaign = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CampaignRequestDto("캠페인 생성 테스트입니다",
                        "캠페인 생성 테스트 내용입니다",
                        LocalDateTime.now(),
                        LocalDateTime.now().plusDays(7).withHour(23).withMinute(59).withSecond(59),
                        "모집 중",
                        100,
                        memberId))
                .when()
                .post("campaigns")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(Campaign.class);

        Long campaignId = campaign.getId();

        // Application 생성
        Application application = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .body(new ApplicationRequest(memberId, campaignId, "url", Status.PENDING))
                .when()
                .post("applications")
                .then().log().all()
                .statusCode(200).
                extract()
                .as(Application.class);

        Long applicationId = application.getId();
        assertThat(applicationId).isNotNull();


        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .pathParam("applicationId", applicationId)
                .when()
                .get("applications/{applicationId}")
                .then().log().all()
                .statusCode(200);
    }



}

