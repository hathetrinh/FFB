package comphieubengoan.game;

import com.badlogic.gdx.Gdx;

public interface GameDefine {
    boolean DEBUG_MODE = false;

    int DEFAULT_SCREEN_WITDH = 480;
    int DEFAULT_SCREEN_HEIGHT = 800;

    int DEFAULT_MENU_ITEM_FONT_SIZE = 20 * Gdx.graphics.getWidth() / DEFAULT_SCREEN_WITDH;
    int DEFAULT_MENU_ELEMENT_PADDING = 15 * Gdx.graphics.getWidth() / DEFAULT_SCREEN_WITDH;
    int DEFAULT_FONT_MENU_TITLE_SIZE = 35 * Gdx.graphics.getWidth() / DEFAULT_SCREEN_WITDH;
    int DEFAULT_MENU_ITEM_WIDTH = 250 * Gdx.graphics.getWidth() / DEFAULT_SCREEN_WITDH;

    float GAME_SCREEN_WIDTH = 14;
    float GAME_SCREEN_HEIGHT = GAME_SCREEN_WIDTH * Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

    String DEFAULT_FONT_NAME = "skin/menu_item.ttf";
    String DEFAULT_FONT_TITLE_NAME = "skin/menu_title.ttf";
    String GAME_ATLAS = "sprites/flappy.atlas";
    String GAME_SKIN = "skin/glassy-ui.json";

    float GRAVITY = -4.8f;
    float JUMP_SPEED = 1.1f;
    float PIPE_SPEED = -3.5f;
}
