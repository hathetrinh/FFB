/*
 * Copyright (c) 2018
 */

package comphieubengoan.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import comphieubengoan.game.MyGdxGame;
import utils.AnimationActor;
import comphieubengoan.game.loader.FBirdAssetManager;

public class LoadScreen implements Screen {

    private final String TAG = LoadScreen.class.getName();
    private Stage stage;
    private AnimationActor counterActor;
    private Image message, background;

    public LoadScreen() {
        Gdx.app.log(TAG, "Init Loading Screen");
        FBirdAssetManager.getInstance().loadSounds();
        FBirdAssetManager.getInstance().loadTexture();
        FBirdAssetManager.getInstance().loadSkin();
        stage = new Stage(new ScreenViewport());
        counterActor = new AnimationActor(FBirdAssetManager.getInstance().getCountDownAnimation());
        message = FBirdAssetManager.getInstance().getImage("message");
        background = FBirdAssetManager.getInstance().getImage("background-day");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(background);
        counterActor.setPosition((Gdx.graphics.getWidth() - counterActor.getWidth()) / 2 - 10, Gdx.graphics.getHeight() / 2);
        counterActor.setZIndex(Integer.MAX_VALUE);
        message.setOrigin(Align.center);
        message.setPosition((Gdx.graphics.getWidth() - message.getWidth()) / 2, (Gdx.graphics.getHeight() - message.getHeight()) / 2);
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
        if (counterActor.isAnimationFinished()) {
            MyGdxGame.getInstance().switchScreen(MyGdxGame.GameScreens.MENU);
        }
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
