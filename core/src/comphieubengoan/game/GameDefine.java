package comphieubengoan.game;

public interface GameDefine {
    boolean DEBUG_MODE = false;

    int DEFAULT_SCREEN_WIDTH = 480;
    int DEFAULT_SCREEN_HEIGHT = 800;

    int DEFAULT_MENU_ITEM_FONT_SIZE = 20;
    int DEFAULT_MENU_ELEMENT_PADDING = 15;
    int DEFAULT_FONT_MENU_TITLE_SIZE = 35;
    int DEFAULT_MENU_ITEM_WIDTH = 250;

    float GAME_SCREEN_WIDTH = 15;
    float GAME_SCREEN_HEIGHT = 25;

    String DEFAULT_FONT_NAME = "skin/menu_item.ttf";
    String DEFAULT_FONT_TITLE_NAME = "skin/menu_title.ttf";
    String GAME_ATLAS = "sprites/flappy.atlas";
    String GAME_SKIN = "skin/plain-james-ui.json";

    float GRAVITY = -6.8f;
    float JUMP_SPEED = 1.7f;
    float PIPE_SPEED = -4.5f;
}
