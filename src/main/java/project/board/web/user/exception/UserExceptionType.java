package project.board.web.user.exception;

import org.springframework.http.HttpStatus;
import project.board.exception.BaseExceptionType;

public enum UserExceptionType implements BaseExceptionType {

    /**
     * 회원가입, 로그인 시
     */
    ALREADY_EXIST_EMAIL(600, HttpStatus.OK, "Duplicate email"),
    NOT_FOUND_USER(601, HttpStatus.OK, "Invalid user");

    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    UserExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

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
