package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private PetService petService;
    private ModelMapper modelMapper;

    public PetController(PetService petService, ModelMapper modelMapper) {
        this.petService = petService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = modelMapper.map(petDTO, Pet.class);
        Pet savedPet = petService.savePet(pet);
        return modelMapper.map(savedPet, PetDTO.class);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Optional<Pet> petById = petService.getPetById(petId);
        return petById.map(p->modelMapper.map(p,PetDTO.class))
                .orElse(null);
    }

    @GetMapping
    public List<PetDTO> getPets() {
        List<Pet> allPets = petService.getAllPets();
        return allPets.stream()
                .map(p -> modelMapper.map(p, PetDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> allPetsByOwnerId = petService.getAllPetsByOwnerId(ownerId);
        return allPetsByOwnerId.stream()
                .map(p -> modelMapper.map(p, PetDTO.class))
                .collect(Collectors.toList());
    }
}
