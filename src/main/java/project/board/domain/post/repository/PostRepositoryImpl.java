package project.board.domain.post.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project.board.domain.post.Post;
import project.board.domain.post.QPost;
import project.board.web.post.dto.PostSearchCondition;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static project.board.domain.post.QPost.*;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Post> searchPosts(PostSearchCondition condition, Pageable pageable) {

        BooleanBuilder builder = new BooleanBuilder();
        String keyword = condition.getKeyword();

        if(hasText(keyword)){
            builder.or(post.title.eq(keyword));
        }

        if(hasText(keyword)){
            builder.or(post.user.nickname.eq(keyword));
        }

        List<Post> posts = queryFactory
                .select(post)
                .from(post)
                .where(
                        builder,
                        dateGoe(condition.getStartDate()),
                        dateLoe(condition.getEndDate())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = queryFactory
                .select(post.count())
                .from(post)
                .fetchOne();

        return new PageImpl<>(posts, pageable, totalCount);
    }

    private BooleanExpression dateLoe(LocalDateTime endDate) {
        return endDate != null ? post.lastModifiedDate.loe(endDate) : null;
    }

    private BooleanExpression dateGoe(LocalDateTime startDate) {
        return startDate != null ? post.lastModifiedDate.goe(startDate) : null;
    }
}
