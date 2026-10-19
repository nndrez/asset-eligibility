package com.overit.assessment.api;

import com.overit.assessment.filter.composite.AndFilter;
import com.overit.assessment.filter.composite.NotFilter;
import com.overit.assessment.filter.composite.OrFilter;

import java.util.Map;
import java.util.function.Predicate;

@FunctionalInterface
public interface Filter extends Predicate<Map<String, String>> {

    boolean matches(Map<String, String> asset);

    @Override
    default boolean test(Map<String, String> asset) {
        return matches(asset);
    }

    default Filter and(Filter other) {
        return new AndFilter(this, other);
    }

    default Filter or(Filter other) {
        return new OrFilter(this, other);
    }

    @Override
    default Filter negate() {
        return new NotFilter(this);
    }

}
