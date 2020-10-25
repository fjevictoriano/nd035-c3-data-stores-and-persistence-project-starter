package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom{

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Schedule saveSchedule(Schedule schedule) {
        List<Pet> pets = schedule.getPets()
                .stream()
                .map(pet -> em.getReference(Pet.class, pet.getId()))
                .collect(Collectors.toList());
        pets.forEach(p->p.getSchedules().add(schedule));

        List<Employee> employees = schedule.getEmployees()
                .stream()
                .map(pet -> em.getReference(Employee.class, pet.getId()))
                .collect(Collectors.toList());

        employees.forEach(e->e.getSchedules().add(schedule));


        schedule.setPets(pets);
        schedule.setEmployees(employees);
        em.persist(schedule);
        return schedule;
    }

}
