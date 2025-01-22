package tt.trialTales.Application;

import org.springframework.data.jpa.repository.JpaRepository;
import tt.trialTales.member.Member;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
