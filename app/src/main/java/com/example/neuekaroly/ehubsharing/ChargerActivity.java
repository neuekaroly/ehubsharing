package com.example.neuekaroly.ehubsharing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import database.ChargerPoint;
import database.ChargerPointDao;
import database.DaoMaster;
import database.DaoSession;

public class ChargerActivity extends AppCompatActivity {

    DaoSession mDaoSession;

    ChargerPoint mCharger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charger);

        mDaoSession = new DaoMaster(new DaoMaster.DevOpenHelper(this, "charger.db").getWritableDb()).newSession();
        ChargerPointDao chargerPointDao = mDaoSession.getChargerPointDao();

        mCharger = chargerPointDao.load(Long.parseLong(getIntent().getStringExtra("CHARGER_ID")) + 1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(mCharger.getName());
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initActivity();
    }

    private void initActivity() {
        TextView textView = (TextView) findViewById(R.id.activity_charger_adress_text_view);
        textView.setText("Adress: " + mCharger.getAdress());

        TextView textView2 = (TextView) findViewById(R.id.activity_charger_openinghours_text_view);
        textView2.setText("Adress: " + mCharger.getOpeningHours());

        textView = (TextView) findViewById(R.id.activity_charger_cost_text_view);
        textView.setText("Adress: " + mCharger.getCost());

        textView = (TextView) findViewById(R.id.activity_charger_connectortypes_text_view);
        textView.setText("Adress: " + mCharger.getConnectorTypes());
    }
}
