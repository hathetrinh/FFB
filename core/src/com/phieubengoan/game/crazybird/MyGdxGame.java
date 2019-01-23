package com.phieubengoan.game.crazybird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.phieubengoan.game.crazybird.loader.FBirdAssetManager;
import com.phieubengoan.game.crazybird.utils.FontDefines;
import com.phieubengoan.game.crazybird.utils.IActivityRequestHandler;
import com.phieubengoan.game.crazybird.views.CountDownScreen;
import com.phieubengoan.game.crazybird.views.ExitScreen;
import com.phieubengoan.game.crazybird.views.GameScreen;
import com.phieubengoan.game.crazybird.views.LoadScreen;
import com.phieubengoan.game.crazybird.views.MenuScreen;
import com.phieubengoan.game.crazybird.views.SettingScreen;

public class MyGdxGame extends Game {

    private static MyGdxGame instance;
    private IActivityRequestHandler IActivityRequestHandler;
    private static int timeOfReplay = 0;

    private MyGdxGame(IActivityRequestHandler IActivityRequestHandler) {
        this.IActivityRequestHandler = IActivityRequestHandler;
    }

    private MyGdxGame() {
    }

    public static MyGdxGame getInstance() {
        if (instance == null) {
            instance = new MyGdxGame();
            instance.IActivityRequestHandler = new IActivityRequestHandler() {
                @Override
                public void showBannerAd() {
                    Gdx.app.log("ADS", "Do No Thing!");
                }

                @Override
                public void hideBannerAd() {
                    Gdx.app.log("ADS", "Do No Thing!");
                }

                @Override
                public boolean isShown() {
                    Gdx.app.log("ADS", "Do No Thing!");
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

            };
        }
        return instance;
    }

    public IActivityRequestHandler getIActivityRequestHandler() {
        return IActivityRequestHandler;
    }

    public static MyGdxGame getInstance(IActivityRequestHandler IActivityRequestHandler) {
        if (instance == null) {
            instance = new MyGdxGame(IActivityRequestHandler);
        }
        return instance;
    }

    public int getTimeOfReplay() {
        return timeOfReplay;
    }

    public void increaseTimeOfReplay() {
        this.timeOfReplay++;
    }

    public enum GameScreens {
        LOADING,
        COUNTDOWN,
        MENU,
        SETTING,
        END_GAME,
        GAME_PLAY
    }

    private Screen loadingScreen, menuScreen, gameScreen, settingScreen, exitScreen, countDownScreen;

    @Override
    public void create() {
        if (this.loadingScreen == null) {
            this.loadingScreen = new LoadScreen();
        }
        this.setScreen(this.loadingScreen);
        this.IActivityRequestHandler.showBannerAd();
    }

    public void switchScreen(GameScreens screen) {
        Screen currentScreen;
        switch (screen) {
            case COUNTDOWN:
                if (this.countDownScreen != null) {
                    this.countDownScreen.dispose();
                }
                currentScreen = new CountDownScreen();
                this.IActivityRequestHandler.showBannerAd();
                break;
            case MENU:
                if (this.menuScreen == null)
                    this.menuScreen = new MenuScreen();
                currentScreen = this.menuScreen;
                this.IActivityRequestHandler.showBannerAd();
                break;
            case SETTING:
                if (this.settingScreen == null)
                    this.settingScreen = new SettingScreen();
                currentScreen = this.settingScreen;
                break;
            case END_GAME:
                if (this.exitScreen == null)
                    this.exitScreen = new ExitScreen();
                currentScreen = this.exitScreen;
                break;
            case GAME_PLAY:
                this.IActivityRequestHandler.hideBannerAd();

                if (this.gameScreen != null)
                    this.gameScreen.dispose();
                this.gameScreen = new GameScreen();
                currentScreen = this.gameScreen;
                break;
            default:
                currentScreen = loadingScreen;
                break;
        }
        this.setScreen(currentScreen);
    }

    @Override
    public void dispose() {
        super.dispose();
        instance = null;
    }

    public void exitApp() {
        FBirdAssetManager.getInstance().dispose();
        FontDefines.getInstance().dispose();
        MyGdxGame.getInstance().dispose();
        Gdx.app.exit();
    }
}
