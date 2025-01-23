package tt.trialTales.Application;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import tt.trialTales.member.Member;

import java.time.LocalDateTime;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member memberId;

    private Long campaignId;

    @Column(nullable = false)
    private String snsUrl;

    @CreatedDate
    private LocalDateTime applicationDate;

    @Column()
    private Boolean isApproved;


    protected Application() {
    }

    public Application(Long id, Member memberId, Long campaignId, String snsUrl, LocalDateTime applicationDate, Boolean isApproved) {
        this.id = id;
        this.memberId = memberId;
        this.campaignId = campaignId;
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
        return memberId;
    }

    public Long getCampaignId() {
        return campaignId;
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
