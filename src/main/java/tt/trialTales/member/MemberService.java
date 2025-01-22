package tt.trialTales.member;

import org.springframework.stereotype.Service;
import tt.JwtProvider;
import tt.SecurityUtils;

import java.util.NoSuchElementException;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public MemberService(MemberRepository memberRepository, JwtProvider jwtProvider) {
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
    }

    // 회원 생성
    public void create(CreateMemberRequest request) {
        memberRepository.save(new Member(
                request.username(),
                SecurityUtils.sha256Encrypt(request.password()),
                request.nickname()));
    }

    public LoginResponse login(LoginRequest request) {
        Member member = memberRepository.findByUsername(request.username())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 틀렸습니다."));

        if (!member.authenticate(request.password())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 틀렸습니다.");
        }

        return new LoginResponse(jwtProvider.createToken(member.getUsername()));
    }

    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("회원을 찾을 수 없습니다 username: " + username));
    }

    // 회원 탈퇴
    public void deleteMember(String username) {
        // findByUsername 메서드를 사용하여 username으로 회원 찾기
        Member member = findByUsername(username);

        // 회원 정보 삭제
        memberRepository.delete(member);
    }
}
