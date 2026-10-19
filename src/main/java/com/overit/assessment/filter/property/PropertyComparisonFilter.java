package com.overit.assessment.filter.property;

import com.overit.assessment.api.Filter;
import com.overit.assessment.operator.NumericOperator;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public record PropertyComparisonFilter(String leftProperty, NumericOperator operator,
                                       String rightProperty) implements Filter {

    public PropertyComparisonFilter {
        Objects.requireNonNull(leftProperty, "Left property cannot be null");
        Objects.requireNonNull(operator, "Operator cannot be null");
        Objects.requireNonNull(rightProperty, "Right property cannot be null");
    }

    @Override
    public boolean matches(Map<String, String> asset) {
        if (asset == null || !asset.containsKey(leftProperty) || !asset.containsKey(rightProperty)) {
            return false;
        }
        String leftRaw = asset.get(leftProperty);
        String rightRaw = asset.get(rightProperty);
        if (leftRaw == null || rightRaw == null) {
            return false;
        }

        try {
            BigDecimal leftVal = new BigDecimal(leftRaw.trim());
            BigDecimal rightVal = new BigDecimal(rightRaw.trim());
            return operator.apply(leftVal, rightVal);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return leftProperty + " " + operator.getSymbol() + " " + rightProperty;
    }
}