package tt.trialTales;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tt.JwtProvider;
import tt.trialTales.member.Role;

@SpringBootTest
public class JwtTest {

    @Autowired
    JwtProvider jwtProvider;

    @Test
    void name() {
        String token = jwtProvider.createToken("", Role.USER);
        System.out.println("token = " + token);
    }
}
