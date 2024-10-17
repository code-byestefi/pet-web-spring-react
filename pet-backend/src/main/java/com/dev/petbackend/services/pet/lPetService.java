package com.dev.petbackend.services.pet;

import com.dev.petbackend.model.factory.Pet;

import java.util.List;

public interface lPetService {

    List<Pet> savePetForAppointment(List<Pet> pets);
    Pet updatePet(Pet pet, Long id);
    void deletePet(Long id);
    Pet getPetById(Long id);

    List<String> getPetTypes();

    List<String> getPetColors();

    List<String> getPetBreeds(String petType);
}
