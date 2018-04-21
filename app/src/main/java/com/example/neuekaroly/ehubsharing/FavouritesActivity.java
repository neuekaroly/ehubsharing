package com.example.neuekaroly.ehubsharing;

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

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import adapter.FavouriteAdapter;
import customitems.CustomDividerItemDecoration;
import customitems.RecyclerTouchListener;
import database.ChargerPoint;
import database.CustomerDao;
import database.DaoMaster;
import database.DaoSession;
import database.JoinCustomersWithChargerPoints;
import database.JoinCustomersWithChargerPointsDao;
import database.Reservation;
import database.ReservationDao;

public class FavouritesActivity extends AppCompatActivity {
    private List<ChargerPoint> chargerPointList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FavouriteAdapter mAdapter;
    DaoSession mDaoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        JodaTimeAndroid.init(this);

        mDaoSession = new DaoMaster(new DaoMaster.DevOpenHelper(this, "charger.db").getWritableDb()).newSession();

        initBottomBar();

        initRecyclerView();

        prepareFavouritesData();
    }

    private void prepareFavouritesData() {
        CustomerDao customerDao = mDaoSession.getCustomerDao();

        List<ChargerPoint> chargerPointsWitThisCustomer = customerDao.load(1L).getChargerPointsWitThisCustomer();

        for (int i = 0; i < chargerPointsWitThisCustomer.size(); i++) {
            chargerPointList.add(chargerPointsWitThisCustomer.get(i));
        }

        mAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.activity_favourites_recycler_view);

        mAdapter = new FavouriteAdapter(chargerPointList);
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
                Toast.makeText(FavouritesActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                final int position = viewHolder.getAdapterPosition();

                //TODO: REFACTOR THIS SPAGHETTI CODE

                CustomerDao customerDao = mDaoSession.getCustomerDao();

                List<ChargerPoint> chargerPointsWitThisCustomer = customerDao.load(1L).getChargerPointsWitThisCustomer();

                Long deleteChargerId = chargerPointsWitThisCustomer.get(position).getId();

                JoinCustomersWithChargerPointsDao joinCustomersWithChargerPointsDao = mDaoSession.getJoinCustomersWithChargerPointsDao();

                List<JoinCustomersWithChargerPoints> joinCustomersWithChargerPoints = joinCustomersWithChargerPointsDao.loadAll();

                for (int i = 0; i < joinCustomersWithChargerPoints.size(); i++) {
                    if(joinCustomersWithChargerPoints.get(i).getChargerPointId() == deleteChargerId && joinCustomersWithChargerPoints.get(i).getCustomerId() == 1L) {
                        JoinCustomersWithChargerPoints deleteFavourite = joinCustomersWithChargerPoints.get(i);
                        joinCustomersWithChargerPointsDao.delete(deleteFavourite);
                        break;
                    }
                }

                //TODO: END

                chargerPointList.remove(position);
                mAdapter.notifyDataSetChanged();
            }

        };

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getApplicationContext(), chargerPointList.get(position).getId() + " is selected!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FavouritesActivity.this, MapActivity.class);
                intent.putExtra("CHARGER_ID", Long.toString(chargerPointList.get(position).getId()));
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

    private void initBottomBar() {
        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        bottomBar.selectTabAtPosition(2);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_map) {
                    Intent intent = new Intent(FavouritesActivity.this, MapActivity.class);
                    intent.putExtra("CUSTOMER_ID", 1L);
                    startActivity(intent);
                } else if (tabId == R.id.tab_reservations) {
                    updateReservationsByTime();
                    Intent intent = new Intent(FavouritesActivity.this, ReservationsActivity.class);
                    intent.putExtra("CUSTOMER_ID", 1L);
                    startActivity(intent);
                }
            }
        });
    }

    private void updateReservationsByTime() {
        CustomerDao customerDao = mDaoSession.getCustomerDao();

        ReservationDao reservationDao = mDaoSession.getReservationDao();

        List<Reservation> reservations = customerDao.load(1L).getReservations();

        for (int i = 0; i < reservations.size(); i++) {
            if(new DateTime(reservations.get(i).getFinishDate()).isBeforeNow()) {
                reservationDao.delete(reservations.get(i));
            }
        }
    }
}
