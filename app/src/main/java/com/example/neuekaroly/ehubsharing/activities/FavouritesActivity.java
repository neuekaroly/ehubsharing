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

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.ArrayList;
import java.util.List;

import com.example.neuekaroly.ehubsharing.adapters.FavouriteAdapter;
import com.example.neuekaroly.ehubsharing.customitems.CustomDividerItemDecoration;
import com.example.neuekaroly.ehubsharing.helpers.RecyclerTouchListener;
import com.example.neuekaroly.ehubsharing.database.ChargerPoint;
import com.example.neuekaroly.ehubsharing.database.CustomerDao;
import com.example.neuekaroly.ehubsharing.database.DaoMaster;
import com.example.neuekaroly.ehubsharing.database.DaoSession;
import com.example.neuekaroly.ehubsharing.database.JoinCustomersWithChargerPoints;
import com.example.neuekaroly.ehubsharing.database.JoinCustomersWithChargerPointsDao;

/**
 * This Activity shows favourites chargers for the user
 * The user can delete from the favourites
 */
public class FavouritesActivity extends AppCompatActivity {

    private List<ChargerPoint> mChargerPointList = new ArrayList<>();

    private RecyclerView mRecyclerView;

    private FavouriteAdapter mAdapter;

    DaoSession mDaoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        JodaTimeAndroid.init(this);

        mDaoSession = new DaoMaster(new DaoMaster.DevOpenHelper(this, "charger.db").getWritableDb()).newSession();

        initBottomBar();

        initRecyclerView();

        prepareFavouritesData();
    }

    @Override
    protected void onResume() {
        super.onResume();

        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        bottomBar.selectTabAtPosition(2);
    }

    private void prepareFavouritesData() {
        CustomerDao customerDao = mDaoSession.getCustomerDao();

        List<ChargerPoint> chargerPointsWitThisCustomer = customerDao.load(SplashActivity.customerId).getChargerPointsWitThisCustomer();

        for (int i = 0; i < chargerPointsWitThisCustomer.size(); i++) {
            mChargerPointList.add(chargerPointsWitThisCustomer.get(i));
        }

        mAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_favourites_recycler_view);

        mAdapter = new FavouriteAdapter(mChargerPointList);
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
                Toast.makeText(FavouritesActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                final int position = viewHolder.getAdapterPosition();

                CustomerDao customerDao = mDaoSession.getCustomerDao();

                List<ChargerPoint> chargerPointsWitThisCustomer = customerDao.load(SplashActivity.customerId).getChargerPointsWitThisCustomer();

                Long deleteChargerId = chargerPointsWitThisCustomer.get(position).getId();

                JoinCustomersWithChargerPointsDao joinCustomersWithChargerPointsDao = mDaoSession.getJoinCustomersWithChargerPointsDao();

                List<JoinCustomersWithChargerPoints> joinCustomersWithChargerPoints = joinCustomersWithChargerPointsDao.loadAll();

                for (int i = 0; i < joinCustomersWithChargerPoints.size(); i++) {
                    if(joinCustomersWithChargerPoints.get(i).getChargerPointId() == deleteChargerId && joinCustomersWithChargerPoints.get(i).getCustomerId() == SplashActivity.customerId) {
                        JoinCustomersWithChargerPoints deleteFavourite = joinCustomersWithChargerPoints.get(i);
                        joinCustomersWithChargerPointsDao.delete(deleteFavourite);
                        break;
                    }
                }

                mChargerPointList.remove(position);
                mAdapter.notifyDataSetChanged();
            }

        };

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(FavouritesActivity.this, MapActivity.class);
                intent.putExtra("CHARGER_ID", Long.toString(mChargerPointList.get(position).getId()));
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

    private void initBottomBar() {
        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        bottomBar.selectTabAtPosition(2);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_map) {
                    Intent intent = new Intent(FavouritesActivity.this, MapActivity.class);
                    startActivity(intent);
                } else if (tabId == R.id.tab_reservations) {
                    //updateReservationsByTime();
                    Intent intent = new Intent(FavouritesActivity.this, ReservationsActivity.class);
                    startActivity(intent);
                } else if (tabId == R.id.tab_search) {
                    Intent intent = new Intent(FavouritesActivity.this, SearchActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
}
