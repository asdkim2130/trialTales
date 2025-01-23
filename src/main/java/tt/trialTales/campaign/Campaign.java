package tt.trialTales.campaign;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String campaignName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Column(nullable = false)
    private String status; // "모집 중", "모집 완료"

    @Min(1) // 최소 모집 인원
    @Max(10) // 최대 모집 인원
    @Column(nullable = false)
    private int recruitmentLimit;

    // 기본 생성자
    protected Campaign() {}

    // 모든 필드 생성자
    public Campaign(String campaignName, String description, LocalDateTime startDate,
                    LocalDateTime endDate, String status, int recruitmentLimit) {
        this.campaignName = campaignName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.recruitmentLimit = recruitmentLimit;
    }

    // Getter 및 Setter
    public Long getId() {
        return id;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRecruitmentLimit() {
        return recruitmentLimit;
    }

    public void setRecruitmentLimit(int recruitmentLimit) {
        if (recruitmentLimit < 1 || recruitmentLimit > 10) {
            throw new IllegalArgumentException("모집 인원은 최소 1명에서 최대 10명이어야 합니다.");
        }
        this.recruitmentLimit = recruitmentLimit;
    }
}
