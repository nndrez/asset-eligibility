package com.overit.assessment.filter.property;

import com.overit.assessment.api.Filter;
import com.overit.assessment.operator.NumericOperator;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public record NumericComparisonFilter(String propertyName, NumericOperator operator,
                                      BigDecimal targetValue) implements Filter {

    public NumericComparisonFilter {
        Objects.requireNonNull(propertyName, "Property name cannot be null");
        Objects.requireNonNull(operator, "Operator cannot be null");
        Objects.requireNonNull(targetValue, "Target value cannot be null");
    }

    @Override
    public boolean matches(Map<String, String> asset) {
        if (asset == null || !asset.containsKey(propertyName)) {
            return false;
        }
        String rawValue = asset.get(propertyName);
        if (rawValue == null) {
            return false;
        }

        try {
            BigDecimal actualValue = new BigDecimal(rawValue.trim());
            return operator.apply(actualValue, targetValue);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return propertyName + " " + operator.getSymbol() + " " + targetValue;
    }
}