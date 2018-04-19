package com.example.neuekaroly.ehubsharing;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import database.ChargerPoint;
import database.ChargerPointDao;
import database.Customer;
import database.CustomerDao;
import database.DaoMaster;
import database.DaoSession;
import database.JoinCustomersWithChargerPoints;
import database.JoinCustomersWithChargerPointsDao;
import database.Reservation;
import database.ReservationDao;

public class ChargerActivity extends AppCompatActivity {

    DaoSession mDaoSession;

    ChargerPoint mCharger;

    Customer mCustomer;

    Long chargerPointId;

    DateTime startDateTime = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charger);

        mDaoSession = new DaoMaster(new DaoMaster.DevOpenHelper(this, "charger.db").getWritableDb()).newSession();
        ChargerPointDao chargerPointDao = mDaoSession.getChargerPointDao();

        chargerPointId = Long.parseLong(getIntent().getStringExtra("CHARGER_ID")) + 1;

        mCharger = chargerPointDao.load(chargerPointId);

        for (int i = 0; i <  mCharger.getReservations().size(); i++) {
            Log.d("TEST", new DateTime(mCharger.getReservations().get(i).getStartDate()).toString());
            Log.d("TEST", new DateTime(mCharger.getReservations().get(i).getFinishDate()).toString());
        }

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

        initFavouriteButton();

        initBookingTimeButton();

        initBookingButton();
    }

    private void initBookingButton() {
        Button bookingButton = findViewById(R.id.activity_charger_booking_button);
        bookingButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (startDateTime != null) {
                    Spinner spinner = findViewById(R.id.activity_charger_booking_spinner);

                    DateTime endDateTime;

                    if (spinner.getSelectedItemId() == 0) {
                        endDateTime = startDateTime.plusMinutes(30);

                    } else {
                        endDateTime = startDateTime.plusMinutes(60);
                    }

                    Reservation reservation = new Reservation();
                    reservation.setCustomerId(1);
                    reservation.setStartDate(startDateTime.toDate());
                    reservation.setFinishDate(endDateTime.toDate());
                    reservation.setChargerPointId(chargerPointId);

                    mDaoSession.insert(reservation);

                    List<Reservation> chargers = mCharger.getReservations();

                    chargers.add(reservation);

                    Log.d("TEST", "ADD NEW RESERVATION");

                } else {
                    Toast.makeText(getApplicationContext(),"Please select the start time", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initFavouriteButton() {
        final FloatingActionButton addFavourite = findViewById(R.id.activity_charger_add_favourite_floating_button);
        if(!mCustomer.getChargerPointsWitThisCustomer().contains(mCharger)){
            addFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JoinCustomersWithChargerPointsDao joinCustomersWithChargerPointsDao = mDaoSession.getJoinCustomersWithChargerPointsDao();

                    JoinCustomersWithChargerPoints joinCustomersWithChargerPoints = new JoinCustomersWithChargerPoints();
                    joinCustomersWithChargerPoints.setChargerPointId(mCharger.getId());
                    joinCustomersWithChargerPoints.setCustomerId(1L);

                    joinCustomersWithChargerPointsDao.insert(joinCustomersWithChargerPoints);

                    Log.d("TEST", "Successfully added to the favourites");

                    addFavourite.hide();
                }
            });
        } else {
            addFavourite.hide();
        }
    }

    private void initBookingTimeButton() {
        Button bookingTimeButton = findViewById(R.id.activity_charger_booking_time_button);
        bookingTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(
                        new com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                                Log.d("TEST", "TIME IS CHANGED");

                                startDateTime = new DateTime(Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH,hourOfDay,minute);

                                Date date = startDateTime.toDate();

                            }
                        }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE),true);
                Timepoint timePoint = new Timepoint(23,0,0);
                tpd.setMinTime(now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND));
                tpd.setMaxTime(timePoint);
                tpd.setTitle("Select time");
                tpd.show(getFragmentManager(), "Select time");
            }
        });
    }
}
