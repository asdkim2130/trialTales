package tt;

import org.springframework.stereotype.Component;
import tt.trialTales.member.Member;
import tt.trialTales.member.MemberService;

@Component
public class LoginMemberResolver {
    private static final String BEARER_PREFIX = "Bearer ";
    public static final String INVALID_TOKEN_MESSAGE = "로그인 정보가 유효하지 않습니다";

    private final JwtProvider jwtProvider;
    private final MemberService memberService;

    public LoginMemberResolver(JwtProvider jwtProvider, MemberService memberService) {
        this.jwtProvider = jwtProvider;
        this.memberService = memberService;
    }

    public Member resolveMemberFromToken(String bearerToken) {
        String token = extractToken(bearerToken);
        if (!jwtProvider.isValidToken(token)) {
            throw new IllegalArgumentException(INVALID_TOKEN_MESSAGE);
        }
        String username = jwtProvider.getSubject(token);
        return memberService.findByUsername(username);
    }

    private String extractToken(String bearerToken) {
        if (bearerToken == null || !bearerToken.startsWith(BEARER_PREFIX)) {
            throw new IllegalArgumentException(INVALID_TOKEN_MESSAGE);
        }
        return bearerToken.substring(BEARER_PREFIX.length());
    }
}