package de.thro.inf.prg3.a08.filtering;

import de.thro.inf.prg3.a08.model.Meal;

public class VegetarianFilter extends FilterBase {

    @Override
    protected boolean include(Meal m) {
        return m.getCategory().contains("Vegetarisch") || m.getCategory().contains("Vegan");
    }
}
