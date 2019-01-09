package com.phieubengoan.game.crazybird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.phieubengoan.game.crazybird.utils.AdsController;
import com.phieubengoan.game.crazybird.views.ExitScreen;
import com.phieubengoan.game.crazybird.views.GameScreen;
import com.phieubengoan.game.crazybird.views.LoadScreen;
import com.phieubengoan.game.crazybird.views.MenuScreen;
import com.phieubengoan.game.crazybird.views.SettingScreen;

public class MyGdxGame extends Game {

    private static MyGdxGame instance;
    private AdsController adsController;

    private MyGdxGame(AdsController adsController) {
        this.adsController = adsController;
    }

    private MyGdxGame() {
    }

    public static MyGdxGame getInstance() {
        if (instance == null) {
            instance = new MyGdxGame();
            instance.adsController = new AdsController() {
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
            };
        }
        return instance;
    }

    public AdsController getAdsController() {
        return adsController;
    }

    public static MyGdxGame getInstance(AdsController adsController) {
        if (instance == null) {
            instance = new MyGdxGame(adsController);
        }
        return instance;
    }


    public enum GameScreens {
        MENU,
        SETTING,
        END_GAME,
        GAME_PLAY
    }

    private Screen loadingScreen, menuScreen, gameScreen, settingScreen, exitScreen;

    @Override
    public void create() {
        if (this.loadingScreen == null) {
            this.loadingScreen = new LoadScreen();
        }
        this.setScreen(this.loadingScreen);
        this.adsController.showBannerAd();
    }

    public void switchScreen(GameScreens screen) {
        Screen currentScreen;
        switch (screen) {
            case MENU:
                if (this.menuScreen == null)
                    this.menuScreen = new MenuScreen();
                currentScreen = this.menuScreen;
                this.adsController.showBannerAd();
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
                this.adsController.hideBannerAd();

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
}
