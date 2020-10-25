package com.udacity.jdnd.course3.critter.dto.mapper.converter;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import org.modelmapper.AbstractConverter;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeIdListConverter extends AbstractConverter<List<Long>, List<Employee>> {
    @Override
    protected List<Employee> convert(List<Long> employeeId) {
        return employeeId.stream().map(Employee::new).collect(Collectors.toList());

    }
}
