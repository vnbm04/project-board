package project.board.web.user.message;

import org.springframework.http.HttpStatus;
import project.board.message.BaseMessageType;

public enum UserMessage implements BaseMessageType {

    /**
     * 회원정보 수정 시
     */
    DELETE_USER_ACCOUNT(HttpStatus.OK, "successfully withdraw");

    UserMessage(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private String message;

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
