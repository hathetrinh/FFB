package com.phieubengoan.game.crazybird.android;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.phieubengoan.game.crazybird.MyGdxGame;
import com.phieubengoan.game.crazybird.R;
import com.phieubengoan.game.crazybird.utils.IActivityRequestHandler;


public class AndroidLauncher extends AndroidApplication implements IActivityRequestHandler {

    AdView bannerAd;
    AdRequest request;
    boolean isShowingInterstitial;

    @Override
    protected void onPause() {
        super.onPause();
        bannerAd.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bannerAd.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bannerAd.destroy();
    }

    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobileAds.initialize(this, getString(R.string.admob_id));
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        View gameView = initializeForView(MyGdxGame.getInstance(this), config);
        setupAds();

        RelativeLayout layout = new RelativeLayout(this);
        layout.addView(gameView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layout.addView(bannerAd, params);

        setContentView(layout);
    }

    public void setupAds() {
        bannerAd = new AdView(this);
        bannerAd.setVisibility(View.INVISIBLE);
        bannerAd.setBackgroundColor(0xff000000);
        bannerAd.setAdUnitId(getString(R.string.admob_banner_unit));
        bannerAd.setAdSize(AdSize.BANNER);

        Bundle extras = new Bundle();
        extras.putString("max_ad_content_rating", "G");

        request = new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                .build();

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial_unit));
        mInterstitialAd.loadAd(request);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(request);
            }

        });
    }

    @Override
    public void showInterstitial() {
        if (isWifiConnected() && !isShowingInterstitial) {
            isShowingInterstitial = true;
            runOnUiThread(() -> mInterstitialAd.show()
            );
        }
    }

    @Override
    public void hideInterstitial() {
        isShowingInterstitial = false;

    }

    @Override
    public void showBannerAd() {
        if (isWifiConnected() || !bannerAd.isShown()) {
            runOnUiThread(() -> {
                bannerAd.loadAd(request);
                bannerAd.setVisibility(View.VISIBLE);
            });
        }
    }

    @Override
    public void hideBannerAd() {
        runOnUiThread(() ->
                bannerAd.setVisibility(View.INVISIBLE)
        );
    }

    @Override
    public boolean isShown() {
        return bannerAd.isShown();
    }

    @Override
    public void shareLink() {
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.phieubengoan.game.crazybird.android"))
                .build();
        ShareDialog shareDialog = new ShareDialog(this);
        if (shareDialog.canShow(content)) {
            shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
        } else {
            Toast.makeText(this, "Can not share :((", Toast.LENGTH_LONG).show();
        }

    }

    public boolean isWifiConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return (ni != null && ni.isConnected());
    }
}