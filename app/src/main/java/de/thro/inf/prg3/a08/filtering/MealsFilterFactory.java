package de.thro.inf.prg3.a08.filtering;

import java.util.HashMap;
import java.util.Map;

public abstract class MealsFilterFactory {

    private static final Map<String, MealsFilter> filter;

    static {
        filter = new HashMap<>();
        filter.put("All", new NoFilter());
        filter.put("Vegetarian", new VegetarianFilter());
        filter.put("No soy", new NoSoyFilter());
        filter.put("No pork", new NoPorkFilter());
    }

    public static MealsFilter getStrategy(String key) {
        return filter.get(key);
    }
}
