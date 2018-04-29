package com.example.neuekaroly.ehubsharing;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.List;

import com.example.neuekaroly.ehubsharing.database.ChargerPoint;

/**
 * Created by neue.karoly on 2018.03.31..
 * CSAK EGY UTIL OSZTÁLY A JSON-BE VALÓ KIÍÍRÁSHOZ, VÉGSŐ BUID-BE NEM KERÜL BELE, TÖRLENDŐ LESZ
 */

public class MapPointsWriter extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public  MapPointsWriter() {
        List<ChargerPoint> chargerPoints = new LinkedList<ChargerPoint>();

        ChargerPoint chargerPoint = new ChargerPoint();
        chargerPoint.setId(1L);
        chargerPoint.setAdress("2335 Taksony Széchenyi István út 88/D");
        chargerPoint.setConnectorTypes("Type2,Type2");
        chargerPoint.setCost(0);
        chargerPoint.setLatitude(47.322244);
        chargerPoint.setLongitude(19.075226);
        chargerPoint.setName("Green Taxi telephely");
        chargerPoint.setOpeningHours("Minden nap: 9-19");

        chargerPoints.add(chargerPoint);

        ChargerPoint chargerPoint2 = new ChargerPoint();

        chargerPoint2.setId(2L);
        chargerPoint2.setAdress("2330 Dunaharszti Fő út 8-10.");
        chargerPoint2.setConnectorTypes("Type2,Type2");
        chargerPoint2.setCost(0);
        chargerPoint2.setLatitude(47.357565);
        chargerPoint2.setLongitude(19.085078);
        chargerPoint2.setName("Fő út 8-10.");
        chargerPoint2.setOpeningHours("24/7");

        chargerPoints.add(chargerPoint2);

        ChargerPoint chargerPoint3 = new ChargerPoint();

        chargerPoint3.setId(3L);
        chargerPoint3.setAdress("2314 Halásztelek Somogyi Béla utca 53.");
        chargerPoint3.setConnectorTypes("Type2,Type2");
        chargerPoint3.setCost(0);
        chargerPoint3.setLatitude(47.362565);
        chargerPoint3.setLongitude(18.982032);
        chargerPoint3.setName("Halásztelek Általános Iskola");
        chargerPoint3.setOpeningHours("24/7");

        chargerPoints.add(chargerPoint3);

        ChargerPoint chargerPoint4 = new ChargerPoint();

        chargerPoint4.setId(4L);
        chargerPoint4.setAdress("1211 Budapest Kossuth Lajos utca 69.");
        chargerPoint4.setConnectorTypes("Type2,Type2");
        chargerPoint4.setCost(0);
        chargerPoint4.setLatitude(47.428736);
        chargerPoint4.setLongitude(19.070034);
        chargerPoint4.setName("Penny Market - Kossut Lajos utca");
        chargerPoint4.setOpeningHours("24/7");

        chargerPoints.add(chargerPoint4);

        Gson gson = new Gson();
        String str = gson.toJson(chargerPoints);

        Log.d("TEST", str);
    }
}
