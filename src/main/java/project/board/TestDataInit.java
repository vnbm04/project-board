package project.board;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.board.domain.user.Role;
import project.board.domain.user.User;
import project.board.domain.user.repository.UserRepository;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

        userRepository.save(user);
        userRepository.save(user2);
    }
}
