package project.board.web.user.service;

import project.board.web.user.form.UserSignUpForm;

public interface UserService {

    /**
     * 회원가입
     * 회원조회
     * 회원수정
     * 회원탈퇴
     */

    void join(UserSignUpForm userSignUpForm);

    boolean isDuplicateEmail(String email);
}
