package tt.trialTales.Application;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApplicationRestController {

    private final ApplicationService applicationService;

    public ApplicationRestController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("applications")
    public ApplicationResponse createApplication(@RequestBody ApplicationRequest request){
        return applicationService.create(request);
    }

    @GetMapping("applications/{applicationId}")
    public ApplicationResponse findApplications(@PathVariable (name = "applicationId") Long id){
        return applicationService.find(id);
    }

}
