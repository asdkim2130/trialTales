package tt.trialTales;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tt.trialTales.Application.ApplicationRequest;
import tt.trialTales.Application.ApplicationResponse;
import tt.trialTales.Application.Status;
import tt.trialTales.campaign.*;
import tt.trialTales.member.*;

import java.time.LocalDateTime;

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
    //회원가임
    public void application생성테스트(){


        //회원가입
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateMemberRequest("admin", "12345", "nickname", Role.ADMIN))
                .when()
                .post("members")
                .then().log().all()
                .statusCode(200);

        //로그인
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("admin", "12345"))
                .when()
                .post("login")
                .then().log().all()
                .statusCode(200);


    }

    @Test
    public void 캠페인생성(){
        Member admin = new Member("admin", "password", "Admin", Role.ADMIN);
        memberRepository.save(admin);


        Campaign campaign = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CampaignRequestDto("캠페인 생성 테스트입니다",
                        "캠페인 생성 테스트 내용입니다",
                        LocalDateTime.now(),
                        LocalDateTime.now().plusDays(7).withHour(23).withMinute(59).withSecond(59),
                        "모집 중",
                        100,
                        admin.getId()))
                .when()
                .post("campaigns")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(Campaign.class);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header(new Header("bearToken", ""))
                .body(new ApplicationRequest(admin.getId(), campaign.getId(), "url", Status.PENDING ))
                .when()
                .post("applications")
                .then().log().all()
                .statusCode(200);
//                .extract()
//                .as(ApplicationResponse.class);

    }


}