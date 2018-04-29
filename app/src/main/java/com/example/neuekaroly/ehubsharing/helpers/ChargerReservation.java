package com.example.neuekaroly.ehubsharing.helpers;


import com.example.neuekaroly.ehubsharing.database.ChargerPoint;
import com.example.neuekaroly.ehubsharing.database.Reservation;

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
