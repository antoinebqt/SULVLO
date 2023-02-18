package ca.ulaval.glo4003.station.domain.technician;

import ca.ulaval.glo4003.station.domain.exceptions.NotEnoughBicyclesInTransferException;
import ca.ulaval.glo4003.trip.domain.bicycle.Bicycle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BicycleTransfer {
    private final LinkedList<Bicycle> bicycles;

    public BicycleTransfer(List<Bicycle> bicycles) {
        this.bicycles = new LinkedList<>(bicycles);
    }

    public BicycleTransfer() {
        this.bicycles = new LinkedList<>();
    }

    public boolean contains(Bicycle bicycle) {
        return bicycles.contains(bicycle);
    }

    public void addBicycle(Bicycle bicycle) {
        bicycles.add(bicycle);
    }

    public Bicycle removeBicycle() {
        if (bicycles.isEmpty()) {
            throw new NotEnoughBicyclesInTransferException();
        }
        return bicycles.removeLast();
    }

    public List<Bicycle> removeBicycles(int numberOfBicyclesToRemove) {
        List<Bicycle> removedBicycles = new ArrayList<>();
        for (int i = 0; i < numberOfBicyclesToRemove; i++) {
            Bicycle removedBicycle = removeBicycle();
            removedBicycles.add(removedBicycle);
        }
        return removedBicycles;
    }

    public LinkedList<Bicycle> getBicycles() {
        return bicycles;
    }
}
