package project.board.web.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.board.domain.user.User;
import project.board.domain.user.repository.UserRepository;
import project.board.web.user.exception.UserException;
import project.board.web.user.exception.UserExceptionType;
import project.board.web.user.form.UserRecoveryPwdForm;
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
    public void recoveryAccount(UserRecoveryPwdForm form) {
        User user = userRepository.findByEmail(form.getEmail()).orElseThrow(() -> new UserException(UserExceptionType.ALREADY_EXIST_EMAIL));
        user.updateLoginDate();
        user.updatePassword(form.getNewPassword());
        user.encodePassword(passwordEncoder);
    }

    @Override
    public boolean isDuplicateEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean isValidEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
