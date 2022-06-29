package project.board.web.post.service;

import project.board.domain.user.User;
import project.board.web.post.dto.PostInfoDto;
import project.board.web.post.form.PostRegForm;

import java.util.List;

public interface PostService {

    /**
     * 게시글 등록
     * 게시글 조회
     * 게시글 수정
     * 게시글 삭제
     */

    void registration(PostRegForm form, Long userId);
    List<PostInfoDto> findAll();
    PostInfoDto getPostInfoDto(Long id);
}
