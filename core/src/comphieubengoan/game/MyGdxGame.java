package comphieubengoan.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import comphieubengoan.game.views.*;

public class MyGdxGame extends Game {

    private static MyGdxGame instance;

    private MyGdxGame() {
    }

    public static MyGdxGame getInstance() {
        if (instance == null) {
            instance = new MyGdxGame();
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
    }

    public void switchScreen(GameScreens screen) {
        Screen currentScreen;
        switch (screen) {
            case MENU:
                if (this.menuScreen == null)
                    this.menuScreen = new MenuScreen();
                currentScreen = this.menuScreen;
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
    }
}
