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
import project.board.web.mailAuth.dto.MailAuthDto;
import project.board.web.mailAuth.message.MailAuthMessage;
import project.board.web.mailAuth.service.MailAuthService;
import project.board.web.user.form.UserSignUpForm;
import project.board.web.user.service.UserService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final MailAuthService mailAuthService;

    /**
     * 회원가입
     */
    @GetMapping("sign-up")
    public String signUpForm(Model model){
        model.addAttribute("user", new UserSignUpForm());
        return "user/sign-up-form";
    }

    @PostMapping("sign-up")
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


        // 회원가입
        userService.join(form);

        return "redirect:/login";
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

        return new ResponseEntity<>(new Message(MailAuthMessage.SEND_SUCCESS), HttpStatus.OK);
    }
}
