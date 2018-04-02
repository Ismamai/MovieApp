package com.iblesa.movieapp.model;

import java.util.Arrays;
import java.util.List;

/**
 * SortCriteria to store the allowed criterias
 */

public class SortCriteria {
    //Constants
    public static final String POPULAR = "popular";
    public static final String RATE = "rate";
    public static final String FAVORITES = "favorites";

    private static final List<String> ALLOWED_CRITERIAS = Arrays.asList(POPULAR, RATE, FAVORITES);

    private final String criteria;



    public SortCriteria(String criteria) {
        if (!ALLOWED_CRITERIAS.contains(criteria)) {
            throw new IllegalArgumentException("Criteria is not allowed. Requested " + criteria + " but allowed are " + ALLOWED_CRITERIAS);
        }
        this.criteria = criteria;
    }

    public String getCriteria() {
        return criteria;
    }
}
