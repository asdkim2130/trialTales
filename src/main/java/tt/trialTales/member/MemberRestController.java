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
    @PostMapping("/members/signup")
    public void create(@Valid @RequestBody CreateMemberRequest request) {
        memberService.create(request);  // 회원가입 처리
    }

    // 로그인 API
    @PostMapping("members/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return memberService.login(request);  // 로그인 처리
    }

    // 프로필 조회 API
    @GetMapping("/members/profile")
    public MemberProfileResponse getProfile(@RequestHeader("Authorization") String token) {
        Member member = loginMemberResolver.resolveMemberFromToken(token);
        return memberService.getProfile(member);
    }

    // 회원 탈퇴 API
    @DeleteMapping("/members/profile")
    public void delete(@RequestHeader("Authorization") String token, @RequestParam String username) {
        // 토큰에서 회원 정보 추출 후 탈퇴 처리
        Member member = loginMemberResolver.resolveMemberFromToken(token);
        memberService.deleteMember(username, member);
    }

    // 닉네임 수정 API
    @PutMapping("/members/profile")
    public void updateNickname(@RequestHeader("Authorization") String token, @RequestBody String newNickname) {
        // 토큰에서 회원 정보 추출 후 닉네임 수정 처리
        Member member = loginMemberResolver.resolveMemberFromToken(token);
        memberService.updateNickname(member.getUsername(), newNickname, member);
    }
}
