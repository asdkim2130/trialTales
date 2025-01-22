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

    protected Member() {
    }

    public Member(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = (nickname == null) ? username : nickname; // nickname이 없으면 username으로 설정
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

    public boolean authenticate(String rawPassword) {
        String hashedInputPassword = SecurityUtils.sha256Encrypt(rawPassword);
        return password.equals(hashedInputPassword);
    }
}
