package project.board.exception;

import org.springframework.http.HttpStatus;

public interface BaseExceptionType {

    /**
     * 에러 코드
     * Http 상태
     * 에러 메시지
     */

    int getErrorCode();
    HttpStatus getHttpStatus();
    String getErrorMessage();
}
