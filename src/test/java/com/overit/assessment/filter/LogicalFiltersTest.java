package com.overit.assessment.filter;

import com.overit.assessment.api.Filter;
import com.overit.assessment.api.Filters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LogicalFiltersTest {

    private final Map<String, String> asset = Map.of(
            "zone", "Zone-A",
            "type", "Sensor"
    );

    @Test
    void testConstantFilters() {
        assertThat(Filters.alwaysMatch().matches(asset)).isTrue();
        assertThat(Filters.neverMatch().matches(asset)).isFalse();
    }

    @Test
    void testNotFilter() {
        Filter isZoneA = Filters.equals("zone", "Zone-A");
        Filter isNotZoneA = Filters.not(isZoneA);

        assertThat(isZoneA.matches(asset)).isTrue();
        assertThat(isNotZoneA.matches(asset)).isFalse();
    }

    @Test
    void testOrFilter() {
        Filter filter = Filters.or(
                Filters.equals("zone", "Zone-B"),
                Filters.equals("type", "Sensor")
        );

        assertThat(filter.matches(asset)).isTrue();
    }

    @Test
    void testOrFilterWithFluentSyntax() {
        Filter filter = Filters.equals("zone", "Zone-B")
                        .or(Filters.equals("type", "Sensor"));

        assertThat(filter.matches(asset)).isTrue();
    }

    @Test
    void testNestedComplexFilter() {
        Filter nested = Filters.and(
                Filters.or(
                        Filters.equals("zone", "Zone-A"),
                        Filters.equals("zone", "Zone-B")
                ),
                Filters.not(Filters.equals("type", "Gateway"))
        );

        assertThat(nested.matches(asset)).isTrue();

        Map<String, String> gatewayAsset = Map.of(
                "zone", "Zone-A",
                "type", "Gateway"
        );
        assertThat(nested.matches(gatewayAsset)).isFalse();
    }
}