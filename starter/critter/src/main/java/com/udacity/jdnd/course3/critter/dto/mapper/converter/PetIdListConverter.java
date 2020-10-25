package com.udacity.jdnd.course3.critter.dto.mapper.converter;

import com.udacity.jdnd.course3.critter.entity.Pet;
import org.modelmapper.AbstractConverter;

import java.util.List;
import java.util.stream.Collectors;

public class PetIdListConverter extends AbstractConverter<List<Long>, List<Pet>> {


    @Override
    protected List<Pet> convert(List<Long> petIds) {
        return petIds.stream().map(Pet::new).collect(Collectors.toList());

    }
}
