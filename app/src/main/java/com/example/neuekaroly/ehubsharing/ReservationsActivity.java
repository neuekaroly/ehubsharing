package com.example.neuekaroly.ehubsharing;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import adapter.ReservationAdapter;
import database.ChargerPointDao;
import database.CustomerDao;
import database.DaoMaster;
import database.DaoSession;
import database.Reservation;
import helpers.ChargerReservation;

public class ReservationsActivity extends AppCompatActivity {
    private List<ChargerReservation> chargerReservationList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ReservationAdapter mAdapter;
    DaoSession mDaoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        mDaoSession = new DaoMaster(new DaoMaster.DevOpenHelper(this, "charger.db").getWritableDb()).newSession();

        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        bottomBar.selectTabAtPosition(1);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_favourites) {
                    Intent intent = new Intent(ReservationsActivity.this, FavouritesActivity.class);
                    intent.putExtra("CUSTOMER_ID", 1L);
                    startActivity(intent);
                } else if (tabId == R.id.tab_map) {
                    Intent intent = new Intent(ReservationsActivity.this, MapActivity.class);
                    intent.putExtra("CUSTOMER_ID", 1L);
                    startActivity(intent);
                }
            }
        });



        recyclerView = (RecyclerView) findViewById(R.id.activity_reservations_recycler_view);

        mAdapter = new ReservationAdapter(chargerReservationList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareReservationsData();

    }

    private void prepareReservationsData() {
        CustomerDao customerDao = mDaoSession.getCustomerDao();

        ChargerPointDao chargerPointDao = mDaoSession.getChargerPointDao();

        List<Reservation> reservations = customerDao.load(1L).getReservations();

        for (int i = 0; i < reservations.size(); i++) {
            ChargerReservation chargerReservation = new ChargerReservation(reservations.get(i), chargerPointDao.load(reservations.get(i).getChargerPointId()));
            chargerReservationList.add(chargerReservation);
        }

        mAdapter.notifyDataSetChanged();
    }
}
