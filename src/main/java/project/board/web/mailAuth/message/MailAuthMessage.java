package project.board.web.mailAuth.message;

import org.springframework.http.HttpStatus;
import project.board.message.BaseMessageType;

public enum MailAuthMessage implements BaseMessageType {

    /**
     * 인증코드 전송 시
     */
    ALREADY_EXIST_EMAIL(HttpStatus.OK, "Duplicate email"),
    ALREADY_SENT_EMAIL(HttpStatus.OK, "Already sent email"),
    SEND_SUCCESS(HttpStatus.OK, "successfully sent email");

    private HttpStatus httpStatus;
    private String message;

    MailAuthMessage(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
