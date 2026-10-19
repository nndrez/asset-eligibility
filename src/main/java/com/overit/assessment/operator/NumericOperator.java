package com.overit.assessment.operator;

import java.math.BigDecimal;

public enum NumericOperator {
    GREATER_THAN(">") {
        @Override
        public boolean apply(BigDecimal left, BigDecimal right) {
            return left.compareTo(right) > 0;
        }
    },
    GREATER_THAN_OR_EQUAL(">=") {
        @Override
        public boolean apply(BigDecimal left, BigDecimal right) {
            return left.compareTo(right) >= 0;
        }
    },
    LESS_THAN("<") {
        @Override
        public boolean apply(BigDecimal left, BigDecimal right) {
            return left.compareTo(right) < 0;
        }
    },
    LESS_THAN_OR_EQUAL("<=") {
        @Override
        public boolean apply(BigDecimal left, BigDecimal right) {
            return left.compareTo(right) <= 0;
        }
    },
    EQUAL("==") {
        @Override
        public boolean apply(BigDecimal left, BigDecimal right) {
            return left.compareTo(right) == 0;
        }
    };

    private final String symbol;

    NumericOperator(String symbol) {
        this.symbol = symbol;
    }

    public abstract boolean apply(BigDecimal left, BigDecimal right);

    public String getSymbol() {
        return symbol;
    }
}