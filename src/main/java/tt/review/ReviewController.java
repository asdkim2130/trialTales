package tt.review;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController {
    private final ReviewService reviewService;

    //**생성자 주입
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    //**리뷰작성 api
    @PostMapping("/reviews")
    void create(@RequestBody ReviewRequest request) {
        reviewService.save(request);
    }

    //**리뷰조회 api
    @GetMapping("/reviews")
    public List<ReviewResponse> getReviews(@RequestParam(required = false) Long userId) {
        if (userId == null) {
            return reviewService.findAll();
        } else {
            return reviewService.getUserReviews(userId);
        }
    }

    //**리뷰 삭제 api
    @DeleteMapping("/reviews/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
    }

    //**리뷰 수정 api
    @PutMapping("/reviews/{reviewId}")
    void updateReview(@PathVariable Long reviewId, @RequestBody ReviewRequest reviewRequest) {
        reviewService.update(reviewId, reviewRequest);
    }
}
