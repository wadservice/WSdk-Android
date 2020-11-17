package com.wafour.ads.wsdksamples.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wafour.ads.sdk.WErrorCode;
import com.wafour.ads.sdk.interstitial.WInterstitial;
import com.wafour.ads.wsdksamples.Consts;
import com.wafour.ads.wsdksamples.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "BannerActivity";

    private Button m_btnBanner;
    private Button m_btnBannerWithLocation;
    private Button m_btnInterstitial;
    private Button m_btnNative;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
    }

    private void initViews() {
        m_btnBanner = findViewById(R.id.btn_banner);
        m_btnBannerWithLocation = findViewById(R.id.btn_banner_with_location);
        m_btnInterstitial = findViewById(R.id.btn_interstitial);
        m_btnNative = findViewById(R.id.btn_native);
        m_btnBanner.setOnClickListener(this);
        m_btnBannerWithLocation.setOnClickListener(this);
        m_btnInterstitial.setOnClickListener(this);
        m_btnNative.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_banner) {
            startBannerActivity();
        } else if(v.getId() == R.id.btn_banner_with_location) {
            startBannerWithLocationActivity();
        } else if(v.getId() == R.id.btn_interstitial) {
            showInterstitial();
        } else if(v.getId() == R.id.btn_native) {
            startNativeAdActivity();
        }
    }

    private void startBannerActivity() {
        Intent i = new Intent(this, BannerActivity.class);
        startActivity(i);
    }
    private void startBannerWithLocationActivity() {
        Intent i = new Intent(this, BannerWithLocationActivity.class);
        startActivity(i);
    }
    private void startNativeAdActivity() {
        Intent i = new Intent(this, NativeAdActivity.class);
        startActivity(i);
    }

    private void showInterstitial() {
        WInterstitial interstitial = new WInterstitial(this, Consts.APP_ID, Consts.UNIT_ID_INTERSTITIAL);
        interstitial.setInterstitialAdListener(new WInterstitial.InterstitialAdListener() {
            @Override
            public void onInterstitialLoaded(WInterstitial interstitial) {
                Toast.makeText(MainActivity.this, "onInterstitialLoaded", Toast.LENGTH_SHORT).show();
                interstitial.show();
            }

            @Override
            public void onInterstitialFailed(WInterstitial interstitial, WErrorCode errorCode) {
                Toast.makeText(MainActivity.this, "onInterstitialFailed e="+errorCode.toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onInterstitialShown(WInterstitial interstitial) {
                Toast.makeText(MainActivity.this, "onInterstitialShown", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onInterstitialClicked(WInterstitial interstitial) {
                Toast.makeText(MainActivity.this, "onInterstitialClicked", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onInterstitialDismissed(WInterstitial interstitial) {
                Toast.makeText(MainActivity.this, "onInterstitialDismissed", Toast.LENGTH_SHORT).show();
            }
        });
        interstitial.load();
    }
}
