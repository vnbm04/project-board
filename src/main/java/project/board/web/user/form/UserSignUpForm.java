package project.board.web.user.form;

import lombok.Getter;
import lombok.Setter;
import project.board.domain.user.User;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class UserSignUpForm {

    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String nickname;

    @NotBlank
    private String password;

    @NotBlank
    private String authCode;

    public User toEntity(){
        return User.builder()
                .email(email)
                .username(username)
                .nickname(nickname)
                .password(password)
                .build();
    }
}
