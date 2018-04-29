package com.example.neuekaroly.ehubsharing.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neuekaroly.ehubsharing.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import com.example.neuekaroly.ehubsharing.adapters.SearchChargerAdapter;
import com.example.neuekaroly.ehubsharing.helpers.RecyclerTouchListener;
import com.example.neuekaroly.ehubsharing.database.ChargerPoint;
import com.example.neuekaroly.ehubsharing.database.DaoMaster;
import com.example.neuekaroly.ehubsharing.database.DaoSession;

public class SearchActivity extends AppCompatActivity {
    SearchView mSearchView;

    RecyclerView mRecyclerView;

    List<ChargerPoint> chargerPointList = new ArrayList<>();

    SearchChargerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        DaoSession mDaoSession = new DaoMaster(new DaoMaster.DevOpenHelper(this, "charger.db").getWritableDb()).newSession();

        chargerPointList = mDaoSession.getChargerPointDao().loadAll();

        initBottomBar();

        mRecyclerView = (RecyclerView) findViewById(R.id.search_actitivity_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new SearchChargerAdapter(chargerPointList, SearchActivity.this);
        mRecyclerView.setAdapter(mAdapter);

        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getApplicationContext(), chargerPointList.get(position).getId() + " is selected!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SearchActivity.this, MapActivity.class);
                intent.putExtra("CHARGER_ID", Long.toString(chargerPointList.get(position).getId()));
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        mRecyclerView.setAdapter(mAdapter);
    }

    private void initBottomBar() {
        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        bottomBar.selectTabAtPosition(3);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_map) {
                    Intent intent = new Intent(SearchActivity.this, MapActivity.class);
                    startActivity(intent);
                } else if (tabId == R.id.tab_reservations) {
                    Intent intent = new Intent(SearchActivity.this, ReservationsActivity.class);
                    startActivity(intent);
                } else if (tabId == R.id.tab_favourites) {
                    Intent intent = new Intent(SearchActivity.this, FavouritesActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_file, menu);
        final MenuItem myActionMenuItem = menu.findItem(R.id.search);
        mSearchView = (SearchView) myActionMenuItem.getActionView();
        changeSearchViewTextColor(mSearchView);
        ((EditText) mSearchView.findViewById(
                android.support.v7.appcompat.R.id.search_src_text)).
                setHintTextColor(getResources().getColor(R.color.mdtp_white));

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!mSearchView.isIconified()) {
                    mSearchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<ChargerPoint> filtermodelist = filter(chargerPointList,newText);
                mAdapter.setfilter(filtermodelist);
                return true;
            }
        });

        return true;
    }

    private List<ChargerPoint> filter(List<ChargerPoint> pl, String query) {
        query=query.toLowerCase();
        final List<ChargerPoint> filteredModeList=new ArrayList<>();
        for (ChargerPoint model:pl) {
            final String name = model.getName().toLowerCase();

            final String adress = model.getAdress().toLowerCase();

            if (name.contains(query) || adress.contains(query))
            {
                filteredModeList.add(model);
            }
        }

        return filteredModeList;
    }

    private void changeSearchViewTextColor(View view) {
        if (view != null) {
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(Color.WHITE);
            return;
        } else if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                changeSearchViewTextColor(viewGroup.getChildAt(i));
            }
        }
    }
}
}
