/*
 * Copyright (c) 2018
 */

package comphieubengoan.game.views;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import comphieubengoan.game.GameDefine;
import comphieubengoan.game.controller.KeyBoardInputController;
import comphieubengoan.game.entity.LevelFactory;
import comphieubengoan.game.entity.systems.*;
import utils.BodyFactory;
import utils.FFBContactListener;

public class GameScreen implements Screen {

    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private PooledEngine engine;
    private LevelFactory levelFactory;
    private KeyBoardInputController controller;
    private World world;
    private BodyFactory bodyFactory;
    private Box2DDebugRenderer renderer;

    public GameScreen() {
        camera = new OrthographicCamera(GameDefine.SCREEN_WIDTH, GameDefine.SCREEN_HEIGHT);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(camera.combined);
        controller = new KeyBoardInputController();
        world = new World(new Vector2(0f, GameDefine.GRAVITY), false);
        world.setContactListener(new FFBContactListener());
        engine = new PooledEngine();
        bodyFactory = new BodyFactory(world);
        renderer = new Box2DDebugRenderer(true, true, true, true, true, true);

        levelFactory = new LevelFactory(engine, bodyFactory);

        engine.addSystem(new MoveAbleSystem());
        engine.addSystem(new AnimatorSystem());
        engine.addSystem(new RenderSystem(camera, spriteBatch));
        engine.addSystem(new PlayerSystem(controller));
        engine.addSystem(new PhysicsSystem(world));

        levelFactory.createPlayer();
        levelFactory.createPipe();
        levelFactory.createBackground();
        levelFactory.createPlatform(new Vector2(GameDefine.SCREEN_WIDTH, GameDefine.SCREEN_HEIGHT));
        levelFactory.createPlatform(new Vector2(GameDefine.SCREEN_WIDTH, 0.0f));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render(world, camera.combined);
        engine.update(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        engine.clearPools();
        world.dispose();
        spriteBatch.dispose();
    }
}
