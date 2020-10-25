package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.dto.mapper.converter.PetListConverter;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.EmployeeSkill;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.assertj.core.util.Sets;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private CustomerService customerService;
    private ModelMapper modelMapper;
    private EmployeeService employeeService;

    @Autowired
    public UserController(CustomerService customerService,
                          EmployeeService employeeService,
                          ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
        this.employeeService = employeeService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customerToBeSaved = modelMapper.map(customerDTO, Customer.class);
        Customer savedCustomer = customerService.saveCustomer(customerToBeSaved);
        return modelMapper.map(savedCustomer, CustomerDTO.class);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers()
                .stream()
                .map(c -> modelMapper.map(c, CustomerDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        Optional<Customer> ownerByPetId = customerService.getOwnerByPetId(petId);
        return ownerByPetId.map(o -> modelMapper.map(o, CustomerDTO.class)).orElse(null);

    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employeeTobeSaved = modelMapper.map(employeeDTO, Employee.class);
        Employee savedEmployee = employeeService.saveEmployee(employeeTobeSaved);
        return modelMapper.map(savedEmployee, EmployeeDTO.class);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return employeeService.getEmployeeById(employeeId)
                .map(e->modelMapper.map(e, EmployeeDTO.class)).orElse(null);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.getEmployeeById(employeeId)
                .ifPresent(e->e.setDaysAvailable(daysAvailable));
    }

    @PostMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        Set<EmployeeSkill> skills = employeeDTO.getSkills();
        DayOfWeek dayOfWeek = employeeDTO.getDate().getDayOfWeek();
        List<Employee> employeesBySkills =
                employeeService.getEmployeesAvailability(dayOfWeek, skills);
        return employeesBySkills.stream()
                .map(e -> modelMapper.map(e, EmployeeDTO.class))
                .collect(Collectors.toList());


    }

}
