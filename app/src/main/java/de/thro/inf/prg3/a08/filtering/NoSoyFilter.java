package de.thro.inf.prg3.a08.filtering;

import de.thro.inf.prg3.a08.model.Meal;

public class NoSoyFilter extends FilterBase {

    @Override
    protected boolean include(Meal m) {
        boolean containsSoy = false;

        for (String s : m.getNotes()) {
            containsSoy = containsSoy || s.contains("Soja");
        }

        return !containsSoy;
    }
}
