package project.board.web.mailAuth.exception;

import org.springframework.http.HttpStatus;
import project.board.exception.BaseExceptionType;

public enum MailAuthExceptionType implements BaseExceptionType {

    /**
     * 메일 전송 시
     */
    NOT_FOUND_EMAIL(700, HttpStatus.OK, "Invalid email"),
    INVALID_URL(701, HttpStatus.OK, "Invalid email or verification code");


    MailAuthExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
