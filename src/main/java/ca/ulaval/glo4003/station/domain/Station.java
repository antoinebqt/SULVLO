package ca.ulaval.glo4003.station.domain;

import ca.ulaval.glo4003.station.domain.chargingPoint.ChargingPoint;
import ca.ulaval.glo4003.station.domain.chargingPoint.ChargingPointId;
import ca.ulaval.glo4003.station.domain.exceptions.StationInMaintenanceException;
import ca.ulaval.glo4003.station.domain.exceptions.StationNotInMaintenanceException;
import ca.ulaval.glo4003.station.domain.exceptions.StationSlotNotFoundException;
import ca.ulaval.glo4003.station.domain.technician.BicycleTransfer;
import ca.ulaval.glo4003.trip.domain.bicycle.Bicycle;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Station {

    private final StationId id;
    private final StationLocation location;
    private final String name;
    private final int capacity;
    private final Map<ChargingPointId, ChargingPoint> chargingPoints;
    private boolean inMaintenance;

    public Station(StationId id, StationLocation location, String name, int maxCapacity, boolean inMaintenance,
                   Map<ChargingPointId, ChargingPoint> chargingPoints) {
        this.id = id;
        this.location = location;
        this.name = name;
        this.capacity = maxCapacity;
        this.inMaintenance = inMaintenance;
        this.chargingPoints = chargingPoints;
    }

    public StationId getStationId() {
        return id;
    }

    public StationLocation getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public long getNumberOfBicycles() {
        return chargingPoints.values().stream().filter(ChargingPoint::isUsed).count();
    }

    public List<ChargingPoint> getChargingPoints() {
        return List.copyOf(chargingPoints.values());
    }

    public boolean isInMaintenance() {
        return this.inMaintenance;
    }

    public void setInMaintenance(boolean inMaintenance) {
        if (this.inMaintenance == inMaintenance) return;
        this.inMaintenance = inMaintenance;

        if (this.isInMaintenance()) {
            chargingPoints.values().forEach(ChargingPoint::idleBicycle);
        } else {
            chargingPoints.values().forEach(ChargingPoint::rechargeBicycle);
        }
    }

    public Bicycle rentBicycle(ChargingPointId chargingPointId) {
        verifyNotInMaintenance();
        ChargingPoint chargingPoint = findChargingPoint(chargingPointId);
        Bicycle rentedBicycle = chargingPoint.removeBicycle();
        rentedBicycle.activate();
        return rentedBicycle;
    }

    public void returnBicycle(ChargingPointId chargingPointId, Bicycle bicycle) {
        verifyNotInMaintenance();
        ChargingPoint chargingPoint = findChargingPoint(chargingPointId);
        chargingPoint.placeBicycle(bicycle);
    }

    public void removeBicyclesForTransfer(List<ChargingPointId> chargingPointIds, BicycleTransfer bicycleTransfer) {
        if (!isInMaintenance()) {
            throw new StationNotInMaintenanceException();
        }
        chargingPointIds.forEach(chargingPointId -> {
            Bicycle removedBicycle = findChargingPoint(chargingPointId).removeBicycle();
            bicycleTransfer.addBicycle(removedBicycle);
        });
    }

    public void returnBicyclesFromTransfer(List<ChargingPointId> chargingPointIds, BicycleTransfer bicycleTransfer) {
        List<Bicycle> bicycles = bicycleTransfer.removeBicycles(chargingPointIds.size());
        Iterator<ChargingPointId> chargingPointIdIterator = chargingPointIds.iterator();
        Iterator<Bicycle> bicycleIterator = bicycles.iterator();
        while (chargingPointIdIterator.hasNext() && bicycleIterator.hasNext()) {
            returnBicycle(chargingPointIdIterator.next(), bicycleIterator.next());
        }
    }

    private ChargingPoint findChargingPoint(ChargingPointId chargingPointId) {
        ChargingPoint chargingPoint = chargingPoints.get(chargingPointId);
        if (chargingPoint == null) {
            throw new StationSlotNotFoundException();
        }
        return chargingPoint;
    }

    private void verifyNotInMaintenance() {
        if (isInMaintenance()) {
            throw new StationInMaintenanceException();
        }
    }

}

