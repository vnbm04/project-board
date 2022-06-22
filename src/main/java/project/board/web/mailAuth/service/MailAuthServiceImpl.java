package project.board.web.mailAuth.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.board.domain.mailAuth.MailAuth;
import project.board.domain.mailAuth.repository.MailAuthRepository;
import project.board.web.mailAuth.dto.MailAuthDto;

@Service
@Transactional
@RequiredArgsConstructor
public class MailAuthServiceImpl implements MailAuthService {

    private final MailAuthRepository mailAuthRepository;
    private final ModelMapper modelMapper;

    @Override
    public MailAuthDto createMailAuthCode(String email) {

        MailAuth mailAuth = mailAuthRepository.findByEmail(email).orElse(null);

        if (mailAuth == null) {
            return toMailAuthDto(mailAuthRepository.save(createMailAuth(email)));
        }

        // 인증코드 만료 시
        if(mailAuth.isAuthCodeExpired()){
            mailAuthRepository.delete(mailAuth);
            return toMailAuthDto(mailAuthRepository.save(createMailAuth(email)));
        }

        // 전송 횟수 초과
        if(mailAuth.getCount() >= 2){
            return null;
        }

        mailAuth.increaseCount();
        return toMailAuthDto(mailAuth);
    }

    @Override
    public Boolean isValidEmailAndAuthCode(String email, String authCode) {
        return mailAuthRepository.existsByEmailAndAuthCode(email, authCode);
    }

    private MailAuthDto toMailAuthDto(MailAuth mailAuth) {
        return modelMapper.map(mailAuth, MailAuthDto.class);
    }

    private MailAuth createMailAuth(String email) {
        return MailAuth.builder()
                .email(email)
                .authCode(createTmpNumber())
                .build();
    }

    private String createTmpNumber(){
        long leftLimit = 111111L;
        long rightLimit = 999999L;
        Long tempNumber = new RandomDataGenerator().nextLong(leftLimit, rightLimit);
        return String.valueOf(tempNumber);
    }
}
