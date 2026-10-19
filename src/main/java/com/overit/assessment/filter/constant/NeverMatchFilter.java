package com.overit.assessment.filter.constant;

import com.overit.assessment.api.Filter;

import java.util.Map;

public final class NeverMatchFilter implements Filter {
    public static final NeverMatchFilter INSTANCE = new NeverMatchFilter();

    private NeverMatchFilter() {
    }

    @Override
    public boolean matches(Map<String, String> asset) {
        return false;
    }

    @Override
    public String toString() {
        return "ALWAYS_FALSE";
    }
}