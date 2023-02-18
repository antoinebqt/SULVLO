package ca.ulaval.glo4003.station.domain;

import ca.ulaval.glo4003.station.domain.exceptions.InvalidStationException;

public enum StationLocation {
    VACHON("Vachon"),
    PEPS("Peps"),
    DESJARDINS("Desjardins"),
    VANDRY("Vandry"),
    MYRAND("Myrand"),
    PYRAMIDE("Pyramide"),
    CASAULT("Casault"),
    PLACE_STE_FOY("Place Ste-Foy");

    private final String value;

    StationLocation(String value) {
        this.value = value;
    }

    public static StationLocation fromLocation(String location) {
        for (StationLocation stationLocation : values()) {
            if (location.equalsIgnoreCase(stationLocation.value)) {
                return stationLocation;
            }
        }
        throw new InvalidStationException();
    }

    public String getValue() {
        return value;
    }
}
