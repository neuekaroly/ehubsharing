package com.example.neuekaroly.ehubsharing.activities;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.example.neuekaroly.ehubsharing.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import com.example.neuekaroly.ehubsharing.adapters.ReservationAdapter;
import com.example.neuekaroly.ehubsharing.customitems.CustomDividerItemDecoration;
import com.example.neuekaroly.ehubsharing.helpers.RecyclerTouchListener;
import com.example.neuekaroly.ehubsharing.database.ChargerPointDao;
import com.example.neuekaroly.ehubsharing.database.CustomerDao;
import com.example.neuekaroly.ehubsharing.database.DaoMaster;
import com.example.neuekaroly.ehubsharing.database.DaoSession;
import com.example.neuekaroly.ehubsharing.database.Reservation;
import com.example.neuekaroly.ehubsharing.database.ReservationDao;
import com.example.neuekaroly.ehubsharing.helpers.ChargerReservation;

public class ReservationsActivity extends AppCompatActivity {
    private Long mCustomerId;

    private List<ChargerReservation> mChargerReservationList = new ArrayList<>();

    private RecyclerView mRecyclerView;

    private ReservationAdapter mAdapter;

    DaoSession mDaoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        mCustomerId = SplashActivity.customerId;

        mDaoSession = new DaoMaster(new DaoMaster.DevOpenHelper(this, "charger.db").getWritableDb()).newSession();

        initBottomBar();

        initRecyclerView();

        prepareReservationsData();

    }

    private void prepareReservationsData() {
        CustomerDao customerDao = mDaoSession.getCustomerDao();

        ChargerPointDao chargerPointDao = mDaoSession.getChargerPointDao();

        List<Reservation> reservations = customerDao.load(mCustomerId).getReservations();

        for (int i = 0; i < reservations.size(); i++) {
            ChargerReservation chargerReservation = new ChargerReservation(reservations.get(i), chargerPointDao.load(reservations.get(i).getChargerPointId()));
            mChargerReservationList.add(chargerReservation);
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
                    startActivity(intent);
                } else if (tabId == R.id.tab_map) {
                    Intent intent = new Intent(ReservationsActivity.this, MapActivity.class);
                    startActivity(intent);
                } else if (tabId == R.id.tab_search) {
                    Intent intent = new Intent(ReservationsActivity.this, SearchActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_reservations_recycler_view);

        mAdapter = new ReservationAdapter(mChargerReservationList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new CustomDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));

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

                reservationDao.delete(mChargerReservationList.get(position).getReservation());

                mChargerReservationList.remove(position);
                mAdapter.notifyDataSetChanged();
            }


        };

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "Selected!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ReservationsActivity.this, MapActivity.class);
                intent.putExtra("CHARGER_ID", Long.toString(mChargerReservationList.get(position).getChargerPoint().getId()));
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.setAdapter(mAdapter);
    }
}
