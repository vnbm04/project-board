package project.board.security.custom;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.board.domain.user.User;
import project.board.domain.user.repository.UserRepository;
import project.board.security.PrincipalDetails;
import project.board.web.user.dto.UserLoginDto;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Invalid email or password"));
        UserLoginDto userLoginDto = modelMapper.map(user, UserLoginDto.class);
        return new PrincipalDetails(userLoginDto);
    }
}
