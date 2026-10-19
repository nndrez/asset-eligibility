package com.overit.assessment;

import com.overit.assessment.api.Filter;
import com.overit.assessment.api.Filters;
import com.overit.assessment.operator.NumericOperator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class EdgeCasesTest {

    @Test
    @DisplayName("Fail-safe: valutazione su mappa null o vuota")
    void shouldHandleNullOrEmptyAssetSafely() {
        Filter filter = Filters.equals("zone", "Lombardy");

        assertThat(filter.matches(null)).isFalse();
        assertThat(filter.matches(Collections.emptyMap())).isFalse();
    }

    @Test
    @DisplayName("Fail-safe: proprietà mancante nella mappa restituisce false per confronto numerico e stringa")
    void shouldReturnFalseWhenPropertyIsMissing() {
        Map<String, String> asset = Map.of("isOnline", "true");

        Filter batteryFilter = Filters.greaterThanOrEquals("batteryLevel", 20);
        Filter zoneFilter = Filters.equals("zone", "Lombardy");

        assertThat(batteryFilter.matches(asset)).isFalse();
        assertThat(zoneFilter.matches(asset)).isFalse();
    }

    @Test
    @DisplayName("Fail-safe: valore non numerico restituisce false senza lanciare NumberFormatException")
    void shouldReturnFalseOnInvalidNumericValue() {
        Map<String, String> corruptedAsset = Map.of("batteryLevel", "not_a_number");

        Filter filter = Filters.greaterThanOrEquals("batteryLevel", 20);

        // Deve restituire false in modo sicuro senza propagare eccezioni
        assertThat(filter.matches(corruptedAsset)).isFalse();
    }

    @Test
    @DisplayName("Fail-safe: confronto tra due proprietà se una delle due è assente o non numerica")
    void shouldHandleMissingOrCorruptedPropertiesInComparison() {
        Filter filter = Filters.compareProperties("temp1", NumericOperator.LESS_THAN, "temp2");

        Map<String, String> missingOne = Map.of("temp1", "50");
        Map<String, String> invalidFormat = Map.of("temp1", "50", "temp2", "high");

        assertThat(filter.matches(missingOne)).isFalse();
        assertThat(filter.matches(invalidFormat)).isFalse();
    }

    @Test
    @DisplayName("Uguaglianza stringa: gestione di valori nulli all'interno della mappa")
    void shouldHandleNullValuesInsideMap() {
        Map<String, String> assetWithNull = new HashMap<>();
        assetWithNull.put("zone", null);

        Filter eqExpected = Filters.equals("zone", "Lombardy");
        Filter eqNull = Filters.equals("zone", null);

        assertThat(eqExpected.matches(assetWithNull)).isFalse();
        assertThat(eqNull.matches(assetWithNull)).isTrue();
    }
}