package project.board.web.user.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class UserEditForm {

    @NotBlank
    private String username;

    @NotBlank
    private String nickname;

    @NotBlank
    private String email;
}
