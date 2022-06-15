package project.board.domain.user;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.board.domain.base.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@SequenceGenerator(
        name = "user_seq_generator",
        sequenceName = "user_seq"
)
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_generator")
    @Column(name = "user_id")
    private Long id;

    private String username;
    private String nickname;
    private String email;
    private String password;
    private String role;
    private LocalDate loginDate;

    /**
     * 회원가입 시, User 권한을 부여하는 메서드
     */
    public void addAuthority(){
        this.role = "ROLE_USER";
    }

    /**
     * 회원가입 시, 비밀번호를 암호화하는 메서드
     */
    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }

    /**
     * 회원정보 수정
     */
    public void updateUsername(String username){
        this.username = username;
    }

    public void updateNickname(String nickname){
        this.nickname = nickname;
    }

    public void updateLoginDate(){
        this.loginDate = LocalDate.now();
    }
}
