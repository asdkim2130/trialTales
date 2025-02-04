package tt.trialTales.campaign;

import jakarta.persistence.*;
import tt.trialTales.member.Member;

import java.time.LocalDateTime;

@Entity
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String campaignName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private int recruitmentLimit;

    @Column(nullable = false)
    private boolean deleted = false; // ✅ Soft Delete 추가

    protected Campaign() {}

    public Campaign(Member member, String campaignName, String description, LocalDateTime startDate, LocalDateTime endDate, String status, int recruitmentLimit) {
        this.member = member;
        this.campaignName = campaignName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.recruitmentLimit = recruitmentLimit;
    }

    public Long getId() {
        return id;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) { // ✅ 모집 상태 업데이트 가능하도록 setter 추가
        this.status = status;
    }

    public int getRecruitmentLimit() {
        return recruitmentLimit;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void delete() {
        this.deleted = true; // ✅ Soft Delete 처리
    }

    public void restore() {
        this.deleted = false; // ✅ 캠페인 복구 기능 추가
    }

    public void updateCampaign(String campaignName, String description, int recruitmentLimit) {
        this.campaignName = campaignName;
        this.description = description;
        this.recruitmentLimit = recruitmentLimit;
    }
}
