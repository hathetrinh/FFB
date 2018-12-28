/*
 * Copyright (c) 2018
 */

package comphieubengoan.game.views;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import comphieubengoan.game.GameDefine;
import comphieubengoan.game.MyGdxGame;
import comphieubengoan.game.controller.KeyBoardInputController;
import comphieubengoan.game.entity.LevelFactory;
import comphieubengoan.game.entity.components.PlayerComponent;
import comphieubengoan.game.entity.systems.*;
import comphieubengoan.game.loader.FBirdAssetManager;
import utils.BodyFactory;
import utils.FFBContactListener;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {

    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private PooledEngine engine;
    private LevelFactory levelFactory;
    private KeyBoardInputController controller;
    private World world;
    private BodyFactory bodyFactory;
    private Box2DDebugRenderer renderer;

    private Entity player;

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
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new FreePipeSystem(world));
        engine.addSystem(new MapGeneratorSystem(levelFactory));
        engine.addSystem(new FreeBuggerSystem(levelFactory));

        player = levelFactory.createPlayer();
        levelFactory.createBackground();
        levelFactory.createPlatform(new Vector2(GameDefine.SCREEN_WIDTH, GameDefine.SCREEN_HEIGHT + 1f));
        levelFactory.createPlatform(new Vector2(GameDefine.SCREEN_WIDTH, -1.0f));
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

        PlayerComponent player = this.player.getComponent(PlayerComponent.class);
        List<TextureRegion> pointTextureRegions = getPointTextureRegion(player.point);
        spriteBatch.begin();
        Vector2 pointSize = new Vector2(0.75f, 0.75f);
        for (int i = 0; i < pointTextureRegions.size(); i++) {
            spriteBatch.draw(pointTextureRegions.get(i), GameDefine.SCREEN_WIDTH - 2 - (pointSize.x + 0.1f) * i, GameDefine.SCREEN_HEIGHT - 2, 0, 0, pointSize.x, pointSize.y, 1f, 1f, 0f);
        }
        spriteBatch.end();
        if (player == null || (player.isDead && player.isGround)) {
            engine.removeAllEntities();
            MyGdxGame.getInstance().switchScreen(MyGdxGame.GameScreens.MENU);
        }
    }

    public List<TextureRegion> getPointTextureRegion(int point) {
        List<TextureRegion> textureRegion = new ArrayList<>();
        do {
            int val = point % 10;
            textureRegion.add(FBirdAssetManager.getInstance().getTextureRegion(val + ""));
            point = point / 10;
        } while (point > 0);
        return textureRegion;
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
        world.dispose();
        spriteBatch.dispose();
        engine.clearPools();
    }
}
