package project.board.security.custom;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import project.board.domain.user.Role;
import project.board.security.PrincipalDetails;
import project.board.web.user.dto.UserLoginDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        setDefaultTargetUrl("/");
        String customTargetUrl = "/admin";

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        UserLoginDto userLoginDto = principalDetails.getUserLoginDto();

        if(savedRequest != null){
            checkUserRole(request, response, customTargetUrl, savedRequest, userLoginDto);
        }else{
            checkUserRole(request, response, customTargetUrl, userLoginDto);
        }
    }

    private void checkUserRole(HttpServletRequest request, HttpServletResponse response, String customTargetUrl, SavedRequest savedRequest, UserLoginDto userLoginDto) throws IOException {
        if(userLoginDto.getRole().equals(Role.ROLE_USER)) {
            String targetUrl = savedRequest.getRedirectUrl();
            redirectStrategy.sendRedirect(request, response, targetUrl);
        }else{
            redirectStrategy.sendRedirect(request, response, customTargetUrl);
        }
    }

    private void checkUserRole(HttpServletRequest request, HttpServletResponse response, String customTargetUrl, UserLoginDto userLoginDto) throws IOException {
        if(userLoginDto.getRole().equals(Role.ROLE_USER)) {
            redirectStrategy.sendRedirect(request, response, getDefaultTargetUrl());
        }else{
            redirectStrategy.sendRedirect(request, response, customTargetUrl);
        }
    }

}
