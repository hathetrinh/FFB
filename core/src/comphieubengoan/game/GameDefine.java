package comphieubengoan.game;

public interface GameDefine {
    boolean DEBUG_MODE = false;
    int DEFAULT_MENU_ITEM_FONT_SIZE = 20;
    int DEFAULT_MENU_ELEMENT_PADDING = 15;
    int DEFAULT_FONT_MENU_TITLE_SIZE = 35;
    int DEFAULT_MENU_ITEM_WIDTH = 250;

    int SCREEN_WIDTH = 16;
    int SCREEN_HEIGHT = SCREEN_WIDTH * 800 / 480;

    String DEFAULT_FONT_NAME = "skin/menu_item.ttf";
    String DEFAULT_FONT_TITLE_NAME = "skin/menu_title.ttf";
    String GAME_ATLAS = "sprites/flappy.atlas";
    String GAME_SKIN = "skin/glassy-ui.json";

    float GRAVITY = -4.0f;
    float JUMP_SPEED = 20.0f;
}
