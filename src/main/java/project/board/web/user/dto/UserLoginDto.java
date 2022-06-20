package project.board.web.user.dto;

import lombok.Getter;
import lombok.Setter;
import project.board.domain.user.Role;

import java.time.LocalDate;

@Getter @Setter
public class UserLoginDto {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String password;
    private Role role;
    private LocalDate LoginDate;
}
