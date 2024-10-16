package com.dev.petbackend.controller;

import com.dev.petbackend.exceptions.ResourceNotFoundException;
import com.dev.petbackend.model.Photo;
import com.dev.petbackend.model.dto.response.ApiResponse;
import com.dev.petbackend.services.photo.lImageService;
import com.dev.petbackend.utils.FeedBackMessage;
import com.dev.petbackend.utils.UrlMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(UrlMapping.PHOTOS)
@RequiredArgsConstructor
@Tag(name = "Photo")
public class PhotoController {
    private final lImageService photoService;

    @PostMapping(UrlMapping.UPLOAD_PHOTO)
    public ResponseEntity<ApiResponse> savePhoto(
            @RequestParam MultipartFile file,
            @RequestParam Long userId) throws SQLException, IOException {
        try {
            Photo photo = photoService.savePhoto(file, userId);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.CREATE_SUCCESS, photo.getId()));
        } catch (IOException | SQLException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping(value = UrlMapping.GET_PHOTO_BY_ID)
    public ResponseEntity<ApiResponse> getPhotoById(@PathVariable Long photoId) {
        try {
            Photo photo = photoService.getPhotoById(photoId);
            if (photo != null) {
                byte[] photoBytes = photoService.getImageData(photo.getId());
                return ResponseEntity.ok(new ApiResponse(FeedBackMessage.RESOURCE_FOUND, photoBytes));
            }
        } catch (ResourceNotFoundException | SQLException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(null, NOT_FOUND));
    }

    @DeleteMapping(UrlMapping.DELETE_PHOTO)
    public ResponseEntity<ApiResponse> deletePhoto(@PathVariable Long photoId, @PathVariable Long userId) {
        try {
            Photo photo = photoService.getPhotoById(photoId);
            if (photo != null) {
                photoService.deletePhoto(photo.getId(), userId);
                return ResponseEntity.ok(new ApiResponse(FeedBackMessage.DELETE_SUCCESS, photo.getId()));
            }
        } catch (ResourceNotFoundException | SQLException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(null, INTERNAL_SERVER_ERROR));
    }


    @PutMapping(UrlMapping.UPDATE_PHOTO)
    public ResponseEntity<ApiResponse> updatePhoto(@PathVariable Long photoId, @RequestBody MultipartFile file) throws SQLException {
        try {
            Photo photo = photoService.getPhotoById(photoId);
            if (photo != null) {
                Photo updatedPhoto = photoService.updatePhoto(photo.getId(), file);
                return ResponseEntity.ok(new ApiResponse(FeedBackMessage.UPDATE_SUCCESS, updatedPhoto.getId()));
            }
        } catch (ResourceNotFoundException | IOException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, NOT_FOUND));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(null, INTERNAL_SERVER_ERROR));

    }

}
