package project.board;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.board.domain.post.Post;
import project.board.domain.post.repository.PostRepository;
import project.board.domain.user.Role;
import project.board.domain.user.User;
import project.board.domain.user.repository.UserRepository;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostRepository postRepository;

    @PostConstruct
    public void init(){
        User user = User.builder()
                .email("user")
                .password(passwordEncoder.encode("123!"))
                .role(Role.ROLE_USER)
                .build();

        User user2 = User.builder()
                .email("admin")
                .password(passwordEncoder.encode("123!"))
                .role(Role.ROLE_ADMIN)
                .build();

        User user3 = User.builder()
                .email("vnbm04@gmail.com")
                .username("김동현")
                .nickname("김동현 닉네임")
                .password(passwordEncoder.encode("123!"))
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(user3);

        for(int i = 0; i < 50; i++){
            Post post = Post.builder()
                    .title("타이틀" + i)
                    .content("내용" + i)
                    .user(user3)
                    .build();
            postRepository.save(post);
        }
    }
}
