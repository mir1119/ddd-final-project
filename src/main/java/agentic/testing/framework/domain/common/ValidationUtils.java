package agentic.testing.framework.domain.common;

import java.util.Collection;

public final class ValidationUtils {
    private ValidationUtils() {
    }

    public static void requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new DomainValidationException(fieldName + " must not be blank.");
        }
    }

    public static void requireNotNull(Object value, String fieldName) {
        if (value == null) {
            throw new DomainValidationException(fieldName + " must not be null.");
        }
    }

    public static void requireNotEmpty(Collection<?> values, String fieldName) {
        if (values == null || values.isEmpty()) {
            throw new DomainValidationException(fieldName + " must not be empty.");
        }
    }

    public static void requireNonNegative(double value, String fieldName) {
        if (value < 0) {
            throw new DomainValidationException(fieldName + " must be non-negative.");
        }
    }
}
