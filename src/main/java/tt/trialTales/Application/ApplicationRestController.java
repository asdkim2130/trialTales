package tt.trialTales.Application;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tt.JwtProvider;
import tt.LoginMemberResolver;
import tt.trialTales.member.Member;

import java.util.List;

@RestController
public class ApplicationRestController {

    private final ApplicationService applicationService;
    private final JwtProvider jwtProvider;
    private final LoginMemberResolver loginMemberResolver;

    public ApplicationRestController(ApplicationService applicationService, JwtProvider jwtProvider, LoginMemberResolver loginMemberResolver) {
        this.applicationService = applicationService;
        this.jwtProvider = jwtProvider;
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
    public ApplicationResponse findApplications(@RequestHeader(HttpHeaders.AUTHORIZATION)String bearToken,
                                                @PathVariable (name = "applicationId") Long id){

        loginMemberResolver.resolveMemberFromToken(bearToken);

        return applicationService.find(id);
    }

    //사용자 신청서 모두 조회
    @GetMapping("applications/{memberId}")
    public List<ApplicationResponse> findAllUserApplications(@PathVariable Long memberId){
        return applicationService.findAll(memberId);
    }

    //삭제(관리자 권한 필요)
    @DeleteMapping("applications/{applicationId}")
    public void deleteApplication(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearToken,
            @PathVariable (name = "applicationId") Long id){

        Member member = loginMemberResolver.resolveMemberFromToken(bearToken);

        applicationService.delete(id, member);
    }

    //수정(관리자 권한 필요)
    @PatchMapping("applications/{applicationId}")
    public ApplicationResponse updateStatus(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearToken,
                                            @PathVariable (name = "applicationId") Long id){

        Member member = loginMemberResolver.resolveMemberFromToken(bearToken);

        return applicationService.update(id, member);
    }
}
