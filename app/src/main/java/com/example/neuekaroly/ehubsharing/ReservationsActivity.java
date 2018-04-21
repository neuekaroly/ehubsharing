package com.example.neuekaroly.ehubsharing;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import adapter.ReservationAdapter;
import customitems.CustomDividerItemDecoration;
import customitems.RecyclerTouchListener;
import database.ChargerPointDao;
import database.CustomerDao;
import database.DaoMaster;
import database.DaoSession;
import database.Reservation;
import database.ReservationDao;
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


        initBottomBar();

        initRecyclerView();

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

    private void initBottomBar() {
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
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.activity_reservations_recycler_view);

        mAdapter = new ReservationAdapter(chargerReservationList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new CustomDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                Toast.makeText(ReservationsActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                final int position = viewHolder.getAdapterPosition();

                ReservationDao reservationDao = mDaoSession.getReservationDao();

                reservationDao.delete(chargerReservationList.get(position).getReservation());

                chargerReservationList.remove(position);
                mAdapter.notifyDataSetChanged();
            }


        };

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "Selected!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ReservationsActivity.this, MapActivity.class);
                intent.putExtra("CHARGER_ID", Long.toString(chargerReservationList.get(position).getChargerPoint().getId()));
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(mAdapter);
    }
}
