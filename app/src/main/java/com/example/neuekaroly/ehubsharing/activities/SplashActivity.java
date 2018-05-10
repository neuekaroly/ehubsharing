package com.example.neuekaroly.ehubsharing.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.neuekaroly.ehubsharing.R;
import com.facebook.stetho.Stetho;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.example.neuekaroly.ehubsharing.database.ChargerPoint;
import com.example.neuekaroly.ehubsharing.database.ChargerPointDao;
import com.example.neuekaroly.ehubsharing.database.Customer;
import com.example.neuekaroly.ehubsharing.database.CustomerDao;
import com.example.neuekaroly.ehubsharing.database.DaoMaster;
import com.example.neuekaroly.ehubsharing.database.DaoSession;

/**
 * This Activity create the welcome screen
 */
public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    private static final int ERROR_DIALOG_REQUEST = 9001;

    public static DaoSession mDaoSession;

    SharedPreferences mSharedPreferences = null;

    public static Long customerId = 1L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPreferences = getSharedPreferences("com.example.neuekaroly.ehubsharing", MODE_PRIVATE);

        mDaoSession = new DaoMaster(new DaoMaster.DevOpenHelper(this, "charger.db").getWritableDb()).newSession();

        Stetho.initializeWithDefaults(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setTheme(R.style.NoActionBar);
        setContentView(R.layout.activity_splash);

        if(isServicesOk()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent homeIntent = new Intent( SplashActivity.this, MapActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mSharedPreferences.getBoolean("firstrun", true)) {
            Log.d("TEST", getString(R.string.splash_activity_first_run_msg));

            fillUpDatabase();

            mSharedPreferences.edit().putBoolean("firstrun", false).commit();
        }
    }

    /**
     * This method load the chargerpoints from the database
     */
    private void fillUpDatabase() {
        Reader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("chargers.txt"), "UTF-8"));
        } catch(IOException e) {
            Log.d("TEST", e.toString());
        }

        LinkedList<ChargerPoint> list = new LinkedList<>(
                (ArrayList<ChargerPoint>) new Gson().fromJson(
                        reader, new TypeToken<List<ChargerPoint>>(){}.getType()
                )
        );

        mDaoSession = new DaoMaster(new DaoMaster.DevOpenHelper(this, "charger.db").getWritableDb()).newSession();
        ChargerPointDao chargerPointDao = mDaoSession.getChargerPointDao();

        for (int i = 0; i < list.size(); i++) {
            chargerPointDao.insert(list.get(i));
        }

        CustomerDao customerPointDao = mDaoSession.getCustomerDao();
        Customer customer = new Customer();
        customer.setId(1L);
        customerPointDao.insert(customer);
    }

    /**
     * This method check the MAPS services is OK
     */
    public boolean isServicesOk() {
        String TAG = getString(R.string.splash_actvitiy_name);
        Log.d(TAG, getString(R.string.splash_activity_google_services_check_msg));

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(SplashActivity.this);

        if(available == ConnectionResult.SUCCESS) {
            Log.d(TAG, getString(R.string.splash_activity_google_services_working_msg));
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, getString(R.string.splash_activity_google_services_not_working_msg));
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(SplashActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, R.string.splash_activity_google_map_not_work, Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
