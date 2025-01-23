package tt.trialTales.campaign;

import jakarta.persistence.*;
import tt.trialTales.Application.Application;
import tt.trialTales.member.Member;
import tt.trialTales.review.Review;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 캠페인을 생성한 사용자

    @Column(nullable = false)
    private String campaignName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Column(nullable = false)
    private String status; // "모집 중", "모집 완료"

    @Column(nullable = false)
    private int recruitmentLimit;

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

    public Member getMember() {
        return member;
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

    public LocalDateTime getEndDate() {
        return endDate;
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
        this.recruitmentLimit = recruitmentLimit;
    }
}