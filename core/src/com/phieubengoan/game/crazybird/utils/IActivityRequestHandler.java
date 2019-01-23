package com.phieubengoan.game.crazybird.utils;

public interface IActivityRequestHandler {

    void showBannerAd();

    void hideBannerAd();

    boolean isShown();

    void showInterstitial();

    void hideInterstitial();

    void shareLink();
}