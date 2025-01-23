package tt.trialTales.Application;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

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



}
