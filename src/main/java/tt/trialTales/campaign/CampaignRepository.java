package tt.trialTales.campaign;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findByEndDateBefore(LocalDateTime now);
    List<Campaign> findByDeletedFalse(); // 삭제되지 않은 캠페인만 조회
}
