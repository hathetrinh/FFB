package com.phieubengoan.game.crazybird.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.phieubengoan.game.crazybird.GameDefine;
import com.phieubengoan.game.crazybird.MyGdxGame;
import com.phieubengoan.game.crazybird.loader.FBirdAssetManager;
import com.phieubengoan.game.crazybird.utils.AnimationActor;

public class CountDownScreen implements Screen {

    private Stage stage;
    private AnimationActor counterActor;
    private Image background;
    private OrthographicCamera camera;
    private float backgroundPosX = 0;

    public CountDownScreen() {
        camera = new OrthographicCamera();
        stage = new Stage(new StretchViewport(GameDefine.GAME_LOADING_SCREEN_WIDTH, GameDefine.GAME_LOADING_SCREEN_HEIGHT, camera));
        counterActor = new AnimationActor(FBirdAssetManager.getInstance().getAnimation(FBirdAssetManager.ANI_TYPES.countdown));
        counterActor.setSize(1f, 36 / 16f);
        background = FBirdAssetManager.getInstance().getImage("background");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        background.setSize(4 * GameDefine.GAME_LOADING_SCREEN_WIDTH, GameDefine.GAME_LOADING_SCREEN_HEIGHT + 2);
        background.setPosition(0, -2);
        stage.addActor(background);
        counterActor.setPosition((GameDefine.GAME_LOADING_SCREEN_WIDTH - counterActor.getWidth()) / 2, GameDefine.GAME_LOADING_SCREEN_HEIGHT / 2);
        counterActor.setZIndex(Integer.MAX_VALUE);
        stage.addActor(counterActor);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MIN_FILTER, GL20.GL_NEAREST);
        Gdx.gl.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S, GL20.GL_CLAMP_TO_EDGE);
        stage.getCamera().update();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        background.setPosition(backgroundPosX -= delta * 1.5f, -2);
        if (backgroundPosX < -3 * GameDefine.GAME_LOADING_SCREEN_WIDTH) {
            backgroundPosX = 0;
        }
        if (counterActor.isAnimationFinished()) {
            FBirdAssetManager.getInstance().playMusic(FBirdAssetManager.Audios.point);
            MyGdxGame.getInstance().switchScreen(MyGdxGame.GameScreens.GAME_PLAY);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            MyGdxGame.getInstance().exitApp();
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
