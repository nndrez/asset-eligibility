package com.overit.assessment.filter.composite;

import com.overit.assessment.api.Filter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record OrFilter(List<Filter> filters) implements Filter {

    public OrFilter {
        if (filters == null || filters.isEmpty()) {
            throw new IllegalArgumentException("Filters cannot be null or empty");
        }
        filters = List.copyOf(filters);
    }

    public OrFilter(Filter... filters) {
        this(filters != null ? Arrays.asList(filters) : null);
    }

    @Override
    public boolean matches(Map<String, String> asset) {
        for (Filter filter : filters) {
            if (filter.matches(asset)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + filters.stream().map(Object::toString).collect(Collectors.joining(" OR ")) + ")";
    }
}