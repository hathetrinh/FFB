package com.phieubengoan.game.crazybird.views;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.phieubengoan.game.crazybird.AppPreferences;
import com.phieubengoan.game.crazybird.GameDefine;
import com.phieubengoan.game.crazybird.MyGdxGame;
import com.phieubengoan.game.crazybird.controller.KeyBoardInputController;
import com.phieubengoan.game.crazybird.entity.LevelFactory;
import com.phieubengoan.game.crazybird.entity.components.PlayerComponent;
import com.phieubengoan.game.crazybird.entity.systems.AnimatorSystem;
import com.phieubengoan.game.crazybird.entity.systems.BackgroundSystem;
import com.phieubengoan.game.crazybird.entity.systems.BulletSystem;
import com.phieubengoan.game.crazybird.entity.systems.CollisionSystem;
import com.phieubengoan.game.crazybird.entity.systems.EnemySystem;
import com.phieubengoan.game.crazybird.entity.systems.FreeBuggerSystem;
import com.phieubengoan.game.crazybird.entity.systems.FreeEnemySystem;
import com.phieubengoan.game.crazybird.entity.systems.MapGeneratorSystem;
import com.phieubengoan.game.crazybird.entity.systems.MoveAbleSystem;
import com.phieubengoan.game.crazybird.entity.systems.PhysicsSystem;
import com.phieubengoan.game.crazybird.entity.systems.PlayerSystem;
import com.phieubengoan.game.crazybird.entity.systems.RenderSystem;
import com.phieubengoan.game.crazybird.loader.FBirdAssetManager;
import com.phieubengoan.game.crazybird.utils.BodyFactory;
import com.phieubengoan.game.crazybird.utils.FFBContactListener;
import com.phieubengoan.game.crazybird.utils.MyButton;

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
    private Stage stage;
    private MyButton btnReplay, btnMenu, btnPause;
    private Image gameOver;
    private Entity player;
    private int point;
    private boolean isPause;

    public GameScreen() {
        camera = new OrthographicCamera(GameDefine.GAME_SCREEN_WIDTH, GameDefine.GAME_SCREEN_HEIGHT);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(camera.combined);

        controller = new KeyBoardInputController();

        world = new World(new Vector2(0f, GameDefine.GRAVITY), false);
        world.setContactListener(new FFBContactListener());

        bodyFactory = new BodyFactory(world);

        renderer = new Box2DDebugRenderer(true, true, true, true, true, true);

        engine = new PooledEngine();

        levelFactory = new LevelFactory(engine, bodyFactory);

        engine.addSystem(new MoveAbleSystem());
        engine.addSystem(new AnimatorSystem());
        engine.addSystem(new RenderSystem(camera, spriteBatch));
        engine.addSystem(new PlayerSystem(controller));
        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new FreeEnemySystem(world));
        engine.addSystem(new MapGeneratorSystem(levelFactory));
        engine.addSystem(new FreeBuggerSystem(levelFactory));
        engine.addSystem(new BulletSystem(levelFactory));
        engine.addSystem(new EnemySystem(levelFactory));
        engine.addSystem(new BackgroundSystem());

        player = levelFactory.createPlayer();
        levelFactory.createBackground();
        levelFactory.createPlatform(new Vector2(GameDefine.GAME_SCREEN_WIDTH, GameDefine.GAME_SCREEN_HEIGHT + 1));
        levelFactory.createPlatform(new Vector2(GameDefine.GAME_SCREEN_WIDTH, 1.0f));

        stage = new Stage(new StretchViewport(GameDefine.GAME_SCREEN_WIDTH, GameDefine.GAME_SCREEN_HEIGHT, camera));
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
                btnReplay.remove();
                btnPause.remove();
                btnMenu.remove();
                MyGdxGame.getInstance().switchScreen(MyGdxGame.GameScreens.COUNTDOWN);
                MyGdxGame.getInstance().increaseTimeOfReplay();
                MyGdxGame.getInstance().getIActivityRequestHandler().hideInterstitial();
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.btnPause.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isPause = false;
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MIN_FILTER, GL20.GL_NEAREST);
        Gdx.gl.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S, GL20.GL_CLAMP_TO_EDGE);

        renderer.render(world, camera.combined);
        engine.update(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            MyGdxGame.getInstance().exitApp();
            return;
        }

        PlayerComponent player = this.player.getComponent(PlayerComponent.class);
        if (player == null || (player.isDead && player.isGround) || this.isPause) {
            renderInGameMenu(true);
            Gdx.input.setInputProcessor(stage);
            int currentMax = AppPreferences.getInstance().getMaxPoint();
            if (currentMax < point) {
                AppPreferences.getInstance().setMaxPoint(point);
            }
            if (MyGdxGame.getInstance().getTimeOfReplay() % 10 == 0) {
                MyGdxGame.getInstance().getIActivityRequestHandler().showInterstitial();
            }
        } else {
            world.step(1 / 60f, 6, 2);
            this.point = player.point;
            renderInGameMenu(false);
            Gdx.input.setInputProcessor(controller);
        }

    }

    public void renderInGameMenu(Boolean needRender) {
        List<TextureRegion> pointTextureRegions = getPointTextureRegion(point);
        spriteBatch.begin();
        spriteBatch.enableBlending();
        Vector2 pointSize = new Vector2(1 / 2f, 18 / 16f);
        for (int i = 0; i < pointTextureRegions.size(); i++) {
            spriteBatch.draw(pointTextureRegions.get(i), GameDefine.GAME_SCREEN_WIDTH - 2 - (pointSize.x + 0.1f) * i, GameDefine.GAME_SCREEN_HEIGHT - 2, 0, 0, pointSize.x, pointSize.y, 1f, 1f, 0f);
        }

        if (needRender) {
            btnMenu.draw(spriteBatch, 1);
            if (isPause) {
                btnPause.draw(spriteBatch, 1);
                this.stage.addActor(btnPause);
            } else {
                gameOver.draw(spriteBatch, 1);
                btnReplay.draw(spriteBatch, 1);
                this.stage.addActor(btnReplay);

            }
        }
        spriteBatch.end();
    }

    public List<TextureRegion> getPointTextureRegion(int point) {
        List<TextureRegion> textureRegion = new ArrayList<>();
        do {
            int val = point % 10;
            textureRegion.add(FBirdAssetManager.getInstance().getNumber(val));
            point = point / 10;
        } while (point > 0);
        return textureRegion;
    }

    public void createInGameMenu() {
        this.btnMenu = FBirdAssetManager.getInstance().getButtonMenu();
        this.btnMenu.setSize(3f, 3f);
        this.btnMenu.setPosition(GameDefine.GAME_SCREEN_WIDTH / 2 - 1.5f * btnMenu.getWidth(), GameDefine.GAME_SCREEN_HEIGHT / 2 - btnMenu.getHeight() / 2);
        stage.addActor(btnMenu);

        this.btnReplay = FBirdAssetManager.getInstance().getButtonPlay();
        this.btnReplay.setSize(3f, 3f);
        this.btnReplay.setPosition(GameDefine.GAME_SCREEN_WIDTH / 2 + btnReplay.getWidth(), GameDefine.GAME_SCREEN_HEIGHT / 2 - btnReplay.getHeight() / 2);

        this.btnPause = FBirdAssetManager.getInstance().getButtonPause();
        this.btnPause.setSize(3f, 3f);
        this.btnPause.setPosition(GameDefine.GAME_SCREEN_WIDTH / 2 + btnPause.getWidth(), GameDefine.GAME_SCREEN_HEIGHT / 2 - btnPause.getHeight() / 2);

        this.gameOver = FBirdAssetManager.getInstance().getImage("gameover");
        this.gameOver.setSize(8f, 4f);
        this.gameOver.setPosition(GameDefine.GAME_SCREEN_WIDTH / 2 - 4, GameDefine.GAME_SCREEN_HEIGHT / 2 + 3);
        stage.addActor(gameOver);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

        if (player != null && player.getComponent(PlayerComponent.class) != null && !player.getComponent(PlayerComponent.class).isDead) {
            this.isPause = true;
        }
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
        camera = null;
    }
}
