package com.dev.petbackend.services.review;

import com.dev.petbackend.model.Review;
import com.dev.petbackend.model.dto.request.ReviewUpdateRequest;
import org.springframework.data.domain.Page;

public interface lReviewService {
    Review saveReview(Review review, Long reviewerId, Long veterinarianId);
    double getAverageRatingForVet(Long veterinarianId);
    Review updateReview(Long reviewerId, ReviewUpdateRequest review);
    Page<Review> findAllReviewsByUserId(Long userId, int page, int size);
    void deleteReview(Long reviewerId);
}
