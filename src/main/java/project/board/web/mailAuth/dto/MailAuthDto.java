package project.board.web.mailAuth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MailAuthDto {
    private String email;
    private String authCode;
}
