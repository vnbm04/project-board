package project.board.web.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.board.message.Message;
import project.board.web.mail.service.MailSendService;
import project.board.web.mailAuth.dto.MailAuthDto;
import project.board.web.mailAuth.exception.MailAuthException;
import project.board.web.mailAuth.exception.MailAuthExceptionType;
import project.board.web.mailAuth.message.MailAuthMessage;
import project.board.web.mailAuth.service.MailAuthService;
import project.board.web.user.form.UserForgotPwdForm;
import project.board.web.user.form.UserRecoveryPwdForm;
import project.board.web.user.form.UserSignUpForm;
import project.board.web.user.service.UserService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final MailAuthService mailAuthService;
    private final MailSendService mailSendService;

    /**
     * 회원가입
     */
    @GetMapping("/sign-up")
    public String signUpForm(Model model){
        model.addAttribute("user", new UserSignUpForm());
        return "user/sign-up-form";
    }

    @PostMapping("/sign-up")
    public String signUp(@Validated @ModelAttribute("user") UserSignUpForm form, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "user/sign-up-form";
        }

        // 이메일 중복 검증
        if(userService.isDuplicateEmail(form.getEmail())){
            bindingResult.rejectValue("email", "DuplicateEmail");
            log.info("errors={}", bindingResult);
            return "user/sign-up-form";
        }

        // 인증코드 검증
        if(!mailAuthService.isValidEmailAndAuthCode(form.getEmail(), form.getAuthCode())){
            bindingResult.rejectValue("authCode", "InvalidEmailOrAuthCode");
            log.info("errors={}", bindingResult);
            return "user/sign-up-form";
        }

        // 회원가입
        userService.join(form);

        return "redirect:/loginForm";
    }

    /**
     * 인증코드 전송
     */
    @GetMapping("/auth")
    @ResponseBody
    public ResponseEntity<Message> sendAuthCode(@RequestParam String email){

        // 이메일 중복 검증
        if(userService.isDuplicateEmail(email)){
            return new ResponseEntity<>(new Message(MailAuthMessage.ALREADY_EXIST_EMAIL), HttpStatus.OK);
        }

        // 이메일 인증 정보 생성
        MailAuthDto mailAuthDto = mailAuthService.createMailAuthCode(email);

        // 전송 횟수 초과 시
        if(mailAuthDto == null){
            return new ResponseEntity<>(new Message(MailAuthMessage.ALREADY_SENT_EMAIL), HttpStatus.OK);
        }

        // 인증코드 전송
        mailSendService.sendAuthCode(mailAuthDto);
        return new ResponseEntity<>(new Message(MailAuthMessage.SEND_SUCCESS), HttpStatus.OK);
    }

    /**
     * 비밀번호 변경 & 휴면 계정 전환
     */
    @GetMapping("/forgot-password")
    public String forgotPasswordForm(Model model){
        model.addAttribute("user", new UserForgotPwdForm());
        return "user/forgot-password-form";
    }

    @PostMapping("/forgot-password")
    public String sendChangePwdForm(@Validated @ModelAttribute("user") UserForgotPwdForm form, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "user/forgot-password-form";
        }

        // 이메일 유효성 검증
        if(!userService.isValidEmail(form.getEmail())){
            bindingResult.rejectValue("email", "InvalidEmail");
            log.info("errors={}", bindingResult);
            return "user/forgot-password-form";
        }

        // 비밀번호를 변경할 수 있는 URL 전송
        MailAuthDto mailAuthDto = mailAuthService.getMailAuthDto(form.getEmail());
        mailSendService.sendChangePwdURL(mailAuthDto);

        return "redirect:/loginForm";
    }

    /**
     * 비밀번호 변경 및 휴면 계정 활성화
     */
    @GetMapping("/account-recovery")
    public String recoveryPwdForm(String email, String authCode, Model model){

        // Url 검증
        if(!mailAuthService.isValidEmailAndAuthCode(email, authCode)){
            throw new MailAuthException(MailAuthExceptionType.INVALID_URL);
        }

        model.addAttribute("user", new UserRecoveryPwdForm(email));
        return "user/recovery-password-form";
    }

    @PostMapping("/account-recovery")
    public String recoveryPwd(@Validated @ModelAttribute("user") UserRecoveryPwdForm form, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "user/recovery-password-form";
        }

        // 비밀번호 일치 검증
        if(!form.getNewPassword().equals(form.getRePassword())){
            bindingResult.rejectValue("rePassword", "PasswordMismatch");
            log.info("errors={}", bindingResult);
            return "user/recovery-password-form";
        }

        // 회원정보 수정
        userService.recoveryAccount(form);
        return "redirect:/loginForm";
    }

}
