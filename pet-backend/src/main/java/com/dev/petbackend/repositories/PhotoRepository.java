package com.dev.petbackend.repositories;

import com.dev.petbackend.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
