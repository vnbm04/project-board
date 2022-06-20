package project.board.web.user.exception;

import project.board.exception.BaseException;
import project.board.exception.BaseExceptionType;

public class UserException extends BaseException {

    private BaseExceptionType baseExceptionType;

    public UserException(BaseExceptionType baseExceptionType) {
        this.baseExceptionType = baseExceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return baseExceptionType;
    }
}
