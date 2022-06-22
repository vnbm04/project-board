package project.board.web.mailAuth.service;

import project.board.web.mailAuth.dto.MailAuthDto;

public interface MailAuthService {

    /**
     * 인증정보 저장
     * 인증정보 검증
     */
    MailAuthDto createMailAuthCode(String email);
    Boolean isValidEmailAndAuthCode(String email, String authCode);
}
