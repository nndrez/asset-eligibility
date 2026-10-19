package com.overit.assessment.filter.property;

import com.overit.assessment.api.Filter;
import com.overit.assessment.api.Filters;
import com.overit.assessment.operator.NumericOperator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class PropertyFiltersTest {

    @Test
    void testExistsFilter() {
        Map<String, String> asset = Map.of("firmwareVersion", "1.0.0");

        Filter exists = Filters.exists("firmwareVersion");
        Filter notExists = Filters.exists("FIRMWAREVERSION");

        assertThat(exists.matches(asset)).isTrue();
        assertThat(notExists.matches(asset)).isFalse();
    }

    @Test
    void testStringEqualityCaseInsensitive() {
        Map<String, String> asset = Map.of("zone", "Lombardy-Plant-A");

        Filter filterLower = Filters.equals("zone", "lombardy-plant-a");
        Filter filterUpper = Filters.equals("zone", "LOMBARDY-PLANT-A");
        Filter filterMismatch = Filters.equals("zone", "Veneto-Plant-B");

        assertThat(filterLower.matches(asset)).isTrue();
        assertThat(filterUpper.matches(asset)).isTrue();
        assertThat(filterMismatch.matches(asset)).isFalse();
    }

    @Test
    @DisplayName("PropertyComparisonFilter: confronta dinamicamente due proprietà dello stesso asset")
    void testCompareTwoPropertiesOfSameAsset() {
        Filter tempSafetyCheck = Filters.compareProperties(
                "currentTemperature",
                NumericOperator.LESS_THAN_OR_EQUAL,
                "maxAllowedTemperature"
        );

        Map<String, String> safeAsset = Map.of(
                "currentTemperature", "75.5",
                "maxAllowedTemperature", "90.0"
        );
        assertThat(tempSafetyCheck.matches(safeAsset)).isTrue();

        Map<String, String> overheatingAsset = Map.of(
                "currentTemperature", "95.0",
                "maxAllowedTemperature", "90.0"
        );
        assertThat(tempSafetyCheck.matches(overheatingAsset)).isFalse();
    }
}