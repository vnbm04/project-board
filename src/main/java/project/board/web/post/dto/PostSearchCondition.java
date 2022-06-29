package project.board.web.post.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class PostSearchCondition {

    // title, nickname
    private String keyword;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDate maxDate;
    private int page;

    public PostSearchCondition() {
        this.maxDate = LocalDate.now();
    }
}
