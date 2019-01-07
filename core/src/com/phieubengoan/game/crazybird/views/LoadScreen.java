/*
 * Copyright (c) 2018
 */

package com.phieubengoan.game.crazybird.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;

import com.phieubengoan.game.crazybird.GameDefine;
import com.phieubengoan.game.crazybird.MyGdxGame;
import com.phieubengoan.game.crazybird.utils.AnimationActor;
import com.phieubengoan.game.crazybird.loader.FBirdAssetManager;

public class LoadScreen implements Screen {

    private Stage stage;
    private AnimationActor counterActor;
    private Image background;
    private Button btnPlay, btnMenu;
    private OrthographicCamera camera;
    private float backgroundPosX = 0;

    public LoadScreen() {
        FBirdAssetManager.getInstance().load();
        camera = new OrthographicCamera();
        stage = new Stage(new FillViewport(GameDefine.GAME_SCREEN_WIDTH, GameDefine.GAME_SCREEN_HEIGHT, camera));
        counterActor = new AnimationActor(FBirdAssetManager.getInstance().getAnimation(FBirdAssetManager.ANI_TYPES.countdown));
        counterActor.setSize(1f, 36 / 16f);
        background = FBirdAssetManager.getInstance().getImage("background");
        btnMenu = FBirdAssetManager.getInstance().getButtonMenuMenu();
        btnMenu.setSize(6f, 3f);
        btnPlay = FBirdAssetManager.getInstance().getButtonMenuPlay();
        btnPlay.setSize(6f, 3f);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        background.setSize(4 * GameDefine.GAME_SCREEN_WIDTH, GameDefine.GAME_SCREEN_HEIGHT + 2);
        background.setPosition(0, -2);
        stage.addActor(background);
        counterActor.setPosition((GameDefine.GAME_SCREEN_WIDTH - counterActor.getWidth()) / 2, GameDefine.GAME_SCREEN_HEIGHT / 2);
        counterActor.setZIndex(Integer.MAX_VALUE);
        btnMenu.setOrigin(Align.center);
        btnMenu.setPosition((GameDefine.GAME_SCREEN_WIDTH - btnMenu.getWidth()) / 2, (GameDefine.GAME_SCREEN_HEIGHT - btnMenu.getHeight()) / 2 - 2);
        stage.addActor(btnMenu);
        btnMenu.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MyGdxGame.getInstance().switchScreen(MyGdxGame.GameScreens.MENU);
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        btnPlay.setOrigin(Align.center);
        btnPlay.setPosition((GameDefine.GAME_SCREEN_WIDTH - btnPlay.getWidth()) / 2, (GameDefine.GAME_SCREEN_HEIGHT - btnPlay.getHeight()) / 2 + 2);
        stage.addActor(btnPlay);
        btnPlay.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                stage.addActor(counterActor);
                btnMenu.remove();
                btnPlay.remove();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
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
        if (backgroundPosX < -3 * GameDefine.GAME_SCREEN_WIDTH) {
            backgroundPosX = 0;
        }
        if (counterActor.isAnimationFinished()) {
            FBirdAssetManager.getInstance().playMusic(FBirdAssetManager.Audios.point);
            MyGdxGame.getInstance().switchScreen(MyGdxGame.GameScreens.GAME_PLAY);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            FBirdAssetManager.getInstance().dispose();
            Gdx.app.exit();
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
