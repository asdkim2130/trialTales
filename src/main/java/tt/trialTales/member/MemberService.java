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

    /**
     * 회원을 생성하는 메서드
     *
     * @param request 회원 생성 요청 정보 (username, password, nickname)
     */
    public void create(CreateMemberRequest request) {
        // 비밀번호는 SHA-256으로 암호화하여 저장
        memberRepository.save(new Member(
                request.username(),
                SecurityUtils.sha256Encrypt(request.password()),
                request.nickname()));
    }

    /**
     * 로그인 요청을 처리하는 메서드
     *
     * @param request 로그인 요청 정보 (username, password)
     * @return LoginResponse 로그인 응답 정보 (accessToken)
     */
    public LoginResponse login(LoginRequest request) {
        // 사용자가 존재하는지 확인
        Member member = memberRepository.findByUsername(request.username())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 틀렸습니다."));

        // 비밀번호 확인
        if (!member.authenticate(request.password())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 틀렸습니다.");
        }

        // 로그인 성공 시 JWT 토큰 생성하여 반환
        return new LoginResponse(jwtProvider.createToken(member.getUsername()));
    }

    /**
     * 회원 탈퇴를 처리하는 메서드
     *
     * @param username 탈퇴할 회원의 username
     */
    public void deleteMember(String username) {
        // username으로 회원을 찾아서 삭제
        Member member = findByUsername(username);
        memberRepository.delete(member);
    }

    /**
     * 사용자의 닉네임을 수정하는 메서드
     *
     * @param username 사용자의 username (토큰에서 추출)
     * @param newNickname 새로운 닉네임
     */
    public void updateNickname(String username, String newNickname) {
        // username으로 회원을 찾음
        Member member = findByUsername(username);

        // 새로운 닉네임을 설정
        member = new Member(member.getUsername(), member.getPassword(), newNickname);

        // 수정된 회원 정보 저장
        memberRepository.save(member);
    }

    /**
     * username으로 회원을 찾는 메서드
     *
     * @param username 사용자 이름
     * @return Member 객체 (회원 정보)
     * @throws NoSuchElementException 회원을 찾을 수 없을 경우 예외 발생
     */
    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("회원을 찾을 수 없습니다 username: " + username));
    }
}
