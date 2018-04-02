package com.example.neuekaroly.ehubsharing;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import database.ChargerPoint;
import database.ChargerPointDao;
import database.Customer;
import database.CustomerDao;
import database.DaoMaster;
import database.DaoSession;
import database.JoinCustomersWithChargerPoints;
import database.JoinCustomersWithChargerPointsDao;

public class ChargerActivity extends AppCompatActivity {

    DaoSession mDaoSession;

    ChargerPoint mCharger;

    Customer mCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charger);

        mDaoSession = new DaoMaster(new DaoMaster.DevOpenHelper(this, "charger.db").getWritableDb()).newSession();
        ChargerPointDao chargerPointDao = mDaoSession.getChargerPointDao();

        mCharger = chargerPointDao.load(Long.parseLong(getIntent().getStringExtra("CHARGER_ID")) + 1);

        CustomerDao customerDao = mDaoSession.getCustomerDao();

        mCustomer = customerDao.load(1L);

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
        textView2.setText("Opening hours: " + mCharger.getOpeningHours());

        textView = (TextView) findViewById(R.id.activity_charger_cost_text_view);
        textView.setText("Cost: " + mCharger.getCost());

        textView = (TextView) findViewById(R.id.activity_charger_connectortypes_text_view);
        textView.setText("Connector types: " + mCharger.getConnectorTypes());

        FloatingActionButton addFavourite = findViewById(R.id.activity_charger_add_favourite_floating_button);
        addFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCustomer.getChargerPointsWitThisCustomer().contains(mCharger)) {
                    Log.d("TEST", "It is already in the favourites");

                } else {
                    JoinCustomersWithChargerPointsDao joinCustomersWithChargerPointsDao = mDaoSession.getJoinCustomersWithChargerPointsDao();

                    JoinCustomersWithChargerPoints joinCustomersWithChargerPoints = new JoinCustomersWithChargerPoints();
                    joinCustomersWithChargerPoints.setChargerPointId(mCharger.getId());
                    joinCustomersWithChargerPoints.setCustomerId(1L);

                    joinCustomersWithChargerPointsDao.insert(joinCustomersWithChargerPoints);

                    Log.d("TEST", "Successfully added to the favourites");
                }
            }
        });

        Button bookingButton = findViewById(R.id.activity_charger_booking_button);

        bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                CustomTimePickerDialog mTimePicker;
                mTimePicker = new CustomTimePickerDialog(ChargerActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }
}
