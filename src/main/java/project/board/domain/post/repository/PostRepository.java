package project.board.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.board.domain.post.Post;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

}
