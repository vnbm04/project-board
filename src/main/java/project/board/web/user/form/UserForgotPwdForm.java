package project.board.web.user.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class UserForgotPwdForm {

    @NotBlank
    private String email;
}
