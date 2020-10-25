package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private PetRepository petRepository;
    private CustomerService customerService;

    @Autowired
    public PetService(PetRepository petRepository, CustomerService customerService) {
        this.petRepository = petRepository;
        this.customerService = customerService;
    }

    public Optional<Pet> getPetById(Long id) {
        return petRepository.findById(id);
    }

    public Pet savePet(Pet pet) {
        Optional<Customer> ownerByPetId = customerService.getCustomerById(pet.getOwner().getId());
        if(ownerByPetId.isPresent()){
            Customer customer = ownerByPetId.get();
            pet.setOwner(customer);
            customer.getPets().add(pet);
        }
        return petRepository.save(pet);
    }

    public List<Pet> getAllPetsByOwnerId(Long id) {
        return petRepository.findAllByOwnerId(id);
    }

    public List<Pet> getAllPets() {
        List<Pet> pets = new ArrayList<>();
        Iterable<Pet> all = petRepository.findAll();
        all.forEach(pets::add);
        return pets;
    }
}
