package project.board.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project.board.web.user.dto.UserLoginDto;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class PrincipalDetails implements UserDetails {

    private UserLoginDto userLoginDto;

    public PrincipalDetails(UserLoginDto userLoginDto) {
        this.userLoginDto = userLoginDto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(userLoginDto.getRole().name()));
        return roles;
    }

    @Override
    public String getPassword() {
        return userLoginDto.getPassword();
    }

    @Override
    public String getUsername() {
        return userLoginDto.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 마지막 로그인으로부터
     * 1년을 초과한 경우 휴면 계정으로 전환
     */
    @Override
    public boolean isAccountNonLocked() {

        LocalDate lastLoginDate = userLoginDto.getLoginDate();
        LocalDate currentDate = LocalDate.now();

        // 최초 로그인
        if(lastLoginDate == null){
            return true;
        }

        Long standardDate = 365L;
        long period = ChronoUnit.DAYS.between(lastLoginDate, currentDate);

        // 마지막 로그인으로부터 1년을 초과한 경우
        if(period > standardDate){
            return false;
        }

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
