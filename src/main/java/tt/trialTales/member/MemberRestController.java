package tt.trialTales.member;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import tt.LoginMemberResolver;

@RestController
public class MemberRestController {

    private final MemberService memberService;
    private final LoginMemberResolver loginMemberResolver;

    public MemberRestController(MemberService memberService, LoginMemberResolver loginMemberResolver) {
        this.memberService = memberService;
        this.loginMemberResolver = loginMemberResolver;
    }

    // 회원 가입 API
    @PostMapping("/members")
    public void create(@Valid @RequestBody CreateMemberRequest request) {
        memberService.create(request);  // 회원가입 처리
    }

    // 로그인 API
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return memberService.login(request);  // 로그인 처리
    }

    // 회원 탈퇴 API
    @DeleteMapping("/members")
    public void delete(@RequestHeader("Authorization") String token) {
        // 토큰에서 username 추출
        Member member = loginMemberResolver.resolveMemberFromToken(token);
        String username = member.getUsername(); // username을 얻기

        memberService.deleteMember(username);  // 회원 탈퇴 처리
    }

    // 닉네임 수정 API
    @PutMapping("/members/nickname")
    public void updateNickname(@RequestHeader(value = "Authorization") String token, @RequestBody String newNickname) {
        // 토큰에서 username 추출
        Member member = loginMemberResolver.resolveMemberFromToken(token);
        String username = member.getUsername(); // username을 얻기

        // 닉네임 변경 처리
        memberService.updateNickname(username, newNickname);
    }
}
