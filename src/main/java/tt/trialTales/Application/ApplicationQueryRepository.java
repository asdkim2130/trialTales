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

    public List<Application> findAllByStatus(Status statusFilter){
        if(statusFilter == null){

            return jpaQueryFactory
                    .selectFrom(application)
                    .fetch();
        }
        if(statusFilter == Status.PENDING){
            return jpaQueryFactory
                    .selectFrom(application)
                    .where(statusIsPending())
                    .fetch();
        }return jpaQueryFactory
                .selectFrom(application)
                .where(statusIsApproved())
                .fetch();
    }

    private BooleanExpression statusIsPending(){
        return application.status.eq(Status.PENDING);
    }

    private BooleanExpression statusIsApproved(){
        return application.status.eq(Status.APPROVED);
    }

    //Soft Delete
    public void softDeleteById(Long id){
        jpaQueryFactory.update(application)
                .set(application.deletedAt, LocalDateTime.now())
                .where(application.id.eq(id))
                .execute();
    }

    //삭제되지 않은 개별 Application 조회
    public Optional<Application> findActiveApplication(Long id){
        return Optional.ofNullable(jpaQueryFactory.selectFrom(application)
                .where(application.id.eq(id)
                        .and(application.deletedAt.isNull()))
                .fetchOne());
    }

    public List<Application> findAllActiveApplications(Long memberId){
        return jpaQueryFactory.selectFrom(application)
                .where(application.member.id.eq(memberId)
                        .and(application.deletedAt.isNull()))
                .fetch();
    }



}
