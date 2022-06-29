package project.board.domain.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.board.domain.post.Post;
import project.board.web.post.dto.PostSearchCondition;

public interface PostRepositoryCustom {

    Page<Post> searchPosts(PostSearchCondition condition, Pageable pageable);

}
