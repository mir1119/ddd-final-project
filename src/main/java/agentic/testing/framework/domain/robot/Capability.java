package agentic.testing.framework.domain.robot;

import agentic.testing.framework.domain.common.ValidationUtils;

import java.util.Locale;
import java.util.Objects;

/**
 * DDD Value Object.
 * A capability is compared by its normalized name, because the requirement document
 * says compatibility validation should compare Agent capabilities with Task required capabilities.
 */
public final class Capability {
    private final String capabilityId;
    private final String name;
    private final String description;

    public Capability(String capabilityId, String name, String description) {
        ValidationUtils.requireText(capabilityId, "capabilityId");
        ValidationUtils.requireText(name, "name");
        this.capabilityId = capabilityId.trim();
        this.name = name.trim();
        this.description = description == null ? "" : description.trim();
    }

    public String getCapabilityId() {
        return capabilityId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean match(Capability requiredCapability) {
        if (requiredCapability == null) {
            return false;
        }
        return normalize(name).equals(normalize(requiredCapability.name));
    }

    private String normalize(String value) {
        return value.trim().toLowerCase(Locale.ROOT).replace(" ", "_");
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Capability)) {
            return false;
        }
        Capability that = (Capability) other;
        return normalize(name).equals(normalize(that.name));
    }

    @Override
    public int hashCode() {
        return Objects.hash(normalize(name));
    }

    @Override
    public String toString() {
        return name;
    }
}
