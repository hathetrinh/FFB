package comphieubengoan.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import comphieubengoan.game.AppPreferenes;
import comphieubengoan.game.GameDefine;
import comphieubengoan.game.MyGdxGame;
import comphieubengoan.game.loader.FBirdAssetManager;
import utils.MyFont;

public class MenuScreen implements Screen {

    private Stage stage;
    private Image background;
    private final String TAG = MenuScreen.class.getName();

    public MenuScreen() {
        stage = new Stage(new ScreenViewport());
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
        table.setFillParent(true);
        table.setDebug(GameDefine.DEBUG_MODE);
        stage.addActor(table);
        table.add(new Label("SETTING", new Label.LabelStyle(myHeaderFont, Color.NAVY))).fillX().uniformX();
        table.row().pad(30, 0, 20, 0);

        table.add(new Label("Music enable", new Label.LabelStyle(myFont, Color.BROWN)));
        CheckBox musicEnable = new CheckBox("", skin);
        musicEnable.setChecked(AppPreferenes.getInstance().isMusicEnable());
        musicEnable.addListener((event) -> {
            AppPreferenes.getInstance().setMusicEnable(musicEnable.isChecked());
            return false;
        });
        table.add(musicEnable).width(GameDefine.DEFAULT_MENU_ITEM_WIDTH);

        table.row().pad(10, 0, 20, 0);

        table.add(new Label("Music", new Label.LabelStyle(myFont, Color.BROWN)));
        Slider musicSlide = new Slider(0f, 1f, 0.1f, false, skin);
        table.add(musicSlide).width(GameDefine.DEFAULT_MENU_ITEM_WIDTH);
        musicSlide.setValue(AppPreferenes.getInstance().getMusicVolume());
        musicSlide.addListener((event) -> {
            AppPreferenes.getInstance().setMusicVolume(musicSlide.getValue());
            return false;
        });

        table.row().pad(10, 0, 20, 0);

        table.add(new Label("Sound enable", new Label.LabelStyle(myFont, Color.BROWN)));
        CheckBox soundEnable = new CheckBox("", skin);
        soundEnable.setChecked(AppPreferenes.getInstance().isSoundEnable());
        soundEnable.addListener((event) -> {
            AppPreferenes.getInstance().setSoundEnable(soundEnable.isChecked());
            return false;
        });
        table.add(soundEnable);

        table.row().pad(10, 0, 20, 0);

        table.add(new Label("Sound", new Label.LabelStyle(myFont, Color.BROWN)));
        Slider soundSlider = new Slider(0f, 1f, 0.1f, false, skin);
        table.add(soundSlider).width(GameDefine.DEFAULT_MENU_ITEM_WIDTH);
        soundSlider.setValue(AppPreferenes.getInstance().getSoundVolume());
        soundSlider.addListener((event) -> {
            AppPreferenes.getInstance().setSoundVolume(soundSlider.getValue());
            return false;
        });

        table.row().pad(10, 0, 20, 0);
        table.add(new Label("Bird Color", new Label.LabelStyle(myFont, Color.BROWN)));
        SelectBox selectColor = new SelectBox(skin);
        selectColor.setItems(FBirdAssetManager.BirdAnimationColor.values());
        selectColor.setSelected(FBirdAssetManager.BirdAnimationColor.valueOf(AppPreferenes.getInstance().getActorColor()));
        table.add(selectColor);
        selectColor.addListener((event) -> {
            if (event instanceof ChangeListener.ChangeEvent) {
                AppPreferenes.getInstance().setActorColor(selectColor.getSelected().toString());
            }
            return false;
        });

        table.row().pad(10, 0, 20, 0);
        Label back = new Label("PLAY", new Label.LabelStyle(myFont, Color.NAVY));
        back.addListener((event) -> {
            Gdx.app.log(TAG, event.toString());
            if (event.toString().equals(InputEvent.Type.touchDown.name())) {
                MyGdxGame.getInstance().switchScreen(MyGdxGame.GameScreens.GAME_PLAY);
            }
            return false;
        });
        table.add(back);

        Label exit = new Label("EXIT", new Label.LabelStyle(myFont, Color.RED));
        exit.addListener((event) -> {
            Gdx.app.log(TAG, event.toString());
            if (event.toString().equals(InputEvent.Type.touchDown.name())) {
                Gdx.app.exit();
            }
            return false;
        });
        table.add(exit);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(stage);
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
    }
}
