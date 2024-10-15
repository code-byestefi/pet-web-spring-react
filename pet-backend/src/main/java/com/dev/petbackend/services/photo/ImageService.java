package com.dev.petbackend.services.photo;

import com.dev.petbackend.exceptions.ResourceNotFoundException;
import com.dev.petbackend.model.Photo;
import com.dev.petbackend.model.User;
import com.dev.petbackend.repositories.PhotoRepository;
import com.dev.petbackend.repositories.UserRepository;
import com.dev.petbackend.services.user.UserService;
import com.dev.petbackend.utils.FeedBackMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ImageService implements lImageService{

    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;

    @Override
    public Photo savePhoto(MultipartFile file, Long userId) throws IOException, SQLException {
        Optional<User> theUser = userRepository.findById(userId);
        Photo photo = new Photo();
        if (file != null && !file.isEmpty()) {
            byte[] photoBytes = file.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            photo.setImage(photoBlob);
            photo.setFileType(file.getContentType());
            photo.setFileName(file.getOriginalFilename());
        }
        Photo savedPhoto = photoRepository.save(photo);
        theUser.ifPresent(user -> {user.setPhoto(savedPhoto);});
        userRepository.save(theUser.get());
        return savedPhoto;
    }

    @Override
    public Photo getPhotoById(Long id) {
        return photoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FeedBackMessage.RESOURCE_FOUND));
    }

    @Transactional
    @Override
    public void deletePhoto(Long id, Long userId) {
        userRepository.findById(userId).ifPresentOrElse(User ::removeUserPhoto, () ->{
            throw new ResourceNotFoundException(FeedBackMessage.NOT_FOUND);
        });
        photoRepository.findById(id)
                .ifPresentOrElse(photoRepository::delete, ()->{
                    throw new ResourceNotFoundException(FeedBackMessage.NOT_FOUND);
                });

    }

    @Override
    public Photo updatePhoto(Long id, MultipartFile file) throws SQLException, IOException {
        Photo photo = getPhotoById(id);
        if (photo != null) {
            byte[] photoBytes = file.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            photo.setImage(photoBlob);
            photo.setFileType(file.getContentType());
            photo.setFileName(file.getOriginalFilename());
            return photoRepository.save(photo);
        }
        throw new ResourceNotFoundException(FeedBackMessage.NOT_FOUND);
    }

    @Override
    public byte[] getImageData(Long id) throws SQLException {
        Photo photo = getPhotoById(id);
        if (photo != null) {
            Blob photoBlob = photo.getImage();
            int blobLength = (int) photoBlob.length();
            return new byte[blobLength];
        }
        return null;
    }

}
