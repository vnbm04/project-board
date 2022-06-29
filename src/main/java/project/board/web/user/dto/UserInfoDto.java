package project.board.web.user.dto;

import lombok.Getter;
import lombok.Setter;
import project.board.domain.user.User;

@Getter @Setter
public class UserInfoDto {

    private Long id;
    private String username;
    private String nickname;
    private String email;

    /**
     * DTO to Entity
     */
    public User toEntity(){
        return User.builder()
                .username(username)
                .nickname(nickname)
                .email(email)
                .build();
    }

}
