package com.overit.assessment.filter.property;

import com.overit.assessment.api.Filter;

import java.util.Map;
import java.util.Objects;

public record StringEqualsFilter(String propertyName, String expectedValue) implements Filter {

    public StringEqualsFilter {
        Objects.requireNonNull(propertyName, "Property name cannot be null");
    }

    @Override
    public boolean matches(Map<String, String> asset) {
        if (asset == null || !asset.containsKey(propertyName)) {
            return false;
        }
        String actualValue = asset.get(propertyName);
        if (actualValue == null && expectedValue == null) {
            return true;
        }
        if (actualValue == null || expectedValue == null) {
            return false;
        }
        return actualValue.equalsIgnoreCase(expectedValue);
    }

    @Override
    public String toString() {
        return propertyName + " EQUALS_IGNORE_CASE \"" + expectedValue + "\"";
    }
}