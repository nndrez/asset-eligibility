package com.overit.assessment.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class FiltersTest {

    private Map<String, String> sensor;

    @BeforeEach
    void setUp() {
        sensor = new HashMap<>();
        sensor.put("zone", "Lombardy-Plant-A");
        sensor.put("isOnline", "true");
        sensor.put("batteryLevel", "85");
        sensor.put("firmwareVersion", "2.4.1");
    }

    @Test
    void shouldMatchTelemetrySensorCriteria() {
        Filter criteria = Filters.and(
                Filters.equals("zone", "Lombardy-Plant-A"),
                Filters.equals("isOnline", "true"),
                Filters.greaterThanOrEquals("batteryLevel", 20)
        );

        assertThat(criteria.matches(sensor)).isTrue();

        sensor.put("batteryLevel", "10");
        assertThat(criteria.matches(sensor)).isFalse();
    }

    @Test
    void shouldWorkWithFluentSyntax() {
        Filter criteria = Filters.equals("zone", "Lombardy-Plant-A")
                .and(Filters.equals("isOnline", "true"))
                .and(Filters.greaterThanOrEquals("batteryLevel", 20));

        assertThat(criteria.matches(sensor)).isTrue();
    }

    @Test
    void shouldProduceMeaningfulToString() {
        Filter criteria = Filters.and(
                Filters.equals("zone", "Lombardy-Plant-A"),
                Filters.greaterThanOrEquals("batteryLevel", 20)
        );

        assertThat(criteria.toString())
                .isEqualTo("(zone EQUALS_IGNORE_CASE \"Lombardy-Plant-A\" AND batteryLevel >= 20.0)");
    }

    @Test
    void shouldProduceMeaningfulToStringWithFluentSyntax() {
        Filter criteria = Filters.equals("zone", "Lombardy-Plant-A")
                .and(Filters.greaterThanOrEquals("batteryLevel", 20));

        assertThat(criteria.toString())
                .isEqualTo("(zone EQUALS_IGNORE_CASE \"Lombardy-Plant-A\" AND batteryLevel >= 20.0)");
    }
}