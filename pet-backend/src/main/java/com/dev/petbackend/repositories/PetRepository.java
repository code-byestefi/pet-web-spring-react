package com.dev.petbackend.repositories;

import com.dev.petbackend.model.factory.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
    Pet getPetById(Long petId);
}
