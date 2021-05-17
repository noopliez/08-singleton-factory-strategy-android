package de.thro.inf.prg3.a08.filtering;

import java.util.List;

import de.thro.inf.prg3.a08.model.Meal;

public interface MealsFilter {
    List<Meal> filter(List<Meal> meals);
}
