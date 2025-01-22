package tt.trialTales.review;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long campaignId;
    private String content;
    private int rating;
    @CreatedDate
    private LocalDateTime reviewDate;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Review(Long userId, Long campaignId, String content, int rating) {
        this.userId = userId;
        this.campaignId = campaignId;
        this.content = content;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getCampaignId() {
        return campaignId;
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


}
