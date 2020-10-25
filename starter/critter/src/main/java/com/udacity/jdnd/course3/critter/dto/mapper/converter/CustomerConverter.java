package com.udacity.jdnd.course3.critter.dto.mapper.converter;

import com.udacity.jdnd.course3.critter.entity.Customer;
import org.modelmapper.AbstractConverter;

public class CustomerConverter extends AbstractConverter<Customer, Long> {
    @Override
    protected Long convert(Customer customer) {
        return customer.getId();
    }
}
