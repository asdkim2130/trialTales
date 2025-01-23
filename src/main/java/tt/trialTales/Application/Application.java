package tt.trialTales.Application;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import tt.trialTales.campaign.Campaign;
import tt.trialTales.member.Member;

import java.time.LocalDateTime;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "campaignId", nullable = false)
    private Campaign campaign;

    @Column(nullable = false)
    private String snsUrl;

    @CreatedDate
    private LocalDateTime applicationDate;

    @Column(nullable = false)
    private Boolean isApproved;


    protected Application() {
    }

    public Application(Long id, Member member, Campaign campaign, String snsUrl, LocalDateTime applicationDate, Boolean isApproved) {
        this.id = id;
        this.member = member;
        this.campaign = campaign;
        this.snsUrl = snsUrl;
        this.applicationDate = applicationDate;
        this.isApproved = isApproved;
    }

    public Application(String snsUrl) {
        this.snsUrl = snsUrl;
    }

    public Long getId() {
        return id;
    }

    public Member getMemberId() {
        return member;
    }

    public Campaign getCampaignId() {
        return campaign;
    }

    public String getSnsUrl() {
        return snsUrl;
    }

    public LocalDateTime getApplicationDate() {
        return applicationDate;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void changeStatus(){
        this.isApproved = !this.isApproved;
    }
}
