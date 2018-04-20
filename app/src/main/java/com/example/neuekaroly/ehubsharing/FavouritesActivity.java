package com.example.neuekaroly.ehubsharing;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class FavouritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

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
    }
}
