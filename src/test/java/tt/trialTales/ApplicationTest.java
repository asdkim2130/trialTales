package tt.trialTales;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tt.trialTales.Application.*;
import tt.trialTales.campaign.*;
import tt.trialTales.member.*;

import java.time.LocalDateTime;
import java.util.List;

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


    @DisplayName("Application 생성 부터 조회")
    @Test
    //회원가입
    public void application생성_일반조회테스트(){

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

        //Application 조회
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .pathParam("applicationId", applicationId)
                .when()
                .get("applications/{applicationId}")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("사용자별 모든 신청서 조회")
    @Test
    public void 조회테스트(){
        final String adminUsername = "admin";
        final String adminPassword = "admin123";
        final Long memberId = 1L;

        signUp(adminUsername, adminPassword);

        logIn(adminUsername, adminPassword);

        String token = getToken(adminUsername, adminPassword);

        Long campaignId = getCampaignId(memberId);

        //member의 신청서 세 개 생성
        getApplicationId(token, memberId, campaignId);
        getApplicationId(token, memberId, campaignId);
        getApplicationId(token, memberId, campaignId);

        List<ReadApplicationResponse> list = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .pathParam("memberId", memberId)
                .when()
                .get("applications/user/{memberId}")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", ReadApplicationResponse.class);

        assertThat(list.size()).isEqualTo(3);
        assertThat(list).anyMatch(readApplicationResponse -> readApplicationResponse.id().equals(1L));
        assertThat(list).anyMatch(readApplicationResponse -> readApplicationResponse.id().equals(2L));
        assertThat(list).anyMatch(readApplicationResponse -> readApplicationResponse.id().equals(3L));
    }



    // 테스트 함수 메서드 분리
    //회원가입
    public ValidatableResponse signUp(String adminUsername, String adminPassword ){
        ValidatableResponse validatableResponse = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateMemberRequest(adminUsername, adminPassword, "nickname", Role.ADMIN))
                .when()
                .post("members")
                .then().log().all()
                .statusCode(200);
        return validatableResponse;
    }

    //로그인
    public ValidatableResponse logIn(String adminUsername, String adminPassword){
        ValidatableResponse login = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(adminUsername, adminPassword))
                .when()
                .post("login")
                .then().log().all()
                .statusCode(200);

        return login;
    }

    //토큰 발급
    public String getToken(String adminUsername, String adminPassword) {
        LoginResponse loginResponse = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(adminUsername, adminPassword))
                .when()
                .post("/login")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);

        return loginResponse.accessToken();
    }

    //캠페인 생성
    public Long getCampaignId(Long memberId){
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

        return  campaign.getId();
    }

    //신청서 생성
    public Long getApplicationId(String adminToken, Long memberId, Long campaignId){
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

        return application.getId();

    }
}

