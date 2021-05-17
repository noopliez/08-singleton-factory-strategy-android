package de.thro.inf.prg3.a08.filtering;

import java.util.List;
import java.util.stream.Collectors;

import de.thro.inf.prg3.a08.model.Meal;

public abstract class FilterBase implements MealsFilter {

    @Override
    public List<Meal> filter(List<Meal> meals) {
        return meals.stream().filter(this::include).collect(Collectors.toList());
    }

    protected abstract boolean include(Meal m);
}
