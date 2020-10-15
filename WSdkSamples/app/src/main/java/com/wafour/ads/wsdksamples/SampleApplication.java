package com.wafour.ads.wsdksamples;

import android.app.Application;

import com.wafour.ads.sdk.WSdk;

public class SampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WSdk.init(this, Consts.PUBLISHER_ID);
    }
}
