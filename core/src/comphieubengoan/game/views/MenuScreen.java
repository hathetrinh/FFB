package comphieubengoan.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;

import comphieubengoan.game.AppPreferences;
import comphieubengoan.game.GameDefine;
import comphieubengoan.game.MyGdxGame;
import comphieubengoan.game.loader.FBirdAssetManager;
import utils.MyFont;

public class MenuScreen implements Screen {

    private Camera camera;
    private Stage stage;
    private Image background;
    private final String TAG = MenuScreen.class.getName();

    public MenuScreen() {
        camera = new OrthographicCamera();
        stage = new Stage(new FillViewport(GameDefine.DEFAULT_SCREEN_WIDTH, GameDefine.DEFAULT_SCREEN_HEIGHT, camera));
    }

    @Override
    public void show() {
        background = FBirdAssetManager.getInstance().getImage("background-day");
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(background);

        Skin skin = FBirdAssetManager.getInstance().getSkin();

        BitmapFont myFont = new MyFont.MyFontBuilder(GameDefine.DEFAULT_FONT_NAME).setFontSize(GameDefine.DEFAULT_MENU_ITEM_FONT_SIZE).build().getFont();
        BitmapFont myHeaderFont = new MyFont.MyFontBuilder(GameDefine.DEFAULT_FONT_TITLE_NAME).setFontSize(GameDefine.DEFAULT_FONT_MENU_TITLE_SIZE).build().getFont();
        skin.add("myFont", myFont, BitmapFont.class);

        Table table = new Table(skin);

        table.pad(GameDefine.DEFAULT_MENU_ELEMENT_PADDING).align(Align.center);
        table.setSize(GameDefine.DEFAULT_SCREEN_WIDTH, GameDefine.DEFAULT_SCREEN_HEIGHT);
        table.setDebug(GameDefine.DEBUG_MODE);
        stage.addActor(table);
        table.add(new Label("SETTING", new Label.LabelStyle(myHeaderFont, Color.NAVY))).fillX().uniformX();
        table.row().pad(20, 0, 20, 0);

        table.add(new Label("Music enable", new Label.LabelStyle(myFont, Color.BROWN)));
        CheckBox musicEnable = new CheckBox("", skin);
        musicEnable.setChecked(AppPreferences.getInstance().isMusicEnable());
        musicEnable.addListener((event) -> {
            AppPreferences.getInstance().setMusicEnable(musicEnable.isChecked());
            return false;
        });
        table.add(musicEnable);

        table.row().pad(10, 0, 20, 0);

        table.add(new Label("Music", new Label.LabelStyle(myFont, Color.BROWN)));
        Slider musicSlide = new Slider(0f, 1f, 0.1f, false, skin);
        table.add(musicSlide).width(GameDefine.DEFAULT_MENU_ITEM_WIDTH);
        musicSlide.setValue(AppPreferences.getInstance().getMusicVolume());
        musicSlide.addListener((event) -> {
            AppPreferences.getInstance().setMusicVolume(musicSlide.getValue());
            return false;
        });

        table.row().pad(10, 0, 20, 0);
        table.add(new Label("Bird Color", new Label.LabelStyle(myFont, Color.BROWN)));
        SelectBox selectColor = new SelectBox(skin);
        selectColor.setItems(FBirdAssetManager.BirdAnimationColor.values());
        selectColor.setSelected(FBirdAssetManager.BirdAnimationColor.valueOf(AppPreferences.getInstance().getActorColor().toUpperCase()));
        table.add(selectColor);
        selectColor.addListener((event) -> {
            if (event instanceof ChangeListener.ChangeEvent) {
                AppPreferences.getInstance().setActorColor(selectColor.getSelected().toString());
            }
            return false;
        });

        table.row().pad(50, 0, 20, 0);
        Label highScore = new Label("HIGH SCORE", new Label.LabelStyle(myFont, Color.NAVY));
        table.add(highScore);

        Label score = new Label(AppPreferences.getInstance().getMaxPoint() + "", new Label.LabelStyle(myHeaderFont, Color.RED));
        table.add(score);

        table.row().pad(50, 0, 20, 0);
        Image play = FBirdAssetManager.getInstance().getImage("play");
        table.add(play).size(50, 50);

        Image exit = FBirdAssetManager.getInstance().getImage("exit");
        table.add(exit).size(50, 50);
        stage.addActor(table);

        exit.addListener((event) -> {
            Gdx.app.log(TAG, event.toString());
            if (event.toString().equals(InputEvent.Type.touchDown.name())) {
                exitApp();
                return false;
            }
            return false;
        });

        play.addListener((event) -> {
            Gdx.app.log(TAG, event.toString());
            if (event.toString().equals(InputEvent.Type.touchDown.name())) {
                MyGdxGame.getInstance().switchScreen(MyGdxGame.GameScreens.GAME_PLAY);
            }
            return false;
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(stage);
        stage.getCamera().update();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        camera = null;
        background = null;
    }

    public void exitApp() {
        FBirdAssetManager.getInstance().dispose();
        dispose();
        Gdx.app.exit();
    }
}
