package tt.trialTales.review;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    //**생성자주입
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    //**리뷰작성 서비스로직
    public void save(ReviewRequest request) {
        reviewRepository.save(new Review(
                request.userId(),
                request.campaignId(),
                request.content(),
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

    //**사용자별 조회 서비스로직
    public List<ReviewResponse> getUserReviews(Long userId) {
        return reviewRepository.findAllByUserId(userId)
                .stream()
                .map(review -> new ReviewResponse(review))
//                .collect(Collectors.toList());
                .toList();
    }

    //**리뷰삭제 서비스로직
    public void deleteReview(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new IllegalArgumentException("리뷰를 찾을 수 없습니다.");
        }

        reviewRepository.deleteById(reviewId);
    }

    //**리뷰수정 서비스로직
    @Transactional
    public void update(Long reviewId, ReviewRequest reviewRequest) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        // 리뷰 수정
        review.update(
                reviewRequest.userId(),
                reviewRequest.campaignId(),
                reviewRequest.content(),
                reviewRequest.rating());  // update 메서드를 통해 리뷰 업데이트
    }
}
