package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {


    @Query("SELECT c, p FROM Customer c JOIN c.pets p WHERE p.id = :petId")
    Optional<Customer> findOwnerByPetId(Long petId);

}
