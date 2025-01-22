package tt.trialTales.member;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberRestController {

    private final MemberService memberService;

    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원 가입 API
    // POST /members
    // { "username": "", "password": "", "nickname": "" }
    @PostMapping("/members")
    public void create(@Valid @RequestBody CreateMemberRequest request) {
        memberService.create(request);
    }

    // 로그인 API
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return memberService.login(request);
    }

    // 회원 탈퇴 API
    @DeleteMapping("/members/{username}")
    public void delete(@PathVariable String username) {
        memberService.deleteMember(username);  // username으로 회원 탈퇴
    }
}
