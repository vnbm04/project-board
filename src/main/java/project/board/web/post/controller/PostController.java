package project.board.web.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.board.security.PrincipalDetails;
import project.board.web.post.dto.PostInfoDto;
import project.board.web.post.dto.PostSearchCondition;
import project.board.web.post.form.PostRegForm;
import project.board.web.post.service.PostService;

import java.util.List;

@Controller
@RequestMapping("/posts")
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public String list(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model,
                       @ModelAttribute("search") PostSearchCondition condition, Pageable pageable){

        if(principalDetails == null){
            return "redirect:/login-main";
        }

//        List<PostInfoDto> posts = postService.findAll();
        Page<PostInfoDto> posts = postService.findAllByPaging(condition, pageable);
        model.addAttribute("posts", posts);
        return "post/list";
    }

    @GetMapping("/registration")
    public String registrationForm(Model model){
        model.addAttribute("post", new PostRegForm());
        return "post/reg-form";
    }

    @PostMapping("/registration")
    public String registration(@Validated @ModelAttribute("post") PostRegForm form, BindingResult bindingResult,
                               @AuthenticationPrincipal PrincipalDetails principalDetails){

        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "post/reg-form";
        }

        // 게시글 정보 저장
        postService.registration(form, principalDetails.getUserLoginDto().getId());
        return "redirect:/posts";
    }

    @GetMapping("/search")
    public String searchPost(@ModelAttribute("search") PostSearchCondition condition,
                             RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("keyword", condition.getKeyword());
        redirectAttributes.addAttribute("startDate", condition.getStartDate());
        redirectAttributes.addAttribute("endDate", condition.getEndDate());
        redirectAttributes.addAttribute("page", condition.getPage());
        return "redirect:/posts";
    }

}
