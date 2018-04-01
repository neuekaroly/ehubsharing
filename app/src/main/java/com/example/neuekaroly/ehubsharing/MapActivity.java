package com.example.neuekaroly.ehubsharing;

import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
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

import database.ChargerPoint;
import database.ChargerPointDao;
import database.DaoMaster;
import database.DaoSession;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private DaoSession mDaoSession;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "charger.db", null){
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            }
        };

        mDaoSession = new DaoMaster(new DaoMaster.DevOpenHelper(this, "charger.db").getWritableDb()).newSession();
        ChargerPointDao chargerPointDao = mDaoSession.getChargerPointDao();

        for (int i = 0; i < chargerPointDao.loadAll().size(); i++) {
            Log.d("TEST", chargerPointDao.loadAll().get(i).getAdress());
        }

       // MapPointsWriter mapPointsWriter = new MapPointsWriter();

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

        ChargerPointDao chargerPointDao = mDaoSession.getChargerPointDao();

        List<ChargerPoint> chargerPoints = chargerPointDao.loadAll();

        Bitmap icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.black_charger);

        for (int i = 0; i < chargerPoints.size(); i++) {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(chargerPoints.get(i).getLatitude(),chargerPoints.get(i).getLongitude()))
                    .title(chargerPoints.get(i).getName())
                    .snippet(chargerPoints.get(i).getOpeningHours())
                    .icon(BitmapDescriptorFactory.fromBitmap(icon)));
        }

    }
}
