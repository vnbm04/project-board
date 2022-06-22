package project.board.web.mailAuth.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.board.domain.mailAuth.MailAuth;
import project.board.domain.mailAuth.repository.MailRepository;
import project.board.web.mailAuth.dto.MailAuthDto;

@Service
@Transactional
@RequiredArgsConstructor
public class MailAuthServiceImpl implements MailAuthService {

    private final MailRepository mailRepository;
    private final ModelMapper modelMapper;

    @Override
    public MailAuthDto createMailAuthCode(String email) {

        MailAuth mailAuth = mailRepository.findByEmail(email).orElse(null);

        if (mailAuth == null) {
            return toMailAuthDto(email);
        }

        // 인증코드 만료 시
        if(mailAuth.isAuthCodeExpired()){
            mailRepository.delete(mailAuth);
            return toMailAuthDto(email);
        }

        // 전송 횟수 초과
        if(mailAuth.getCount() >= 3){
            return null;
        }

        mailAuth.increaseCount();
        return modelMapper.map(mailAuth, MailAuthDto.class);
    }

    private MailAuthDto toMailAuthDto(String email) {
        return modelMapper.map(createMailAuth(email), MailAuthDto.class);
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
