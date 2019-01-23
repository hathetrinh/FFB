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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.phieubengoan.game.crazybird.GameDefine;
import com.phieubengoan.game.crazybird.MyGdxGame;
import com.phieubengoan.game.crazybird.loader.FBirdAssetManager;

public class LoadScreen implements Screen {

    private Stage stage;
    private Image background;
    private Button btnPlay, btnMenu;
    private OrthographicCamera camera;
    private float backgroundPosX = 0;

    public LoadScreen() {
        FBirdAssetManager.getInstance().load();
        camera = new OrthographicCamera();
        stage = new Stage(new StretchViewport(GameDefine.GAME_LOADING_SCREEN_WIDTH, GameDefine.GAME_LOADING_SCREEN_HEIGHT, camera));
        background = FBirdAssetManager.getInstance().getImage("background");
        btnMenu = FBirdAssetManager.getInstance().getButtonGMenu();
        btnMenu.setSize(10f, 4.375f);
        btnPlay = FBirdAssetManager.getInstance().getButtonGPlay();
        btnPlay.setSize(10f, 4.375f);
        stage.addActor(background);
        stage.addActor(btnPlay);
        stage.addActor(btnMenu);
    }

    @Override
    public void show() {
        background.setSize(4 * GameDefine.GAME_LOADING_SCREEN_WIDTH, GameDefine.GAME_LOADING_SCREEN_HEIGHT + 2);
        background.setPosition(0, -2);

        btnPlay.setOrigin(Align.center);
        btnPlay.setPosition((GameDefine.GAME_LOADING_SCREEN_WIDTH - btnPlay.getWidth()) / 2, (GameDefine.GAME_LOADING_SCREEN_HEIGHT - btnPlay.getHeight()) / 2 + 3);
        btnPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MyGdxGame.getInstance().switchScreen(MyGdxGame.GameScreens.COUNTDOWN);
            }
        });

        btnMenu.setOrigin(Align.center);
        btnMenu.setPosition((GameDefine.GAME_LOADING_SCREEN_WIDTH - btnMenu.getWidth()) / 2, (GameDefine.GAME_LOADING_SCREEN_HEIGHT - btnMenu.getHeight()) / 2 - 3);
        btnMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MyGdxGame.getInstance().switchScreen(MyGdxGame.GameScreens.MENU);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(stage);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S, GL20.GL_REPEAT);
        stage.getCamera().update();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        background.setPosition(backgroundPosX -= delta * 1.5f, -2);

        if (backgroundPosX < -3 * GameDefine.GAME_LOADING_SCREEN_WIDTH) {
            backgroundPosX = 0;
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
