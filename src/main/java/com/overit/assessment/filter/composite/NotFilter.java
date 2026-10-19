package com.overit.assessment.filter.composite;

import com.overit.assessment.api.Filter;

import java.util.Map;
import java.util.Objects;

public record NotFilter(Filter filter) implements Filter {

    public NotFilter {
        Objects.requireNonNull(filter, "Filter cannot be null");
    }

    @Override
    public boolean matches(Map<String, String> asset) {
        return !filter.matches(asset);
    }

    @Override
    public String toString() {
        return "NOT(" + filter.toString() + ")";
    }
}