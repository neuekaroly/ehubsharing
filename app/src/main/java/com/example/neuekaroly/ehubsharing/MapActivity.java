package com.example.neuekaroly.ehubsharing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.LinkedList;
import java.util.List;

import Entity.ChargerPoint;
import Entity.ChargerPointDao;
import Entity.DaoMaster;
import Entity.DaoSession;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private DaoSession mDaoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDaoSession = new DaoMaster(new DaoMaster.DevOpenHelper(this, "charger.db").getWritableDb()).newSession();
        ChargerPointDao chargerPointDao = mDaoSession.getChargerPointDao();
        ChargerPoint chargerPoint = new ChargerPoint();
        chargerPoint.setAdress("Taksony");
        chargerPoint.setConnectorTypes("ACC");
        chargerPoint.setCost(0);
        chargerPoint.setLatitude(3);
        chargerPoint.setLongitude(4);
        chargerPoint.setName("Taks");
        chargerPoint.setOpeningHours("27/7");
        chargerPointDao.insert(chargerPoint);

        List<ChargerPoint> chargerPoints = mDaoSession.getChargerPointDao().loadAll();

        for (int i = 0; i < chargerPoints.size(); i++) {
            Log.d("TEST", chargerPoints.get(i).getAdress());
        }

        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);

        addMarkersToMap();

        LatLng southMap = new LatLng(47.024250, 15.834115);
        LatLng westMap = new LatLng(47.908052, 23.211434);

        LatLngBounds mapBounds = new LatLngBounds(southMap, westMap);

        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mapBounds, 40));
    }

    private void addMarkersToMap() {

        Bitmap icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.black_charger);

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(47.50569970,19.06325010))
                .title("Oktogon 2.")
                .snippet("06 1/238 1888")
                .icon(BitmapDescriptorFactory.fromBitmap(icon)));
    }
}
