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
                .post("/members/signup")
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
                .post("/members/signup")
                .then().log().all()
                .statusCode(200);

        LoginResponse loginResponse = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(username, password))
                .when()
                .post("/members/login")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);

        // then
        assertThat(loginResponse.accessToken()).isNotNull();
        assertThat(jwtProvider.isValidToken(loginResponse.accessToken())).isTrue();
    }

    @Test
    void getProfileTest() {
        // given
        final String username = "doraemon";
        final String password = "dora!23";
        final String nickname = "도라에몽";

        // 회원 가입
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateMemberRequest(username, password, nickname, null))
                .when()
                .post("/members/signup")
                .then().log().all()
                .statusCode(200);

        // 로그인해서 토큰 받기
        LoginResponse loginResponse = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(username, password))
                .when()
                .post("/members/login")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);

        // when: 로그인한 사용자로 프로필 조회
        MemberProfileResponse profileResponse = RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + loginResponse.accessToken())
                .when()
                .get("/members/profile")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MemberProfileResponse.class);

        // then
        assertThat(profileResponse.username()).isEqualTo(username);
        assertThat(profileResponse.nickname()).isEqualTo(nickname);
        assertThat(profileResponse.role()).isEqualTo(Role.USER);
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
                .post("/members/signup")
                .then().log().all()
                .statusCode(200);

        // 로그인 후 토큰 얻기
        LoginResponse loginResponse = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(username, password))
                .when()
                .post("/members/login")
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
                .delete("/members/profile")
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
                .post("/members/signup")
                .then().log().all()
                .statusCode(200);

        // 로그인 후 토큰 얻기
        LoginResponse loginResponse = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(username, password))
                .when()
                .post("/members/login")
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
                .put("/members/profile")
                .then().log().all()
                .statusCode(200);
    }
}
