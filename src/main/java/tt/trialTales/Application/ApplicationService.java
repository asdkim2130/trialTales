package tt.trialTales.Application;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tt.trialTales.member.Member;
import tt.trialTales.member.MemberRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final MemberRepository memberRepository;

    public ApplicationService(ApplicationRepository applicationRepository, MemberRepository memberRepository) {
        this.applicationRepository = applicationRepository;
        this.memberRepository = memberRepository;
    }

    //신청 생성
    public ApplicationResponse create(ApplicationRequest request) {
        Application application = new Application(request.snsUrl());
        applicationRepository.save(application);

        Member member = memberRepository.findById(request.memberId()).orElseThrow(
                () -> new NoSuchElementException("유효하지 않은 사용자입니다.")
        );

        return  new ApplicationResponse(application.getId(),
                member,
                application.getCampaignId(),
                application.getSnsUrl(),
                application.getApplicationDate(),
                application.getApproved());
    }

    //신청 조회
    public ApplicationResponse find(Long id) {
        Application application = applicationRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 신청 내역이 존재하지 않습니다.")
        );

        return createResponse(application);
    }

    //사용자별 모든 신청 조회
    public List<ApplicationResponse> findAll(Long memberId) {
        return applicationRepository.findById(memberId)
                .stream()
                .map(this::createResponse)
                .toList();
    }

    //신청 삭제(관리자권한 필요)
    @Transactional
    public void delete(Long id) {
        Application application = applicationRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("유효하지 않은 신청입니다.")
        );

        applicationRepository.delete(application);
    }

    //신청 상태 변경(isApproved false -> true, 관리자권한 필요)
    @Transactional
    public ApplicationResponse update(Long id) {
        Application application = applicationRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 신청 내용이 존재하지 않습니다.")
        );

        application.changeStatus();

        return createResponse(application);
    }


    //ApplicationResponse return 함수
    public ApplicationResponse createResponse(Application application) {
        return new ApplicationResponse(application.getId(),
                application.getMemberId(),
                application.getCampaignId(),
                application.getSnsUrl(),
                application.getApplicationDate(),
                application.getApproved());
    }

}
