package project.board.web.mailAuth.exception;

import project.board.exception.BaseException;
import project.board.exception.BaseExceptionType;

public class MailAuthException extends BaseException {

    private BaseExceptionType baseExceptionType;

    public MailAuthException(BaseExceptionType baseExceptionType) {
        this.baseExceptionType = baseExceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return baseExceptionType;
    }
}
