package project.board.web.post.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.board.domain.post.Post;
import project.board.domain.post.repository.PostRepository;
import project.board.domain.user.User;
import project.board.domain.user.repository.UserRepository;
import project.board.web.post.dto.PostInfoDto;
import project.board.web.post.form.PostRegForm;
import project.board.web.user.exception.UserException;
import project.board.web.user.exception.UserExceptionType;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public void registration(PostRegForm form, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_USER));
        Post post = form.toEntity();
        post.addUser(user);
    }

    @Override
    public List<PostInfoDto> findAll() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> modelMapper.map(post, PostInfoDto.class)).collect(Collectors.toList());
    }

    @Override
    public PostInfoDto getPostInfoDto(Long id) {
        return null;
    }
}
