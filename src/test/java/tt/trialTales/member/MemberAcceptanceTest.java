package tt.trialTales.member;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tt.JwtProvider;
import tt.trialTales.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberAcceptanceTest extends AcceptanceTest {

    @Autowired
    JwtProvider jwtProvider;

    @Test
    void createMember() {
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateMemberRequest("doraemon1", "doradora123", null, null))
                .when()
                .post("/members")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void loginTest() {
        // given
        final String username = "doraemon";
        final String password = "dora!23";

        // 회원 가입
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateMemberRequest(username, password, "도라에몽", null))
                .when()
                .post("/members")
                .then().log().all()
                .statusCode(200);

        LoginResponse loginResponse = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(username, password))
                .when()
                .post("/login")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);

        // then
        assertThat(loginResponse.accessToken()).isNotNull();
        assertThat(jwtProvider.isValidToken(loginResponse.accessToken())).isTrue();
    }

    @Test
    void adminDeleteMember() {
        // given
        final String adminUsername = "admin";
        final String adminPassword = "admin123";
        final String memberUsername = "memberToDelete";
        final String memberPassword = "member123";

        // 회원 가입 (관리자, 일반 사용자)
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateMemberRequest(adminUsername, adminPassword, "관리자", Role.ADMIN))
                .when()
                .post("/members")
                .then().log().all()
                .statusCode(200);

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateMemberRequest(memberUsername, memberPassword, "탈퇴회원", null))
                .when()
                .post("/members")
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

        // 관리자 회원 탈퇴 요청
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .param("username", memberUsername)
                .when()
                .delete("/members")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void userDeleteOwnAccount() {
        // given
        final String username = "userToDelete";
        final String password = "user123";

        // 회원 가입
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateMemberRequest(username, password, "사용자", null))
                .when()
                .post("/members")
                .then().log().all()
                .statusCode(200);

        // 로그인 후 토큰 얻기
        LoginResponse loginResponse = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(username, password))
                .when()
                .post("/login")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);

        String userToken = loginResponse.accessToken();

        // 자기 자신 탈퇴 요청
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + userToken)
                .param("username", username)
                .when()
                .delete("/members")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void adminUpdateNickname() {
        // given
        final String adminUsername = "admin";
        final String adminPassword = "admin123";
        final String targetUsername = "userToUpdateNickname";

        // 회원 가입 (관리자, 일반 사용자)
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateMemberRequest(adminUsername, adminPassword, "관리자", Role.ADMIN))
                .when()
                .post("/members")
                .then().log().all()
                .statusCode(200);

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateMemberRequest(targetUsername, "user123", "사용자", null))
                .when()
                .post("/members")
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

        // 관리자 닉네임 수정 요청
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .body("새로운닉네임")
                .when()
                .put("/members/nickname")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void userUpdateOwnNickname() {
        // given
        final String username = "userToUpdateNickname";
        final String password = "user123";

        // 회원 가입
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateMemberRequest(username, password, "사용자", null))
                .when()
                .post("/members")
                .then().log().all()
                .statusCode(200);

        // 로그인 후 토큰 얻기
        LoginResponse loginResponse = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(username, password))
                .when()
                .post("/login")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);

        String userToken = loginResponse.accessToken();

        // 자기 자신 닉네임 수정 요청
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + userToken)
                .body("새로운닉네임")
                .when()
                .put("/members/nickname")
                .then().log().all()
                .statusCode(200);
    }
}
