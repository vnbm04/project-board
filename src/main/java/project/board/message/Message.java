package project.board.message;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
public class Message {
    private HttpStatus httpStatus;
    private String message;

    public Message(BaseMessageType baseMessageType) {
        this.httpStatus = baseMessageType.getHttpStatus();
        this.message = baseMessageType.getMessage();
    }
}
