package com.phieubengoan.game.crazybird.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.phieubengoan.game.crazybird.MyGdxGame;
import com.phieubengoan.game.crazybird.utils.IActivityRequestHandler;

public class HtmlLauncher extends GwtApplication implements IActivityRequestHandler {

    @Override
    public GwtApplicationConfiguration getConfig() {
        return new GwtApplicationConfiguration(720, 1200);
    }

    @Override
    public ApplicationListener createApplicationListener() {
        return MyGdxGame.getInstance();
    }

    @Override
    public void showBannerAd() {

    }

    @Override
    public void hideBannerAd() {

    }

    @Override
    public boolean isShown() {
        return false;
    }

    @Override
    public void showInterstitial() {

    }

    @Override
    public void hideInterstitial() {

    }

    @Override
    public void shareLink() {

    }
}