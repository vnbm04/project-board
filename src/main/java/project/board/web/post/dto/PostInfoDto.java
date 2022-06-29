package project.board.web.post.dto;

import lombok.Getter;
import lombok.Setter;
import project.board.domain.user.User;

import java.time.LocalDate;

@Getter @Setter
public class PostInfoDto {
    private Long id;
    private String title;
    private String content;
    private LocalDate lastModifiedDate;
    private Long hit;
    private User user;
}
