package com.dev.petbackend.services.review;

import com.dev.petbackend.exceptions.ResourceNotFoundException;
import com.dev.petbackend.exceptions.UserAlreadyExistsException;
import com.dev.petbackend.model.Review;
import com.dev.petbackend.model.User;
import com.dev.petbackend.model.dto.request.ReviewUpdateRequest;
import com.dev.petbackend.model.enums.AppointmentStatus;
import com.dev.petbackend.repositories.AppointmentRepository;
import com.dev.petbackend.repositories.ReviewRepository;
import com.dev.petbackend.repositories.UserRepository;
import com.dev.petbackend.utils.FeedBackMessage;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReviewService implements lReviewService{

    private final ReviewRepository reviewRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;


    @Override
    public Review saveReview(Review review, Long reviewerId, Long veterinarianId) {
        if(veterinarianId.equals(reviewerId)) {
            throw new IllegalArgumentException(FeedBackMessage.CANNOT_REVIEW);
        }
        Optional<Review> existingReview = reviewRepository.findByVeterinarianIdAndPatientId(veterinarianId, reviewerId);
        if(existingReview.isPresent()) {
            throw new UserAlreadyExistsException(FeedBackMessage.ALREADY_REVIEWED);
        }
       boolean hadCompletedAppointments =  appointmentRepository.existsByVeterinarianIdAndPatientIdAndStatus(veterinarianId, reviewerId, AppointmentStatus.COMPLETED);
        if(!hadCompletedAppointments) {
            throw new IllegalStateException(FeedBackMessage.NOT_ALLOWED);
        }
        User veterinarian = userRepository.findById(veterinarianId).orElseThrow(()->new ResourceNotFoundException(FeedBackMessage.VET_OR_PATIENT_NOT_FOUND));

        User patient = userRepository.findById(reviewerId).orElseThrow(()->new ResourceNotFoundException(FeedBackMessage.VET_OR_PATIENT_NOT_FOUND));

        review.setVeterinarian(veterinarian);
        review.setPatient(patient);

        return reviewRepository.save(review);
    }

    @Transactional
    @Override
    public double getAverageRatingForVet(Long veterinarianId) {
        List<Review> reviews = reviewRepository.findByVeterinarianId(veterinarianId);
        return reviews.isEmpty() ? 0 : reviews.stream()
                .mapToInt(Review :: getStars)
                .average()
                .orElse(0.0);
    }

    @Override
    public Review updateReview(Long reviewerId, ReviewUpdateRequest review) {
        return reviewRepository.findById(reviewerId)
                .map(existingReview -> {
                    existingReview.setStars(review.getStars());
                    existingReview.setFeedback(review.getFeedback());
                    return reviewRepository.save(existingReview);
                }).orElseThrow(() -> new ResourceNotFoundException(FeedBackMessage.NOT_FOUND));
    }

    @Override
    public Page<Review> findAllReviewsByUserId(Long userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return reviewRepository.findAllByUserId(userId, pageRequest);
    }

    @Override
    public void deleteReview(Long reviewerId) {
        reviewRepository.findById(reviewerId)
                .ifPresentOrElse(Review::removeRelationShip, ()->{
                    throw new ResourceNotFoundException(FeedBackMessage.NOT_FOUND);
                });
        reviewRepository.deleteById(reviewerId);
    }

}
