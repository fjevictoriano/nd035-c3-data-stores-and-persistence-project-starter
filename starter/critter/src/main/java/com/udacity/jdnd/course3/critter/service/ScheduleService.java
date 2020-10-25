package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private ScheduleRepository scheduleRepository;
    private PetService petService;
    private EmployeeService employeeService;
    private CustomerService customerService;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository,
                           PetService petService,
                           EmployeeService employeeService,
                           CustomerService customerService) {
        this.scheduleRepository = scheduleRepository;
        this.petService = petService;
        this.employeeService = employeeService;
        this.customerService = customerService;
    }

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.saveSchedule(schedule);
    }

    public List<Schedule> getAllSchedules() {
        List<Schedule> schedules = new ArrayList<>();
        Iterable<Schedule> all = scheduleRepository.findAll();
        all.forEach(schedules::add);
        return schedules;
    }


    public List<Schedule> getSchedulesByPetId(Long petId) {
        Optional<Pet> petById = petService.getPetById(petId);
        return new ArrayList<>(petById.map(Pet::getSchedules)
                .orElse(Collections.emptyList()));
    }

    public List<Schedule> getSchedulesByEmployeeId(Long employeeId) {
        Optional<Employee> petById = employeeService.getEmployeeById(employeeId);
        return new ArrayList<>(petById.map(Employee::getSchedules)
                .orElse(Collections.emptyList()));
    }

    public List<Schedule> getSchedulesByCustomerId(Long customerId) {
        List<Schedule> schedules = new ArrayList<>();
        Optional<Customer> customerById = customerService.getCustomerById(customerId);
        List<Pet> pets = customerById
                .map(Customer::getPets).orElse(Collections.emptyList());
        pets.forEach(p->schedules.addAll(p.getSchedules()));
        return schedules;
    }


}
