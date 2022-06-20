package project.board.security.custom;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.board.domain.user.User;
import project.board.domain.user.repository.UserRepository;
import project.board.security.PrincipalDetails;

@Component
@RequiredArgsConstructor
@Transactional
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();
        String password = (String) authentication.getCredentials();

        PrincipalDetails principalDetails = (PrincipalDetails) userDetailsService.loadUserByUsername(email);

        // 비밀번호 검증
        if(!passwordEncoder.matches(password, principalDetails.getPassword())){
            throw new BadCredentialsException("Invalid email or password");
        }

        // 휴면 계정 검증
        if(!principalDetails.isAccountNonLocked()){
            throw new LockedException("User account is locked");
        }

        // 로그인 시간 갱신
        User user = userRepository.findById(principalDetails.getUserLoginDto().getId()).orElse(null);
        user.updateLoginDate();

        return new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
