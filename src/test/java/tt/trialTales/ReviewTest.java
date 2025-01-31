package tt.trialTales;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import tt.trialTales.campaign.Campaign;
import tt.trialTales.campaign.CampaignRequestDto;
import tt.trialTales.member.*;
import tt.trialTales.review.ReviewRequest;
import tt.trialTales.review.ReviewResponse;

import java.time.LocalDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReviewTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }


    @Test
    public void createReview() {
        // 날짜 문자열을 LocalDateTime으로 변환
        String startDateString = "2024-01-21";
        LocalDateTime startDate = LocalDateTime.parse(startDateString + "T00:00:00");

        // 회원 가입
        given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateMemberRequest("doraemon1", "doradora123", null, Role.ADMIN))
                .when()
                .post("/members")
                .then().log().all()
                .statusCode(200);

        // 캠페인 생성
        given().log().all()
                .contentType(ContentType.JSON)
                .body(new CampaignRequestDto(
                        "",
                        "",
                        LocalDateTime.parse("2025-01-01T00:00:00"),
                        LocalDateTime.parse("2025-01-07T00:00:00"),
                        "",
                        1,
                        1L))
                .when()
                .post("/campaigns")
                .then().log().all()
                .statusCode(200);

        // 리뷰 생성
        given().log().all()
                .contentType(ContentType.JSON)
                .body(new ReviewRequest(
                        1L,
                        "캠페인",
                        5,
                        1L))  // request body에 ReviewRequest 객체 전달
                .when()
                .post("/reviews")  // 리뷰 생성 API 호출
                .then()
                .statusCode(200);  // HTTP 200 OK 상태 코드 검증

        List<ReviewResponse> responses = given().log().all()
                .when()
                .get("/reviews")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", ReviewResponse.class);

        // 응답 본문 검증: assertThat 사용
        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).Content()).isEqualTo("캠페인");
    }

    @Test
    public void 리뷰삭제() {
        // 날짜 문자열을 LocalDateTime으로 변환
        String startDateString = "2024-01-21";
        LocalDateTime startDate = LocalDateTime.parse(startDateString + "T00:00:00");

        // 회원 가입
        given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateMemberRequest("doraemon1", "doradora123", null, Role.ADMIN))
                .when()
                .post("/members")
                .then().log().all()
                .statusCode(200);

        // 로그인하여 토큰 추출
        LoginResponse LoginResponse = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("doraemon1", "doradora123"))
                .when()
                .post("/login")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);

        // 캠페인 생성
        given().log().all()
                .contentType(ContentType.JSON)
                .body(new CampaignRequestDto(
                        "",
                        "",
                        LocalDateTime.parse("2025-01-01T00:00:00"),
                        LocalDateTime.parse("2025-01-07T00:00:00"),
                        "",
                        1,
                        1L))
                .when()
                .post("/campaigns")
                .then().log().all()
                .statusCode(200);

        // 리뷰 생성
        given().log().all()
                .contentType(ContentType.JSON)
                .body(new ReviewRequest(
                        1L,
                        "캠페인",
                        5,
                        1L))
                .when()
                .post("/reviews")
                .then()
                .statusCode(200);

        // 리뷰 삭제
        given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + LoginResponse.accessToken())
                .pathParam("reviewId", 1)
                .when()
                .delete("/reviews/{reviewId}")
                .then().log().all()
                .statusCode(200);
    }
}
