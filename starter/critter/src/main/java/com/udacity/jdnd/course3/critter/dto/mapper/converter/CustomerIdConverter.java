package com.udacity.jdnd.course3.critter.dto.mapper.converter;

import com.udacity.jdnd.course3.critter.entity.Customer;
import org.modelmapper.AbstractConverter;

public class CustomerIdConverter extends AbstractConverter<Long, Customer> {
    @Override
    protected Customer convert(Long petId) {
        return new Customer(petId);
    }
}
