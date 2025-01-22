package tt.review;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController {
    private final ReviewService reviewService;

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
    public List<ReviewResponse>getReviews(){

        return reviewService.findAll();

    };




}
