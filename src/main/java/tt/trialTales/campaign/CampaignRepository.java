package tt.trialTales.campaign;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    // 모집 종료일이 현재 시간보다 이전인 캠페인 조회
    List<Campaign> findByEndDateBefore(LocalDateTime now);

}
