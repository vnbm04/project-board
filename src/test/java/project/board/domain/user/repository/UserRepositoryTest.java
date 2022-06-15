package project.board.domain.user.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.board.domain.user.User;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("Auditing Test")
    public void auditing_test() throws InterruptedException {

        String email = "email";
        String username = "username";

        // given
        User user = User.builder()
                .email(email)
                .username(username)
                .build();

        User saveUser = userRepository.save(user);
        Thread.sleep(1000);

        saveUser.updateUsername("test");

        em.flush();
        em.clear();

        // when
        User findUser = userRepository.findById(saveUser.getId()).get();

        // then
        System.out.println("findUser.getCreatedDate() = " + findUser.getCreatedDate());
        System.out.println("findUser.getLastModifiedDate() = " + findUser.getLastModifiedDate());
    }

}