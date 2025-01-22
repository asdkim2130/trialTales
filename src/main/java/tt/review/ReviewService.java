package tt.review;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    public List<ReviewResponse> findById;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
    //**리뷰작성 서비스로직
    public void save(ReviewRequest request){
        reviewRepository.save(new Review(
                request.userId(),
                request.campaignId(),
                request.reviewContent(),
                request.rating()));


    }

    //**리뷰조회서비스로직
    public List<ReviewResponse> findAll() {
        // 리뷰 데이터를 데이터베이스에서 조회 (repository 사용)
        List<Review> reviews = reviewRepository.findAll();  // reviewRepository는 실제 DB에서 데이터를 조회하는 역할

        // DB에서 가져온 Review 엔티티를 PostResponse DTO로 변환
        return reviews.stream()
                .map(review -> new ReviewResponse(review))  // 리뷰 엔티티를 PostResponse로 변환
                .collect(Collectors.toList());



    }
}
