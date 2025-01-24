package tt.trialTales.review;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

//리뷰 rating 에 별점순 오름차순을 구현하고자함
@Repository
public class ReviewQueryRepository {

    //*JPAQueryFactory 를 QueryRepository 에 주입해야됨
    public final JPAQueryFactory queryFactory;

    public ReviewQueryRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;

    }

    public List<Review> findReviewsSortedByRating() {
        QReview review = QReview.review;

        return queryFactory.selectFrom(review)
                .orderBy(review.rating.asc())  // rating 기준 오름차순 정렬
                .fetch();
    }
}
