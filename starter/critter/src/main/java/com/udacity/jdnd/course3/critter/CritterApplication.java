package com.udacity.jdnd.course3.critter;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.dto.mapper.converter.*;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Launches the Spring application. Unmodified from starter code.
 */
@SpringBootApplication
public class CritterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CritterApplication.class, args);
    }


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        addConverters(modelMapper);
        return modelMapper;
    }

    private void addConverters(ModelMapper modelMapper) {
        addScheduleConverters(modelMapper);
        addCustomerConverters(modelMapper);
        addPetConverters(modelMapper);

    }

    private void addPetConverters(ModelMapper modelMapper) {
        TypeMap<PetDTO, Pet> petTypeToDTOMap =
                modelMapper.createTypeMap(PetDTO.class, Pet.class);

    petTypeToDTOMap.addMappings(mapper -> mapper.using(new CustomerIdConverter())
                .map(PetDTO::getOwnerId, Pet::setOwner));

    }

    private void addCustomerConverters(ModelMapper modelMapper) {
        TypeMap<Customer, CustomerDTO> customerTypeToDTOMap =
                modelMapper.createTypeMap(Customer.class, CustomerDTO.class);

        TypeMap<CustomerDTO, Customer> customerDTOToTyeMap =
                modelMapper.createTypeMap(CustomerDTO.class, Customer.class);

        customerTypeToDTOMap.addMappings(mapper -> mapper.using(new PetListConverter())
                .map(Customer::getPets, CustomerDTO::setPetIds));

        customerDTOToTyeMap.addMappings(mapper -> mapper.using(new PetIdListConverter())
                .map(CustomerDTO::getPetIds, Customer::setPets));
    }

    private void addScheduleConverters(ModelMapper modelMapper) {
        TypeMap<ScheduleDTO, Schedule> scheduleDTOToTypeMap
                = modelMapper.createTypeMap(ScheduleDTO.class, Schedule.class);

        TypeMap<Schedule, ScheduleDTO> scheduleTypeToDTOMap =
                modelMapper.createTypeMap(Schedule.class, ScheduleDTO.class);

        scheduleDTOToTypeMap.addMappings(mapper -> mapper.using(new PetIdListConverter())
                .map(ScheduleDTO::getPetIds, Schedule::setPets));

        scheduleTypeToDTOMap.addMappings(mapper -> mapper.using(new PetListConverter())
                .map(Schedule::getPets, ScheduleDTO::setPetIds));

        scheduleDTOToTypeMap.addMappings(mapper -> mapper.using(new EmployeeIdListConverter())
                .map(ScheduleDTO::getEmployeeIds, Schedule::setEmployees));

        scheduleTypeToDTOMap.addMappings(mapper -> mapper.using(new EmployeeListConverter())
                .map(Schedule::getEmployees, ScheduleDTO::setEmployeeIds));
    }
}
