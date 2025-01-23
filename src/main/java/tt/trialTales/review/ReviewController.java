package tt.trialTales.review;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tt.LoginMemberResolver;
import tt.trialTales.member.Member;

import java.util.List;

@RestController
public class ReviewController {
    private final ReviewService reviewService;
    private final LoginMemberResolver loginMemberResolver;
    private ReviewRequest reviewRequest;

    //**생성자 주입
    public ReviewController(ReviewService reviewService, LoginMemberResolver loginMemberResolver,ReviewRequest reviewRequest) {
        this.reviewService = reviewService;
        this.loginMemberResolver = loginMemberResolver;
        this.reviewRequest = reviewRequest;
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
    public void deleteReview(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String bearToken,
            @PathVariable Long reviewId,
            @RequestBody ReviewRequest reviewRequest) {
        this.reviewRequest = reviewRequest;
        Member member = loginMemberResolver.resolveMemberFromToken(bearToken);
        reviewService.deleteReview(reviewId,member);
    }

    //**리뷰 수정 api
    @PutMapping("/reviews/{reviewId}")
    void updateReview(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String bearToken,
            @PathVariable Long reviewId,
            @RequestBody ReviewRequest reviewRequest) {
        Member member = loginMemberResolver.resolveMemberFromToken(bearToken);
        reviewService.update(reviewId, reviewRequest,member);
    }
}
