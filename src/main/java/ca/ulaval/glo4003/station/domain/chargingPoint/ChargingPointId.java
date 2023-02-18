package ca.ulaval.glo4003.station.domain.chargingPoint;

import java.util.Objects;

public class ChargingPointId {

    private final Integer value;

    public ChargingPointId(int id) {
        this.value = id;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChargingPointId other = (ChargingPointId) o;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
