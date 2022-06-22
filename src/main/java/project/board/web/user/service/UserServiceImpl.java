package project.board.web.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.board.domain.user.User;
import project.board.domain.user.repository.UserRepository;
import project.board.web.user.form.UserSignUpForm;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void join(UserSignUpForm userSignUpForm) {
        User user = userSignUpForm.toEntity();
        user.encodePassword(passwordEncoder);
        user.addAuthority();
        userRepository.save(user);
    }

    @Override
    public boolean isDuplicateEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
