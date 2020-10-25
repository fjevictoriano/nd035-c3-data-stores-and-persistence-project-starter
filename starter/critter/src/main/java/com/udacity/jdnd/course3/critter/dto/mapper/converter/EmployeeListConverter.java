package com.udacity.jdnd.course3.critter.dto.mapper.converter;

import com.udacity.jdnd.course3.critter.entity.Employee;
import org.modelmapper.AbstractConverter;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeListConverter extends AbstractConverter<List<Employee>, List<Long>> {
    @Override
    protected List<Long> convert(List<Employee> employees) {

        return employees.stream().map(Employee::getId).collect(Collectors.toList());
    }
}
