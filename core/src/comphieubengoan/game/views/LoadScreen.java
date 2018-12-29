/*
 * Copyright (c) 2018
 */

package comphieubengoan.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import comphieubengoan.game.GameDefine;
import comphieubengoan.game.MyGdxGame;
import utils.AnimationActor;
import comphieubengoan.game.loader.FBirdAssetManager;

public class LoadScreen implements Screen {

    private final String TAG = LoadScreen.class.getName();
    private Stage stage;
    private AnimationActor counterActor;
    private Image message, background;
    private OrthographicCamera camera;

    public LoadScreen() {
        Gdx.app.log(TAG, "Init Loading Screen");
        FBirdAssetManager.getInstance().loadMusics();
        FBirdAssetManager.getInstance().loadTexture();
        FBirdAssetManager.getInstance().loadSkin();
        camera = new OrthographicCamera();
        stage = new Stage(new FillViewport(GameDefine.GAME_SCREEN_WIDTH, GameDefine.GAME_SCREEN_HEIGHT, camera));
        counterActor = new AnimationActor(FBirdAssetManager.getInstance().getCountDownAnimation());
        counterActor.setSize(1f, 36 / 16f);
        message = FBirdAssetManager.getInstance().getImage("message");
        message.setSize(7f, 7 * 267 / 184f);
        background = FBirdAssetManager.getInstance().getImage("background-day");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        background.setSize(GameDefine.GAME_SCREEN_WIDTH, GameDefine.GAME_SCREEN_WIDTH * 512 / 228);
        stage.addActor(background);
        counterActor.setPosition((GameDefine.GAME_SCREEN_WIDTH - counterActor.getWidth()) / 2, GameDefine.GAME_SCREEN_HEIGHT / 2);
        counterActor.setZIndex(Integer.MAX_VALUE);
        message.setOrigin(Align.center);
        message.setPosition((GameDefine.GAME_SCREEN_WIDTH - message.getWidth()) / 2, (GameDefine.GAME_SCREEN_HEIGHT - message.getHeight()) / 2);
        stage.addActor(message);
        message.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                stage.addActor(counterActor);
                message.remove();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getCamera().update();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        if (counterActor.isAnimationFinished()) {
            MyGdxGame.getInstance().switchScreen(MyGdxGame.GameScreens.GAME_PLAY);
        }
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
