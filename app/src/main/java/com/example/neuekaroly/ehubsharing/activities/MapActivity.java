package com.example.neuekaroly.ehubsharing.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.neuekaroly.ehubsharing.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.List;

import com.example.neuekaroly.ehubsharing.database.ChargerPoint;
import com.example.neuekaroly.ehubsharing.database.ChargerPointDao;

/**
 * This Activity shows the map with the charger points
 */
public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Long mCameraPosition = -1L;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        JodaTimeAndroid.init(this);

        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        bottomBar.selectTabAtPosition(0);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_favourites) {
                    Intent intent = new Intent(MapActivity.this, FavouritesActivity.class);
                    startActivity(intent);
                } else if (tabId == R.id.tab_reservations) {
                    //updateReservationsByTime();
                    Intent intent = new Intent(MapActivity.this, ReservationsActivity.class);
                    startActivity(intent);
                } else if (tabId == R.id.tab_search) {
                    Intent intent = new Intent(MapActivity.this, SearchActivity.class);
                    startActivity(intent);
                }
            }
        });

        String extra = getIntent().getStringExtra("CHARGER_ID");
        if(extra != null) {
            Log.d("TEST", getIntent().getStringExtra("CHARGER_ID"));
            mCameraPosition = Long.parseLong(extra);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        bottomBar.selectTabAtPosition(0);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(MapActivity.this, ChargerActivity.class);
                intent.putExtra("CHARGER_ID", marker.getId().substring(1, marker.getId().length()));
                startActivity(intent);
            }
        });

        mMap.getUiSettings().setZoomControlsEnabled(true);

        addMarkersToMap();

        LatLng southMap = new LatLng(47.024250, 15.834115);
        LatLng westMap = new LatLng(47.908052, 23.211434);
        LatLngBounds mapBounds = new LatLngBounds(southMap, westMap);

        if(mCameraPosition == -1L) {
            mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    LatLng southMap = new LatLng(47.024250, 15.834115);
                    LatLng westMap = new LatLng(47.908052, 23.211434);
                    LatLngBounds mapBounds = new LatLngBounds(southMap, westMap);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mapBounds, 40));
                }
            });
        }
    }

    private void addMarkersToMap() {

            ChargerPointDao chargerPointDao = SplashActivity.mDaoSession.getChargerPointDao();

            List<ChargerPoint> chargerPoints = chargerPointDao.loadAll();

            BitmapDescriptor bitmapDescriptor = bitmapDescriptorFromVector(this, R.drawable.ic_map_marker);

            for (int i = 0; i < chargerPoints.size(); i++) {
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(new LatLng(chargerPoints.get(i).getLatitude(),chargerPoints.get(i).getLongitude()))
                        .title(chargerPoints.get(i).getName())
                        .snippet(chargerPoints.get(i).getOpeningHours())
                        .flat(true)
                        .icon(bitmapDescriptor);
                mMap.addMarker(markerOptions);
                if(mCameraPosition != -1L) {
                    if(chargerPoints.get(i).getId() == mCameraPosition) {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markerOptions.getPosition(), 14));
                    }
                }
        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}
