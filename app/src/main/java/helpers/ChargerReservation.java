package helpers;


import database.ChargerPoint;
import database.Reservation;

public class ChargerReservation {
    private ChargerPoint chargerPoint;
    private Reservation reservation;

    public ChargerReservation(Reservation reservation, ChargerPoint chargerPoint) {
        this.chargerPoint = chargerPoint;
        this.reservation = reservation;
    }

    public ChargerPoint getChargerPoint() {
        return chargerPoint;
    }

    public Reservation getReservation() {
        return reservation;
    }
}
