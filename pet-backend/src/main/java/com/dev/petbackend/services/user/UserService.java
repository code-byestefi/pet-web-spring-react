package com.dev.petbackend.services.user;

import com.dev.petbackend.model.Review;
import com.dev.petbackend.model.User;
import com.dev.petbackend.model.dto.AppointmentDto;
import com.dev.petbackend.model.dto.EntityConverter;
import com.dev.petbackend.model.dto.ReviewDto;
import com.dev.petbackend.model.dto.UserDto;
import com.dev.petbackend.model.dto.request.RegistrationRequest;
import com.dev.petbackend.model.dto.request.UserUpdateRequest;
import com.dev.petbackend.repositories.UserRepository;
import com.dev.petbackend.repositories.VeterinarianRepository;
import com.dev.petbackend.services.appointment.AppointmentService;
import com.dev.petbackend.services.pet.lPetService;
import com.dev.petbackend.services.photo.lImageService;
import com.dev.petbackend.services.review.lReviewService;
import com.dev.petbackend.services.user.factory.UserFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService implements lUserService {
    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final VeterinarianRepository veterinarianRepository;
    private final EntityConverter<User, UserDto> entityConverter;
    private final AppointmentService appointmentService;
    private final lPetService petService;
    private final lImageService photoService;
    private final lReviewService reviewService;

    @Override
    public User register(RegistrationRequest request) {
        return userFactory.createUser(request);
    }

    @Override
    public User update(Long userId, UserUpdateRequest request) {
        User user = findById(userId);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setGender(request.getGender());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setSpecialization(request.getSpecialization());

        return userRepository.save(user);
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public void delete(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete, () -> {
                    throw new ResourceNotFoundException("User not found");
                });
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> entityConverter.mapEntityToDto(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserWithDetails(Long userId) throws SQLException {
        //1. get the user
        User user = findById(userId);
        //2. convert the user to a userDto
        UserDto userDto = entityConverter.mapEntityToDto(user, UserDto.class);
        //3. get user appointments ( users ( patient and a vet))
        setUserAppointment(userDto);
        //.4 get users photo
        setUserPhoto(userDto, user);
        setUserReviews(userDto, userId);
        return  userDto;
    }

    private void setUserAppointment(UserDto userDto) {
        List<AppointmentDto> appointments = appointmentService.getUserAppointments(userDto.getId());
        userDto.setAppointments(appointments);
    }
    private void setUserPhoto(UserDto userDto, User user) throws SQLException {
        if (user.getPhoto() != null) {
            userDto.setPhotoId(user.getPhoto().getId());
            userDto.setPhoto(photoService.getImageData(user.getPhoto().getId()));
        }
    }


    private void setUserReviews(UserDto userDto, Long userId) {
        Page<Review> reviewPage = reviewService.findAllReviewsByUserId(userId, 0, Integer.MAX_VALUE);
        List<ReviewDto> reviewDto = reviewPage.getContent()
                .stream()
                .map(this::mapReviewToDto).toList();
        if(!reviewDto.isEmpty()) {
            double averageRating = reviewService.getAverageRatingForVet(userId);
        }
        userDto.setReviews(reviewDto);
    }

    private ReviewDto mapReviewToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setStars(review.getStars());
        reviewDto.setFeedback(review.getFeedback());
        mapVeterinarianInfo(reviewDto, review);
        mapPatientInfo(reviewDto, review);
        return reviewDto;
    }
    private void mapVeterinarianInfo(ReviewDto reviewDto, Review review){
        if (review.getVeterinarian() != null) {
            reviewDto.setVeterinarianId(review.getVeterinarian().getId());
            reviewDto.setVeterinarianName(review.getVeterinarian().getFirstName()+ " " + review.getVeterinarian().getLastName());
            setVeterinarianPhoto(reviewDto, review);
        }
    }

    private void mapPatientInfo(ReviewDto reviewDto, Review review) {
        if (review.getPatient() != null) {
            reviewDto.setPatientId(review.getPatient().getId());
            reviewDto.setPatientName(review.getPatient().getFirstName()+ " " + review.getPatient().getLastName());
            setReviewerPhoto(reviewDto, review);
        }
    }

    private void setReviewerPhoto(ReviewDto reviewDto, Review review) {
        if(review.getPatient().getPhoto() != null){
            try {
                reviewDto.setPatientImage(photoService.getImageData(review.getPatient().getPhoto().getId()));
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }else {
            reviewDto.setPatientImage(null);
        }
    }

    private void setVeterinarianPhoto(ReviewDto reviewDto, Review review) {
        if(review.getVeterinarian().getPhoto() != null){
            try {
                reviewDto.setVeterinarianImage(photoService.getImageData(review.getVeterinarian().getPhoto().getId()));
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }else {
            reviewDto.setVeterinarianImage(null);
        }
    }


}
