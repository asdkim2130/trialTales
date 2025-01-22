package tt.trialTales;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import tt.trialTales.Application.ApplicationRequest;
import tt.trialTales.Application.ApplicationResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }


    @DisplayName("Application 생성")
    @Test
    public void application생성테스트(){
        Long userId = 1L;
        Long campaignId = 11L;


        ApplicationResponse response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new ApplicationRequest(userId, campaignId, "url", false))
                .when()
                .post("applications")
                .then().log().all()
                .extract()
                .as(ApplicationResponse.class);

        assertThat(response.snsUrl()).isEqualTo("url");
        assertThat(response.isApproved()).isEqualTo(false);
    }

}