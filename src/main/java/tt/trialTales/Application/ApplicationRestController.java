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

    @PostMapping("applications")
    public ApplicationResponse createApplication(@RequestHeader(HttpHeaders.AUTHORIZATION)String bearToken,
                                                 @RequestBody ApplicationRequest request){

        loginMemberResolver.resolveMemberFromToken(bearToken);

        return applicationService.create(request);
    }

    @GetMapping("applications/{applicationId}")
    public ApplicationResponse findApplications(@RequestHeader(HttpHeaders.AUTHORIZATION)String bearToken,
                                                @PathVariable (name = "applicationId") Long id){

        loginMemberResolver.resolveMemberFromToken(bearToken);

        return applicationService.find(id);
    }

    @GetMapping("applications/{memberId}")
    public List<ApplicationResponse> findAllUserApplications(@PathVariable Long memberId){
        return applicationService.findAll(memberId);
    }

    @DeleteMapping("applications/{applicationId}")
    public void deleteApplication(@PathVariable (name = "applicationId") Long id){

        applicationService.delete(id);
    }

    @PatchMapping("applications/{applicationId}")
    public ApplicationResponse updateStatus(@PathVariable (name = "applicationId") Long id){
        return applicationService.update(id);
    }
}
