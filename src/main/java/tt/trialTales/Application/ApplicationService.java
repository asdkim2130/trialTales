package tt.trialTales.Application;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    //체험단 신청
    public ApplicationResponse create (ApplicationRequest request){
        Application application = new Application(request.snsUrl());

        applicationRepository.save(application);

        return createResponse(application);

    }

    //체험단 신청 조회
    public ApplicationResponse find (Long id){
        Application application = applicationRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 신청 내역이 존재하지 않습니다.")
        );

        return createResponse(application);
    }

    //사용자별 캠페인 신청 조회
    public List<ApplicationResponse> findAll(Long userId){
        return applicationRepository.findByUserId(userId)
                .stream()
                .map(this::createResponse)
                .toList();
    }


    public ApplicationResponse createResponse(Application application){
        return new ApplicationResponse(application.getId(),
                application.getUserId(),
                application.getCampaignId(),
                application.getSnsUrl(),
                application.getApplicationDate(),
                application.getApproved());
    }

}
