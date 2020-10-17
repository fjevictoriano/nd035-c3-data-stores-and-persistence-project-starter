package com.udacity.jdnd.course3.critter.entity;

import java.io.Serializable;

/**
 * A example list of pet type metadata that could be included on a request to create a pet.
 */
public enum PetType implements Serializable {
    CAT, DOG, LIZARD, BIRD, FISH, SNAKE, OTHER;
}
