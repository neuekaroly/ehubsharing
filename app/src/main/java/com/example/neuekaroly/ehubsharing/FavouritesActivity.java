package com.example.neuekaroly.ehubsharing;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import adapter.FavouriteAdapter;
import customitems.CustomDividerItemDecoration;
import database.ChargerPoint;
import database.ChargerPointDao;
import database.CustomerDao;
import database.DaoMaster;
import database.DaoSession;
import database.JoinCustomersWithChargerPoints;
import database.JoinCustomersWithChargerPointsDao;

public class FavouritesActivity extends AppCompatActivity {
    private List<ChargerPoint> chargerPointList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FavouriteAdapter mAdapter;
    DaoSession mDaoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        mDaoSession = new DaoMaster(new DaoMaster.DevOpenHelper(this, "charger.db").getWritableDb()).newSession();

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
                    Intent intent = new Intent(FavouritesActivity.this, ReservationsActivity.class);
                    intent.putExtra("CUSTOMER_ID", 1L);
                    startActivity(intent);
                }
            }
        });

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
                Toast.makeText(FavouritesActivity.this, "on Swiped ", Toast.LENGTH_SHORT).show();
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

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(mAdapter);

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
}
