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
    private Status status = Status.PENDING;

    protected Application() {
    }

    public Application(Long id, Member member, Campaign campaign, String snsUrl, LocalDateTime applicationDate, Status status) {
        this.id = id;
        this.member = member;
        this.campaign = campaign;
        this.snsUrl = snsUrl;
        this.applicationDate = applicationDate;
        this.status = status;
    }

    public Application(String snsUrl, Campaign campaign, Member member) {
        this.snsUrl = snsUrl;
        this.campaign = campaign;
        this.member = member;
    }

    public Application(String snsUrl) {
        this.snsUrl = snsUrl;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public String getSnsUrl() {
        return snsUrl;
    }

    public LocalDateTime getApplicationDate() {
        return applicationDate;
    }

    public Status getStatus() {
        return status;
    }

    public void statusChange(){
        if(this.status == Status.PENDING){
            status = Status.APPROVED;
        }else status = Status.PENDING;
    }
}
