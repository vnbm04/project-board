package project.board.web.post.form;

import lombok.Getter;
import lombok.Setter;
import project.board.domain.post.Post;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class PostRegForm {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    /**
     * DTO To Entity
     */
    public Post toEntity(){
        return Post.builder()
                .title(title)
                .content(content)
                .build();
    }
}
