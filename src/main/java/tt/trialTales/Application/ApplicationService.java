package tt.trialTales.Application;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    //신청 생성
    public ApplicationResponse create (ApplicationRequest request){
        Application application = new Application(request.snsUrl());

        applicationRepository.save(application);

        return createResponse(application);

    }

    //신청 조회
    public ApplicationResponse find (Long id){
        Application application = applicationRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 신청 내역이 존재하지 않습니다.")
        );

        return createResponse(application);
    }

    //사용자별 모든 신청 조회
    public List<ApplicationResponse> findAll(Long userId){
        return applicationRepository.findByUserId(userId)
                .stream()
                .map(this::createResponse)
                .toList();
    }

    //신청 삭제(관리자권한 필요)
    @Transactional
    public void delete(Long id){
        Application application = applicationRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("유효하지 않은 신청입니다.")
        );

        applicationRepository.delete(application);
    }

    //신청 상태 변경(isApproved false -> true, 관리자권한 필요)
    @Transactional
    public ApplicationResponse update(Long id){
        Application application = applicationRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 신청 내용이 존재하지 않습니다.")
        );

        application.changeStatus();

        return createResponse(application);
    }


    //ApplicationResponse return 함수
    public ApplicationResponse createResponse(Application application){
        return new ApplicationResponse(application.getId(),
                application.getUserId(),
                application.getCampaignId(),
                application.getSnsUrl(),
                application.getApplicationDate(),
                application.getApproved());
    }

}
