package tt.trialTales.Application;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ApplicationQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QApplication application = QApplication.application;

    public ApplicationQueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Application> findAllByStatus(Status statusFilter) {
        if (statusFilter == null) {

            return jpaQueryFactory
                    .selectFrom(application)
                    .fetch();
        }

        return jpaQueryFactory
                .selectFrom(application)
                .where(statusFilter(statusFilter)
                        .and(application.isDeleted.isFalse()))
                .fetch();

    }

    private BooleanExpression statusFilter(Status statusFilter) {
        if (statusFilter == null) {
            return null;
        }

        return application.status.eq(statusFilter);
    }

    //Soft Delete
    public void softDeleteById(Long id) {
        jpaQueryFactory.update(application)
                .set(application.deletedAt, LocalDateTime.now())
                .set(application.isDeleted, true)
                .where(application.id.eq(id))
                .execute();
    }

    //삭제되지 않은 개별 Application 조회
    public Optional<Application> findActiveApplication(Long id) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(application)
                .where(application.id.eq(id)
                        .and(application.isDeleted.isFalse()))
                .fetchOne());
    }

    public List<Application> findAllActiveApplications(Long memberId) {
        return jpaQueryFactory.selectFrom(application)
                .where(application.member.id.eq(memberId)
                        .and(application.isDeleted.isFalse()))
                .fetch();
    }


}
