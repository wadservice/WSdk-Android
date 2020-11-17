package com.wafour.ads.wsdksamples.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wafour.ads.sdk.WErrorCode;
import com.wafour.ads.sdk.nativeAd.NativeAd;
import com.wafour.ads.sdk.nativeAd.NativeAdView;
import com.wafour.ads.sdk.nativeAd.NativeAdViewBinder;
import com.wafour.ads.wsdksamples.Consts;
import com.wafour.ads.wsdksamples.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NativeAdActivity extends AppCompatActivity {

    public static final String TAG = NativeAdActivity.class.getSimpleName();

    private static final int AD_INDEX = 5;

    private RecyclerView m_listview = null;
    private NativeAd m_nativeAd = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_native);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Native Ad Test");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView appId = findViewById(R.id.text_app_id);
        appId.setText("APPID: " + Consts.APP_ID);

        TextView unitId = findViewById(R.id.text_unit_id);
        unitId.setText("UNIT ID : " + Consts.UNIT_ID_NATIVE);

        List<Item> items = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            Item item = new Item("Item + " + (i+1));
            items.add(item);
        }
        final ListAdapter adapter = new ListAdapter(items);
        m_listview = findViewById(R.id.listview);
        m_listview.setLayoutManager(new LinearLayoutManager(this));
        m_listview.setAdapter(adapter);

        NativeAdViewBinder viewBinder = new NativeAdViewBinder.Builder(R.layout.native_ad_template)
                .titleResId(R.id.native_ad_title)
                .descResId(R.id.native_ad_description)
                .mainImageResId(R.id.native_ad_main)
                .iconImageResId(R.id.native_ad_icon)
                .ctaResId(R.id.native_ad_cta)
                .optoutResId(R.id.native_optout)
                .build();

        m_nativeAd = new NativeAd(this, viewBinder);
        m_nativeAd.setAppId(Consts.APP_ID);
        m_nativeAd.setUnitId(Consts.UNIT_ID_NATIVE);
        m_nativeAd.setAdListener(new NativeAd.AdListener() {
            @Override
            public void onNativeAdLoaded(NativeAd nativeAd) {
                Log.v(TAG, "NativeAd Loaded");
                NativeAdView nativeAdView = nativeAd.getAdView();
                m_nativeAd = nativeAd;
                adapter.addItem(AD_INDEX, new Item(nativeAdView));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNativeAdFailed(NativeAd nativeAd, WErrorCode errorCode) {
                Log.e(TAG, "Native Ad Load Failed ! " + errorCode);
            }

            @Override
            public void onNativeAdShown(NativeAd nativeAd) {
                Log.v(TAG, "Native Ad Shown!");
            }

            @Override
            public void onNativeAdClicked(NativeAd nativeAd) {
                Log.v(TAG, "Native Ad Clicked!");

            }
        });
        m_nativeAd.loadAd();
    }

    @Override
    protected void onDestroy() {
        if (m_nativeAd != null) {
            m_nativeAd.destroy();
            m_nativeAd = null;
        }

        super.onDestroy();
    }

    public class Item {
        private String m_text;
        private NativeAdView m_adView;
        Item(String text) {
            m_text = text;
            m_adView = null;
        }
        Item(NativeAdView nativeAdView) {
            m_text = null;
            m_adView = nativeAdView;
        }

        String getText() { return m_text; }
        NativeAdView getAdView() { return m_adView; }
        boolean isAd() { return m_adView != null; }
    }
    public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int VIEW_TYPE_TEXT = 1;
        private static final int VIEW_TYPE_AD = 2;

        public class AdViewHolder extends RecyclerView.ViewHolder {
            private LinearLayout adContainer;
            public AdViewHolder(@NonNull View itemView) {
                super(itemView);
                adContainer = itemView.findViewById(R.id.ad_container);
            }

            public void setAdView(NativeAdView adView) {
                adContainer.removeAllViews();
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                adContainer.addView(adView);
            }

        }

        public class TextViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public TextViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.text_view);
            }
            public void setText(String text) {
                textView.setText(text);
            }
        }

        private List<Item> m_data = new ArrayList<>();

        public ListAdapter(List<Item> data) {
            m_data = data;
        }

        public void setItem(List<Item> data) {
            m_data = data;
        }

        public void addItem(int index, Item item) {
            m_data.add(index, item);
        }

        public int getItemViewType(int position) {
            Item item = m_data.get(position);
            if(item.isAd()) return VIEW_TYPE_AD;
            return VIEW_TYPE_TEXT;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater lf = LayoutInflater.from(NativeAdActivity.this);
            if(viewType == VIEW_TYPE_TEXT) {
                View itemView = lf.inflate(R.layout.list_item_text, null);
                return new TextViewHolder(itemView);
            } else if(viewType == VIEW_TYPE_AD) {
                View itemView = lf.inflate(R.layout.list_item_ad, null);
                return new AdViewHolder(itemView);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Item item = m_data.get(position);
            if(holder instanceof AdViewHolder) {
                AdViewHolder adViewHolder = (AdViewHolder)holder;
                adViewHolder.setAdView(item.getAdView());
            } else if(holder instanceof TextViewHolder) {
                TextViewHolder textViewHolder = (TextViewHolder)holder;
                textViewHolder.setText(item.getText());
            }
        }

        @Override
        public int getItemCount() {
            return m_data.size() ;
        }
    }



}

