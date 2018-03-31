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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
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

        Reader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("chargers.txt"), "UTF-8"));
        } catch(IOException e) {
            Log.d("Test", e.toString());
        }

        LinkedList<ChargerPoint> list = new LinkedList<>(
                (ArrayList<ChargerPoint>) new Gson().fromJson(
                        reader, new TypeToken<List<ChargerPoint>>(){}.getType()
                )
        );

        /*for (int i = 0; i < list.size(); i++) {
            Log.d("TEST", list.get(i).getAdress());
        }*/

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

        Bitmap icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.black_charger);

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(47.50569970,19.06325010))
                .title("Oktogon 2.")
                .snippet("06 1/238 1888")
                .icon(BitmapDescriptorFactory.fromBitmap(icon)));
    }
}
