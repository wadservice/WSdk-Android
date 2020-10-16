package com.wafour.ads.wsdksamples.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.wafour.ads.sdk.WErrorCode;
import com.wafour.ads.sdk.WSdk;
import com.wafour.ads.sdk.banner.BannerAdView;
import com.wafour.ads.wsdksamples.Consts;
import com.wafour.ads.wsdksamples.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BannerActivity extends AppCompatActivity {
    private static final String TAG = "BannerActivity";

    private BannerAdView m_banner50;
    private BannerAdView m_banner100;
    private BannerAdView m_banner250;
    private BannerAdView m_bannerAdaptive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Banner Test");
        setSupportActionBar(toolbar);

        initAds();
    }

    private void initAds() {
        /* created banner from code */
        LinearLayout cont = findViewById(R.id.adcontainer);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;

        m_banner50 = createBanner(Consts.APP_ID, Consts.UNIT_ID_50, false);
        cont.addView(m_banner50, params);

        m_banner100 = createBanner(Consts.APP_ID, Consts.UNIT_ID_100, false);
        cont.addView(m_banner100, params);

        m_banner250 = createBanner(Consts.APP_ID, Consts.UNIT_ID_250, false);
        cont.addView(m_banner250, params);

        /* created banner from xml */
        m_bannerAdaptive = findViewById(R.id.bottom_ad);
        m_bannerAdaptive.setAppId(Consts.APP_ID);
        m_bannerAdaptive.setAdUnitId(Consts.UNIT_ID_50);
        m_bannerAdaptive.setAdaptive(true);
        m_bannerAdaptive.loadAd();
    }

    private BannerAdView createBanner(String appId, String unitId, boolean adaptiveMode) {
        BannerAdView bannerAdView = new BannerAdView(this);

        bannerAdView.setAppId(appId);
        bannerAdView.setAdUnitId(unitId);

        //bannerAdView.setTesting(true);
        bannerAdView.setAdaptive(adaptiveMode);
        bannerAdView.setBannerAdListener(new BannerAdView.BannerAdListener() {
            @Override
            public void onBannerLoaded(BannerAdView banner) {
                Log.v(TAG, "onBannerLoaded");

            }

            @Override
            public void onBannerFailed(BannerAdView banner, WErrorCode errorCode) {
                Log.v(TAG, "onBannerFailed e = " + errorCode.toString());
            }

            @Override
            public void onBannerClicked(BannerAdView banner) {
                Log.v(TAG, "onBannerClicked");
            }

            @Override
            public void onBannerExpanded(BannerAdView banner) {
                Log.v(TAG, "onBannerExpanded");

            }

            @Override
            public void onBannerCollapsed(BannerAdView banner) {
                Log.v(TAG, "onBannerCollapsed");
            }
        });
        bannerAdView.loadAd();
        return bannerAdView;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(m_banner50 != null) m_banner50.resume();
        if(m_banner100 != null) m_banner100.resume();
        if(m_banner250 != null) m_banner250.resume();
        if(m_bannerAdaptive != null) m_bannerAdaptive.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(m_banner50 != null) m_banner50.pause();
        if(m_banner100 != null) m_banner100.pause();
        if(m_banner250 != null) m_banner250.pause();
        if(m_bannerAdaptive != null) m_bannerAdaptive.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(m_banner50 != null) m_banner50.destroy();
        if(m_banner100 != null) m_banner100.destroy();
        if(m_banner250!= null) m_banner250.destroy();
        if(m_bannerAdaptive != null) m_bannerAdaptive.destroy();

    }
}
