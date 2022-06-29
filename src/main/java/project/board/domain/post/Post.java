package project.board.domain.post;

import lombok.*;
import project.board.domain.base.BaseEntity;
import project.board.domain.user.User;

import javax.persistence.*;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "post_seq_generator",
        sequenceName = "post_seq"
)
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String content;

    @Builder.Default
    private Long hit = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 연관관계 편의 메서드
     */
    public void addUser(User user){
        if(this.user != null){
            this.user.getPosts().remove(this);
        }
        this.user = user;
        user.getPosts().add(this);
    }


    /**
     * 게시글 정보 수정
     */
    public void updateTitle(String title){
        this.title = title;
    }

    public void updateContent(String content){
        this.content = content;
    }

    public void updateHit(){
        this.hit++;
    }
}
