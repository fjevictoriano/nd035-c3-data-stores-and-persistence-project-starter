package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private ModelMapper modelMapper;
    private ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ModelMapper modelMapper,
                              ScheduleService scheduleService) {
        this.modelMapper = modelMapper;
        this.scheduleService = scheduleService;
    }



    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);
        Schedule savedSchedule = scheduleService.saveSchedule(schedule);
        return modelMapper.map(savedSchedule, ScheduleDTO.class);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> allSchedules = scheduleService.getAllSchedules();
        return allSchedules.stream()
                .map(s->modelMapper.map(s,ScheduleDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
       return scheduleService.getSchedulesByPetId(petId).stream()
               .map(s->modelMapper.map(s,ScheduleDTO.class))
               .collect(Collectors.toList());


    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.getSchedulesByEmployeeId(employeeId).stream()
                .map(s->modelMapper.map(s,ScheduleDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return scheduleService.getSchedulesByCustomerId(customerId).stream()
                .map(s->modelMapper.map(s,ScheduleDTO.class))
                .collect(Collectors.toList());
    }
}
