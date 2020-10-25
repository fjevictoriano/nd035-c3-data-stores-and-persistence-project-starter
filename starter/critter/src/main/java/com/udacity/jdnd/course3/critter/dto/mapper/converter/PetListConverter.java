package com.udacity.jdnd.course3.critter.dto.mapper.converter;

import com.udacity.jdnd.course3.critter.entity.Pet;
import org.modelmapper.AbstractConverter;

import java.util.List;
import java.util.stream.Collectors;

public class PetListConverter extends AbstractConverter<List<Pet>, List<Long>> {

    @Override
    protected List<Long> convert(List<Pet> pets) {

        return pets.stream().map(Pet::getId).collect(Collectors.toList());
    }
}
