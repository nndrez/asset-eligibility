package com.overit.assessment.filter.property;

import com.overit.assessment.api.Filter;

import java.util.Map;
import java.util.Objects;

public record ExistsFilter(String propertyName) implements Filter {

    public ExistsFilter {
        Objects.requireNonNull(propertyName, "Property name cannot be null");
    }

    @Override
    public boolean matches(Map<String, String> asset) {
        return asset != null && asset.containsKey(propertyName);
    }

    @Override
    public String toString() {
        return "EXISTS(" + propertyName + ")";
    }
}