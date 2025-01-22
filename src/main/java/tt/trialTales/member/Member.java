package tt.trialTales.member;

import jakarta.persistence.*;
import tt.SecurityUtils;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username; // login ID

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private Role role; // 사용자 권한 (USER, ADMIN)

    protected Member() {
    }

    public Member(String username, String password, String nickname, Role role) {
        this.username = username;
        this.password = password;
        this.nickname = (nickname == null) ? username : nickname; // nickname이 없으면 username으로 설정
        this.role = role != null ? role : Role.USER; // 기본 권한은 USER
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public Role getRole() {
        return role;
    }

    public boolean authenticate(String rawPassword) {
        String hashedInputPassword = SecurityUtils.sha256Encrypt(rawPassword);
        return password.equals(hashedInputPassword);
    }
}
