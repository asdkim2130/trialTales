package tt.trialTales.Application;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tt.JwtProvider;
import tt.trialTales.campaign.Campaign;
import tt.trialTales.campaign.CampaignRepository;
import tt.trialTales.member.Member;
import tt.trialTales.member.MemberRepository;
import tt.trialTales.member.MemberService;
import tt.trialTales.member.Role;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final MemberRepository memberRepository;
    private final CampaignRepository campaignRepository;
    private final ApplicationQueryRepository queryRepository;

    public ApplicationService(ApplicationRepository applicationRepository, MemberRepository memberRepository, MemberService memberService, JwtProvider jwtProvider, CampaignRepository campaignRepository, ApplicationQueryRepository queryRepository) {
        this.applicationRepository = applicationRepository;
        this.memberRepository = memberRepository;
        this.campaignRepository = campaignRepository;
        this.queryRepository = queryRepository;
    }

    //신청 생성
    public ApplicationResponse create(ApplicationRequest request) {
        Application application = new Application(request.snsUrl());
        applicationRepository.save(application);

        Campaign campaign = campaignRepository.findById(request.campaignId()).orElseThrow(
                () -> new NoSuchElementException("해당 캠페인을 찾을 수 없습니다.")
        );

        Member member = memberRepository.findById(request.memberId()).orElseThrow(
                () -> new NoSuchElementException("유효하지 않은 사용자입니다.")
        );

        return new ApplicationResponse(application.getId(),
                member,
                campaign,
                application.getSnsUrl(),
                application.getApplicationDate(),
                application.getStatus());
    }


    //신청 조회
    public ReadApplicationResponse find(Long id, Member loginMember) {

        if (!loginMember.getRole().equals(Role.ADMIN)) {
            throw new NoSuchElementException("신청서 조회에는 관리자 권한이 필요합니다.");
        }

        Application application = applicationRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 신청 내역이 존재하지 않습니다.")
        );

        Campaign campaign = application.getCampaign();

        return new ReadApplicationResponse(application.getId(),
                campaign,
                application.getSnsUrl(),
                application.getApplicationDate(),
                application.getStatus());
    }

    //사용자별 모든 신청 조회
    public List<ReadApplicationResponse> findAll(Long memberId, Member loginMember) {

        if (!loginMember.getRole().equals(Role.ADMIN)) {
            throw new NoSuchElementException("신청서 조회에는 관리자 권한이 필요합니다.");
        }

        return applicationRepository.findById(memberId)
                .stream()
                .map(application -> new ReadApplicationResponse(
                        application.getId(),
                        application.getCampaign(),
                        application.getSnsUrl(),
                        application.getApplicationDate(),
                        application.getStatus()
                ))
                .toList();
    }

    //승인 상태(PENDING, APPROVED)별로 조회하기
    public List<ReadApplicationResponse>findPending(Status status, Member loginMember){

        if (!loginMember.getRole().equals(Role.ADMIN)) {
            throw new NoSuchElementException("신청서 조회에는 관리자 권한이 필요합니다.");
        }

        return queryRepository.findAllByStatus(status).stream()
                .map(application -> new ReadApplicationResponse(
                        application.getId(),
                        application.getCampaign(),
                        application.getSnsUrl(),
                        application.getApplicationDate(),
                        application.getStatus()
                ))
                .toList();
    }


    //신청 삭제
    @Transactional
    public void delete(Long id, Member loginMember) {

        if (!loginMember.getRole().equals(Role.ADMIN)) {
            throw new NoSuchElementException("신청서 삭제에는 관리자 권한이 필요합니다.");
        }

        Application application = applicationRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 신청 내용이 존재하지 않습니다.")
        );

        applicationRepository.delete(application);
    }


    //신청 상태 변경
    @Transactional
    public UpdateApplicationResponse update(Long id, Member loginMember) {

        if (!loginMember.getRole().equals(Role.ADMIN)) {
            throw new NoSuchElementException("신청서 수정에는 관리자 권한이 필요합니다.");
        }

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 신청 내용이 존재하지 않습니다."));

        application.statusChange();

        return new UpdateApplicationResponse(application.getId(),
                application.getSnsUrl(),
                application.getApplicationDate(),
                application.getStatus());
    }



}
