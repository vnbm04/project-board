package project.board.web.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserLoginDto {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String password;
    private String role;
    private String LoginDate;
}
