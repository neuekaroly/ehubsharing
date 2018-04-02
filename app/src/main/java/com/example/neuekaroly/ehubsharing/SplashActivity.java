package com.example.neuekaroly.ehubsharing;

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

import database.ChargerPoint;
import database.ChargerPointDao;
import database.Customer;
import database.CustomerDao;
import database.DaoMaster;
import database.DaoSession;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    private static final String TAG = "SplashActivity";

    private static final int ERROR_DIALOG_REQUEST = 9001;

    SharedPreferences sharedPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("com.example.neuekaroly.ehubsharing", MODE_PRIVATE);

        Stetho.initializeWithDefaults(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setTheme(R.style.NoActionBar);
        setContentView(R.layout.activity_splash);

        if(isServicesOk()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent homeIntent = new Intent( SplashActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                    //finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (sharedPreferences.getBoolean("firstrun", true)) {
            Log.d("TEST", "FIRSTRUN");

            fillUpDatabase();

            sharedPreferences.edit().putBoolean("firstrun", false).commit();
        }
    }

    private void fillUpDatabase() {
        Reader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("chargers.txt"), "UTF-8"));
        } catch(IOException e) {
            Log.d("Test", e.toString());
        }

        LinkedList<ChargerPoint> list = new LinkedList<>(
                (ArrayList<ChargerPoint>) new Gson().fromJson(
                        reader, new TypeToken<List<ChargerPoint>>(){}.getType()
                )
        );

        DaoSession mDaoSession = new DaoMaster(new DaoMaster.DevOpenHelper(this, "charger.db").getWritableDb()).newSession();
        ChargerPointDao chargerPointDao = mDaoSession.getChargerPointDao();

        for (int i = 0; i < list.size(); i++) {
            chargerPointDao.insert(list.get(i));
        }

        CustomerDao customerPointDao = mDaoSession.getCustomerDao();
        Customer customer = new Customer();
        customer.setId(1L);
        customerPointDao.insert(customer);
    }

    public boolean isServicesOk() {
        Log.d(TAG, "isServicesOk: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(SplashActivity.this);

        if(available == ConnectionResult.SUCCESS) {
            Log.d(TAG, "isServicesOk: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, "isServicesOk: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(SplashActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
