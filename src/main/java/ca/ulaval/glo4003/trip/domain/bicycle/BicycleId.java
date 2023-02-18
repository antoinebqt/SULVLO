package ca.ulaval.glo4003.trip.domain.bicycle;

import java.util.Objects;
import java.util.UUID;

public class BicycleId {
    private final UUID id;

    public BicycleId(UUID id) {
        this.id = id;
    }

    public BicycleId() {
        this.id = UUID.randomUUID();
    }

    public BicycleId(BicycleId other) {
        this(other.id);
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BicycleId other = (BicycleId) o;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
