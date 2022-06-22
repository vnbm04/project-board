package project.board.domain.mailAuth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.board.domain.mailAuth.MailAuth;

import java.util.Optional;

public interface MailRepository extends JpaRepository<MailAuth, Long> {
    Optional<MailAuth> findByEmail(String email);
}
