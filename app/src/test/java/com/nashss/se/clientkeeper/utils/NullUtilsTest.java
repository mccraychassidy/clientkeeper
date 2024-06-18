package com.nashss.se.clientkeeper.utils;

import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

public class NullUtilsTest {

    @Test
    public void ifNull_withNonNullObject_returnsObject() {
        // GIVEN
        String obj = "non-null";
        String valIfNull = "default";

        // WHEN
        String result = NullUtils.ifNull(obj, valIfNull);

        // THEN
        assertEquals(obj, result);
    }

    @Test
    public void ifNull_withNullObject_returnsValIfNull() {
        // GIVEN
        String obj = null;
        String valIfNull = "default";

        // WHEN
        String result = NullUtils.ifNull(obj, valIfNull);

        // THEN
        assertEquals(valIfNull, result);
    }

    @Test
    public void ifNull_withNonNullObjectAndSupplier_returnsObject() {
        // GIVEN
        String obj = "non-null";
        Supplier<String> valIfNullSupplier = () -> "default";

        // WHEN
        String result = NullUtils.ifNull(obj, valIfNullSupplier);

        // THEN
        assertEquals(obj, result);
    }

    @Test
    public void ifNull_withNullObjectAndSupplier_returnsValIfNullSupplier() {
        // GIVEN
        String obj = null;
        Supplier<String> valIfNullSupplier = () -> "default";

        // WHEN
        String result = NullUtils.ifNull(obj, valIfNullSupplier);

        // THEN
        assertEquals("default", result);
    }

    @Test
    public void ifNotNull_withNonNullObject_returnsValIfNotNull() {
        // GIVEN
        String obj = "non-null";
        String valIfNotNull = "value";

        // WHEN
        String result = NullUtils.ifNotNull(obj, valIfNotNull);

        // THEN
        assertEquals(valIfNotNull, result);
    }

    @Test
    public void ifNotNull_withNullObject_returnsNull() {
        // GIVEN
        String obj = null;
        String valIfNotNull = "value";

        // WHEN
        String result = NullUtils.ifNotNull(obj, valIfNotNull);

        // THEN
        assertNull(result);
    }

    @Test
    public void ifNotNull_withNonNullObjectAndSupplier_returnsValIfNotNullSupplier() {
        // GIVEN
        String obj = "non-null";
        Supplier<String> valIfNotNullSupplier = () -> "value";

        // WHEN
        String result = NullUtils.ifNotNull(obj, valIfNotNullSupplier);

        // THEN
        assertEquals("value", result);
    }

    @Test
    public void ifNotNull_withNullObjectAndSupplier_returnsNull() {
        // GIVEN
        String obj = null;
        Supplier<String> valIfNotNullSupplier = () -> "value";

        // WHEN
        String result = NullUtils.ifNotNull(obj, valIfNotNullSupplier);

        // THEN
        assertNull(result);
    }
}
