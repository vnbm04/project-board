package project.board.domain.mailAuth;

import lombok.*;
import project.board.domain.base.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "mail_auth_seq_generator",
        sequenceName = "mail_auth_seq"
)
public class MailAuth extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mail_auth_seq_generator")
    @Column(name = "mail_auth_id")
    private Long id;

    private String email;
    private String authCode;
    private int count;

    /**
     * 메일 인증 코드의
     * 만료 상태를 결정하는 메서드
     */
    public boolean isAuthCodeExpired(){

        long period = ChronoUnit.MINUTES.between(this.getLastModifiedDate(), LocalDateTime.now());
        long standardTime = 30L;

        if(period > standardTime){
            return true;
        }

        return false;
    }

    /**
     * 메일 인증 정보 수정
     */
    public void increaseCount(){
        this.count++;
    }
}
