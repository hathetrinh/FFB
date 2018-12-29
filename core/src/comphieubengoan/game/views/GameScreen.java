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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;

import java.util.ArrayList;
import java.util.List;

import comphieubengoan.game.AppPreferences;
import comphieubengoan.game.GameDefine;
import comphieubengoan.game.MyGdxGame;
import comphieubengoan.game.controller.KeyBoardInputController;
import comphieubengoan.game.entity.LevelFactory;
import comphieubengoan.game.entity.components.PlayerComponent;
import comphieubengoan.game.entity.systems.AnimatorSystem;
import comphieubengoan.game.entity.systems.CollisionSystem;
import comphieubengoan.game.entity.systems.FreeBuggerSystem;
import comphieubengoan.game.entity.systems.FreePipeSystem;
import comphieubengoan.game.entity.systems.MapGeneratorSystem;
import comphieubengoan.game.entity.systems.MoveAbleSystem;
import comphieubengoan.game.entity.systems.PhysicsSystem;
import comphieubengoan.game.entity.systems.PlayerSystem;
import comphieubengoan.game.entity.systems.RenderSystem;
import comphieubengoan.game.loader.FBirdAssetManager;
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
    private int point;
    private boolean isPause;
    private Stage stage;

    private Image btnSettings, btnReplay, btnMenu;

    private Entity player;

    public GameScreen() {
        camera = new OrthographicCamera(GameDefine.GAME_SCREEN_WIDTH, GameDefine.GAME_SCREEN_HEIGHT);
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
        levelFactory.createPlatform(new Vector2(GameDefine.GAME_SCREEN_WIDTH, GameDefine.GAME_SCREEN_HEIGHT + 1f));
        levelFactory.createPlatform(new Vector2(GameDefine.GAME_SCREEN_WIDTH, -1.0f));


        stage = new Stage(new FillViewport(GameDefine.GAME_SCREEN_WIDTH, GameDefine.GAME_SCREEN_HEIGHT, camera));
        createInGameMenu();
    }

    @Override
    public void show() {
        this.btnMenu.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                engine.removeAllEntities();
                MyGdxGame.getInstance().switchScreen(MyGdxGame.GameScreens.MENU);
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.btnReplay.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                engine.removeAllEntities();
                MyGdxGame.getInstance().switchScreen(MyGdxGame.GameScreens.GAME_PLAY);
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render(world, camera.combined);
        engine.update(delta);

        PlayerComponent player = this.player.getComponent(PlayerComponent.class);
        if (player == null || (player.isDead && player.isGround) || this.isPause) {
            renderInGameMenu(true);
            Gdx.input.setInputProcessor(stage);
            int currentMax = AppPreferences.getInstance().getMaxPoint();
            if (currentMax < point) {
                AppPreferences.getInstance().setMaxPoint(point);
            }
        } else {
            world.step(1 / 60f, 6, 2);
            this.point = player.point;
            renderInGameMenu(false);
            Gdx.input.setInputProcessor(controller);
        }
    }

    public void renderInGameMenu(Boolean isPause) {
        List<TextureRegion> pointTextureRegions = getPointTextureRegion(point);
        spriteBatch.begin();
        Vector2 pointSize = new Vector2(1 / 2f, 18 / 16f);
        for (int i = 0; i < pointTextureRegions.size(); i++) {
            spriteBatch.draw(pointTextureRegions.get(i), GameDefine.GAME_SCREEN_WIDTH - 2 - (pointSize.x + 0.1f) * i, GameDefine.GAME_SCREEN_HEIGHT - 2, 0, 0, pointSize.x, pointSize.y, 1f, 1f, 0f);
        }

        if (isPause) {
            btnMenu.draw(spriteBatch, 1);
            btnReplay.draw(spriteBatch, 1);
        }

        spriteBatch.end();
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

    public void createInGameMenu() {
        this.btnMenu = FBirdAssetManager.getInstance().getImage("menu");
        this.btnMenu.setSize(2f, 2f);
        this.btnMenu.setPosition(3, GameDefine.GAME_SCREEN_HEIGHT / 2);
        stage.addActor(btnMenu);

        this.btnSettings = FBirdAssetManager.getInstance().getImage("setting");
        this.btnSettings.setSize(1f, 1f);
        this.btnSettings.setPosition(1f, GameDefine.GAME_SCREEN_HEIGHT - 2);

        this.btnReplay = FBirdAssetManager.getInstance().getImage("replay");
        this.btnReplay.setSize(2f, 2f);
        this.btnReplay.setPosition(9, GameDefine.GAME_SCREEN_HEIGHT / 2);
        stage.addActor(btnReplay);

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        this.isPause = true;
    }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(controller);
        this.isPause = false;
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
