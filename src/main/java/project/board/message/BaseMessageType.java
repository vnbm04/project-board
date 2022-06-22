package project.board.message;

import org.springframework.http.HttpStatus;

public interface BaseMessageType {

    /**
     * Http 상태
     * Response 메시지
     */

    HttpStatus getHttpStatus();
    String getMessage();
}
