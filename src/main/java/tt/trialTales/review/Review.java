package tt.trialTales.review;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import tt.trialTales.campaign.Campaign;

import java.time.LocalDateTime;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;

    private String content;
    private int rating;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Campaign campaign;
    @CreatedDate
    private LocalDateTime reviewDate;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    protected Review() {
    }

    public Review(Long userId, String content, int rating, Campaign campaign) {
        this.userId = userId;
        this.content = content;
        this.rating = rating;
        this.campaign = campaign;
    }



    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }




    public String getContent() {
        return content;
    }

    public int getRating() {
        return rating;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void update(Long userId, String content, int rating, Campaign campaign ) {
        this.userId = userId;
        this.content = content;
        this.rating = rating;
        this.campaign = campaign;
    }
}
