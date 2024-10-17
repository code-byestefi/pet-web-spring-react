package com.dev.petbackend.services.pet;

import com.dev.petbackend.exceptions.ResourceNotFoundException;
import com.dev.petbackend.model.factory.Pet;
import com.dev.petbackend.repositories.PetRepository;
import com.dev.petbackend.utils.FeedBackMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService implements lPetService {

    private final PetRepository petRepository;

    @Override
    public List<Pet> savePetForAppointment(List<Pet> pets) {
        return petRepository.saveAll(pets);
    }

    @Override
    public Pet updatePet(Pet pet, Long petId) {
        Pet exsitedPet = petRepository.getPetById(petId);
        exsitedPet.setName(pet.getName());
        exsitedPet.setType(pet.getType());
        exsitedPet.setColor(pet.getColor());
        exsitedPet.setBreed(pet.getBreed());
        exsitedPet.setAge(pet.getAge());
        return petRepository.save(exsitedPet);
    }

    @Override
    public void deletePet(Long petId) {
        petRepository.findById(petId).ifPresentOrElse(petRepository::delete, () -> {
            throw new ResourceNotFoundException(FeedBackMessage.NOT_FOUND);
        });
    }

    @Override
    public Pet getPetById(Long petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException(FeedBackMessage.NOT_FOUND));
    }

    @Override
    public List<String> getPetTypes(){
        return petRepository.getDistinctPetTypes();
    }

    @Override
    public List<String> getPetColors(){
        return petRepository.getDistinctPetColors();
    }

    @Override
    public List<String> getPetBreeds(String petType){
        return petRepository.getDistinctPetBreedsByPetType(petType);
    }

}
