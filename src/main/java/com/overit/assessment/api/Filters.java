package com.overit.assessment.api;

import com.overit.assessment.filter.composite.AndFilter;
import com.overit.assessment.filter.composite.NotFilter;
import com.overit.assessment.filter.composite.OrFilter;
import com.overit.assessment.filter.constant.AlwaysMatchFilter;
import com.overit.assessment.filter.constant.NeverMatchFilter;
import com.overit.assessment.filter.property.ExistsFilter;
import com.overit.assessment.filter.property.NumericComparisonFilter;
import com.overit.assessment.filter.property.PropertyComparisonFilter;
import com.overit.assessment.filter.property.StringEqualsFilter;
import com.overit.assessment.operator.NumericOperator;

import java.math.BigDecimal;

public final class Filters {

    private Filters() {
    }

    public static Filter alwaysMatch() {
        return AlwaysMatchFilter.INSTANCE;
    }

    public static Filter neverMatch() {
        return NeverMatchFilter.INSTANCE;
    }

    public static Filter exists(String propertyName) {
        return new ExistsFilter(propertyName);
    }

    public static Filter equals(String propertyName, String expectedValue) {
        return new StringEqualsFilter(propertyName, expectedValue);
    }

    public static Filter greaterThan(String propertyName, double value) {
        return new NumericComparisonFilter(propertyName, NumericOperator.GREATER_THAN, BigDecimal.valueOf(value));
    }

    public static Filter greaterThanOrEquals(String propertyName, double value) {
        return new NumericComparisonFilter(propertyName, NumericOperator.GREATER_THAN_OR_EQUAL, BigDecimal.valueOf(value));
    }

    public static Filter lowerThan(String propertyName, double value) {
        return new NumericComparisonFilter(propertyName, NumericOperator.LESS_THAN, BigDecimal.valueOf(value));
    }

    public static Filter lowerThanOrEquals(String propertyName, double value) {
        return new NumericComparisonFilter(propertyName, NumericOperator.LESS_THAN_OR_EQUAL, BigDecimal.valueOf(value));
    }

    public static Filter compareProperties(String leftProperty, NumericOperator operator, String rightProperty) {
        return new PropertyComparisonFilter(leftProperty, operator, rightProperty);
    }

    public static Filter and(Filter... filters) {
        return new AndFilter(filters);
    }

    public static Filter or(Filter... filters) {
        return new OrFilter(filters);
    }

    public static Filter not(Filter filter) {
        return new NotFilter(filter);
    }
}