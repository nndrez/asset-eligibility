package com.overit.assessment.filter.constant;

import com.overit.assessment.api.Filter;

import java.util.Map;

public final class AlwaysMatchFilter implements Filter {
    public static final AlwaysMatchFilter INSTANCE = new AlwaysMatchFilter();

    private AlwaysMatchFilter() {
    }

    @Override
    public boolean matches(Map<String, String> asset) {
        return true;
    }

    @Override
    public String toString() {
        return "ALWAYS_TRUE";
    }
}