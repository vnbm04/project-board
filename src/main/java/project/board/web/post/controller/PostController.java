package project.board.web.post.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.board.security.PrincipalDetails;

@Controller
@RequestMapping("/posts")
public class PostController {

    @GetMapping
    public String list(@AuthenticationPrincipal PrincipalDetails principalDetails){

        if(principalDetails == null){
            return "redirect:/login-main";
        }

        return "main";
    }

}
