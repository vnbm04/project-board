package project.board.web.user.service;

import project.board.web.user.dto.UserInfoDto;
import project.board.web.user.form.UserChangePwdForm;
import project.board.web.user.form.UserEditForm;
import project.board.web.user.form.UserRecoveryPwdForm;
import project.board.web.user.form.UserSignUpForm;

public interface UserService {

    /**
     * 회원가입
     * 회원조회
     * 회원수정
     * 회원탈퇴
     */

    void join(UserSignUpForm form);
    void recoveryAccount(UserRecoveryPwdForm form);
    void editAccount(UserEditForm form);
    void changePassword(Long id, String password);
    UserInfoDto getUserInfoDto(Long id);
    void deleteUser(Long id);

    boolean isDuplicateEmail(String email);
    boolean isValidEmail(String email);
    boolean isValidPwd(Long id, String password);

}
