package project.board.web.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class UserLoginDto {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String password;
    private String role;
    private LocalDate LoginDate;
}
