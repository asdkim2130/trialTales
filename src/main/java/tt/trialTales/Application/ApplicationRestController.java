package tt.trialTales.Application;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import tt.LoginMemberResolver;
import tt.trialTales.member.Member;

import java.util.List;

@RestController
public class ApplicationRestController {

    private final ApplicationService applicationService;
    private final LoginMemberResolver loginMemberResolver;

    public ApplicationRestController(ApplicationService applicationService, LoginMemberResolver loginMemberResolver) {
        this.applicationService = applicationService;
        this.loginMemberResolver = loginMemberResolver;
    }

    //생성
    @PostMapping("applications")
    public ApplicationResponse createApplication(@RequestHeader(HttpHeaders.AUTHORIZATION)String bearToken,
                                                 @RequestBody ApplicationRequest request){

        loginMemberResolver.resolveMemberFromToken(bearToken);

        return applicationService.create(request);
    }

    //조회
    @GetMapping("applications/{applicationId}")
    public ReadApplicationResponse findApplications(@RequestHeader(HttpHeaders.AUTHORIZATION)String bearToken,
                                                @PathVariable (name = "applicationId") Long id){

        Member member = loginMemberResolver.resolveMemberFromToken(bearToken);

        return applicationService.find(id, member);
    }

    //사용자 신청서 모두 조회
    @GetMapping("applications/{memberId}")
    public List<ReadApplicationResponse> findAllUserApplications(@RequestHeader(HttpHeaders.AUTHORIZATION)String bearToken,
                                                             @PathVariable Long memberId){

        Member member = loginMemberResolver.resolveMemberFromToken(bearToken);

        return applicationService.findAll(memberId, member);
    }

    //승인 상태별 신청서 조회
    @GetMapping("applications/{status}")
    public List<ReadApplicationResponse>findAppsByStatus(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearToken,
                                                          @PathVariable Status status){

        Member member = loginMemberResolver.resolveMemberFromToken(bearToken);

        return applicationService.findPending(status, member);
    }

    //삭제
    @DeleteMapping("applications/{applicationId}")
    public void deleteApplication(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearToken,
            @PathVariable (name = "applicationId") Long id){

        Member member = loginMemberResolver.resolveMemberFromToken(bearToken);

        applicationService.delete(id, member);
    }

    //수정
    @PatchMapping("applications/{applicationId}")
    public UpdateApplicationResponse updateStatus(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearToken,
                                            @PathVariable (name = "applicationId") Long id){

        Member member = loginMemberResolver.resolveMemberFromToken(bearToken);

        return applicationService.update(id, member);
    }
}
