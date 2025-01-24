package tt.trialTales.member;

import org.springframework.dao.DataIntegrityViolationException;
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

    // 회원 가입 처리 메서드
    public void create(CreateMemberRequest request) {
        Role role = request.role() != null ? request.role() : Role.USER; // 기본은 USER
        memberRepository.save(new Member(
                request.username(),
                SecurityUtils.sha256Encrypt(request.password()),  // 비밀번호 암호화
                request.nickname(),
                role
        ));
    }

    // 로그인 처리 메서드
    public LoginResponse login(LoginRequest request) {
        // 사용자 정보 확인 후 로그인 처리
        Member member = memberRepository.findByUsername(request.username())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 틀렸습니다."));

        if (!member.authenticate(request.password())) {  // 비밀번호 확인
            throw new IllegalArgumentException("아이디 또는 비밀번호가 틀렸습니다.");
        }

        // JWT 토큰 생성 및 반환
        return new LoginResponse(jwtProvider.createToken(member.getUsername(), member.getRole()));
    }

    // 회원 탈퇴 처리 메서드
    public void deleteMember(String username, Member requestingMember) {
        // 관리자만 다른 사용자를 탈퇴시킬 수 있음
        if (requestingMember.getRole() == Role.ADMIN) {
            Member member = findByUsername(username); // 회원 찾기
            memberRepository.delete(member);  // 회원 삭제
        } else {
            // 일반 사용자는 자기 자신만 탈퇴할 수 있음
            if (!requestingMember.getUsername().equals(username)) {
                throw new IllegalArgumentException("자신의 계정만 탈퇴할 수 있습니다.");
            }
            memberRepository.delete(requestingMember);  // 자신 삭제
        }
    }

    // 닉네임 수정 처리 메서드
    public void updateNickname(String username, String newNickname, Member requestingMember) {
        // 관리자나 본인만 닉네임을 변경할 수 있음
        if (requestingMember.getRole() == Role.USER && !requestingMember.getUsername().equals(username)) {
            throw new IllegalArgumentException("본인만 닉네임을 변경할 수 있습니다.");
        }

        // 회원 정보 조회
        Member member = findByUsername(username);  // 회원 정보 조회
        member.setNickname(newNickname);  // 기존 객체에서 닉네임 수정

        try {
            // 닉네임 수정된 회원 저장
            memberRepository.save(member);
        } catch (DataIntegrityViolationException e) {
            // 닉네임 중복 시 처리
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }
    }

    // 사용자 이름으로 회원을 찾는 메서드
    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("회원을 찾을 수 없습니다 username: " + username));
    }
}
