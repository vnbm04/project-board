package project.board.web.user.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class UserRecoveryPwdForm {

    @NotBlank
    private String email;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String rePassword;

    public UserRecoveryPwdForm(String email) {
        this.email = email;
    }
}
