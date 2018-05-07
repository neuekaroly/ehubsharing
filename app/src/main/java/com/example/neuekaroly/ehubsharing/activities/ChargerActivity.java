package com.example.neuekaroly.ehubsharing.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neuekaroly.ehubsharing.R;
import com.example.neuekaroly.ehubsharing.database.ChargerPoint;
import com.example.neuekaroly.ehubsharing.database.ChargerPointDao;
import com.example.neuekaroly.ehubsharing.database.Customer;
import com.example.neuekaroly.ehubsharing.database.CustomerDao;
import com.example.neuekaroly.ehubsharing.database.DaoMaster;
import com.example.neuekaroly.ehubsharing.database.DaoSession;
import com.example.neuekaroly.ehubsharing.database.JoinCustomersWithChargerPoints;
import com.example.neuekaroly.ehubsharing.database.JoinCustomersWithChargerPointsDao;
import com.example.neuekaroly.ehubsharing.database.Reservation;
import com.example.neuekaroly.ehubsharing.util.StringUtils;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.List;


/**
 * This Activity shows the information about an electric charger
 */
public class ChargerActivity extends AppCompatActivity {

    DaoSession mDaoSession;

    ChargerPoint mCharger;

    Customer mCustomer;

    Long mChargerPointId;

    DateTime mStartDateTime = null;

    List<Reservation> mReservations;

    private Handler mHandler;

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charger);

        initActivity();

        mTextView = (TextView) findViewById(R.id.activity_charger_availability_text_view);

        mHandler = new Handler();

        mHandler.postDelayed(m_Runnable, 1000);
    }

    private final Runnable m_Runnable = new Runnable() {
        public void run() {

            changeTextView();

            mHandler.postDelayed(m_Runnable, 5000);
        }

    };

    public void changeTextView() {
        for (int i = 0; i < mReservations.size(); i++) {
            if(new DateTime(mReservations.get(i).getFinishDate()) == DateTime.now() || new DateTime(mReservations.get(i).getStartDate()) == DateTime.now()
                    || (new DateTime(mReservations.get(i).getFinishDate()).isAfterNow() && new DateTime(mReservations.get(i).getStartDate()).isBeforeNow())) {
                mTextView.setText("IT'S NOT FREE NOW");
                mTextView.setBackgroundColor(Color.RED);
                break;
            } else {
                mTextView.setText("IT'S FREE NOW");
                mTextView.setBackgroundColor(Color.GREEN);
            }
        }

        if(mReservations.size() == 0) {
            mTextView.setText("IT'S FREE NOW");
            mTextView.setBackgroundColor(Color.GREEN);
        }
    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
            mHandler.removeCallbacks(m_Runnable);
            return;
        }

    private void initActivity() {
        initDataBase();

        initToolbar();

        TextView textView = (TextView) findViewById(R.id.activity_charger_adress_text_view);
        textView.setText(getString(R.string.charger_activity_adress_text_view_text) + mCharger.getAdress());

        TextView textView2 = (TextView) findViewById(R.id.activity_charger_opening_hours_text_view);
        textView2.setText(getString(R.string.charger_activity_opening_hours_text_view_text) + mCharger.getOpeningHours());

        textView = (TextView) findViewById(R.id.activity_charger_cost_text_view);
        textView.setText(getString(R.string.charger_activity_cost_text_view_text) + StringUtils.chargerCostStringTransformer(mCharger.getCost()));

        textView = (TextView) findViewById(R.id.activity_charger_connector_types_text_view);
        textView.setText(getString(R.string.charger_activity_connector_types_text_view_text) + StringUtils.connectorTypesStringBuilder(mCharger.getConnectorTypes()));

        initFavouriteButton();

        initBookingTimeButton();

        initBookingButton();
    }

    private void initDataBase() {
        mDaoSession = new DaoMaster(new DaoMaster.DevOpenHelper(this, "charger.db").getWritableDb()).newSession();

        ChargerPointDao chargerPointDao = mDaoSession.getChargerPointDao();

        mChargerPointId = Long.parseLong(getIntent().getStringExtra("CHARGER_ID")) + 1;

        mCharger = chargerPointDao.load(mChargerPointId);

        mReservations = mCharger.getReservations();

        CustomerDao customerDao = mDaoSession.getCustomerDao();

        mCustomer = customerDao.load(SplashActivity.customerId);
    }

    private void initToolbar() {
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
    }

    private void initBookingButton() {
        Button bookingButton = findViewById(R.id.activity_charger_booking_button);
        bookingButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mStartDateTime != null) {
                    Spinner spinner = findViewById(R.id.activity_charger_booking_spinner);

                    DateTime endDateTime;

                    if (spinner.getSelectedItemId() == 0) {
                        endDateTime = mStartDateTime.plusMinutes(30);

                    } else {
                        endDateTime = mStartDateTime.plusMinutes(60);
                    }

                    if(checkReservationTimeIsValid(mStartDateTime, endDateTime)) {

                        Reservation reservation = new Reservation();
                        reservation.setCustomerId(1);
                        reservation.setStartDate(mStartDateTime.toDate());
                        reservation.setFinishDate(endDateTime.toDate());
                        reservation.setChargerPointId(mChargerPointId);

                        mReservations.add(reservation);

                        mDaoSession.insert(reservation);

                        mReservations.add(reservation);

                        Toast.makeText(getApplicationContext(), getString(R.string.sucessfull_reservation), Toast.LENGTH_LONG).show();

                        changeTextView();
                    } else {
                        Toast.makeText(getApplicationContext(),getString(R.string.not_valid_the_selected_starttime_string), Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.not_selected_starttime_string), Toast.LENGTH_LONG).show();
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
                final Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(
                        new com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

                                mStartDateTime = new DateTime(now.get(Calendar.YEAR),now.get(Calendar.MONTH) + 1 ,now.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);

                            }
                        }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE),true);

                Timepoint timePoint = new Timepoint(23,0,0);
                tpd.setMinTime(now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND));
                tpd.setMaxTime(timePoint);
                tpd.setTitle(getString(R.string.charger_activity_time_picker_dialog_title));
                tpd.show(getFragmentManager(), getString(R.string.charger_activity_time_picker_dialog_title));
            }
        });
    }

    private boolean checkReservationTimeIsValid(DateTime newStartTime, DateTime newFinishTime) {

        for (int i = 0; i < mReservations.size(); i++) {
            if((new DateTime(mReservations.get(i).getStartDate()).isBefore(newStartTime) && newStartTime.isBefore(new DateTime(mReservations.get(i).getFinishDate())))
                    || (new DateTime(mReservations.get(i).getStartDate()).isBefore(newFinishTime) && newFinishTime.isBefore(new DateTime(mReservations.get(i).getFinishDate())))
                    || mStartDateTime.isEqual(new DateTime(mReservations.get(i).getStartDate()))) {
                return false;
            }
        }
        return true;
    }
}