package project.board.web.user.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.board.domain.user.User;
import project.board.domain.user.repository.UserRepository;
import project.board.web.user.dto.UserInfoDto;
import project.board.web.user.exception.UserException;
import project.board.web.user.exception.UserExceptionType;
import project.board.web.user.form.UserEditForm;
import project.board.web.user.form.UserRecoveryPwdForm;
import project.board.web.user.form.UserSignUpForm;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

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
    public void editAccount(UserEditForm form) {
        User user = userRepository.findByEmail(form.getEmail()).orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_USER));
        user.updateUsername(form.getUsername());
        user.updateNickname(form.getNickname());
    }

    @Override
    public void changePassword(Long id, String password) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_USER));
        user.updatePassword(password);
        user.encodePassword(passwordEncoder);
    }

    @Override
    public UserInfoDto getUserInfoDto(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_USER));
        return modelMapper.map(user, UserInfoDto.class);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean isDuplicateEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean isValidEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean isValidPwd(Long id, String password) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_USER));
        if(passwordEncoder.matches(password, user.getPassword())){
            return true;
        }
        return false;
    }
}
