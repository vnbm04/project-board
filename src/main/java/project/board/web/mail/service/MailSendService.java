package project.board.web.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.board.domain.mailAuth.MailAuth;
import project.board.web.mailAuth.dto.MailAuthDto;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
@Transactional
@RequiredArgsConstructor
public class MailSendService {

    private final JavaMailSender javaMailSender;

    /**
     * 인증코드 전송
     */
    public void sendAuthCode(MailAuthDto mailAuthDto){

        MimeMessage mail = javaMailSender.createMimeMessage();

        String sender = "bitnal@bitnalcorp.com";

        String subject = "Bitnal Dental Studio Identity";

        String content =
                "<div style=\"width: 500px\">\n" +
                "<h2 style=\"color:blue\">Activate your Bitnal account</h2>\n" +
                "<span style=\"font-size: 14px\"><strong>Please enter the verification code.</strong></span><br>\n" +
                "<span style=\"font-size: 14px\"><strong>Verification code is : " + mailAuthDto.getAuthCode() +
                "</strong></span><br><br>\n" +
                "<hr><br>\n" +
                "<span style=\"font-size: 14px\"><strong>Bitnal Dental Studio</strong></span><br>\n" +
                "<span>This email is automatically sent. Please do not reply to this email.</span><br>\n" +
                "<span>If you have any questions about the bitnal service,<br>\n" +
                "please contact us via dhkim@bitnalcorp.com</span>\n" +
                "</div>";

        try {
            mail.setSubject(subject, "utf-8");
            mail.setText(content, "utf-8", "html");
            mail.setFrom(new InternetAddress(sender));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(mailAuthDto.getEmail()));
            javaMailSender.send(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}
