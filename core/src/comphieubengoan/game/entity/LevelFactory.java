package comphieubengoan.game.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import comphieubengoan.game.AppPreferenes;
import comphieubengoan.game.GameDefine;
import comphieubengoan.game.entity.components.*;
import comphieubengoan.game.loader.FBirdAssetManager;
import utils.BodyFactory;

public class LevelFactory {

    public enum PIPE {
        UP(0, 0),
        DOWN(GameDefine.SCREEN_HEIGHT, 180);

        PIPE(float top, float angle) {
            this.top = top;
            this.angle = angle;
        }

        public float getTop() {
            return top;
        }

        public float getAngle() {
            return angle;
        }

        float top;
        float angle;
    }

    private PooledEngine engine;
    private BodyFactory bodyFactory;

    public LevelFactory(PooledEngine engine, BodyFactory bodyFactory) {
        this.engine = engine;
        this.bodyFactory = bodyFactory;
    }

    public World getWorld() {
        return this.bodyFactory.world;
    }

    public Entity createPlayer() {
        Entity player = engine.createEntity();

        GameObjectComponent gameObjectComponent = engine.createComponent(GameObjectComponent.class);
        gameObjectComponent.gameObject = GameObjectComponent.PLAYER;
        player.add(gameObjectComponent);

        TextureRegionComponent textureRegionComponent = engine.createComponent(TextureRegionComponent.class);
        player.add(textureRegionComponent);

        VelocityComponent velocityComponent = engine.createComponent(VelocityComponent.class);
        velocityComponent.vx = 0.0f;
        velocityComponent.vy = GameDefine.GRAVITY;
        player.add(velocityComponent);

        StateComponent stateComponent = engine.createComponent(StateComponent.class);
        stateComponent.loop = true;
        stateComponent.set(StateComponent.STATE_NORMAL);
        player.add(stateComponent);

        Animation ani = FBirdAssetManager.getInstance().getBirdAnimation(FBirdAssetManager.BirdAnimationColor.valueOf(AppPreferenes.getInstance().getActorColor()));
        AnimationComponent animationComponent = engine.createComponent(AnimationComponent.class);
        animationComponent.animations.put(stateComponent.get(), ani);

        player.add(animationComponent);

        TransformationComponent transform = engine.createComponent(TransformationComponent.class);
        transform.position = new Vector3(10.0f, 20.0f, gameObjectComponent.gameObject);
        transform.size = new Vector2(1, 1);
        player.add(transform);

        BodyComponent bodyComponent = engine.createComponent(BodyComponent.class);
        bodyComponent.body = this.bodyFactory.makeBoxPolyBody(transform, new Vector2(0, 0), 0, BodyFactory.RUBBER, BodyDef.BodyType.DynamicBody);
        player.add(bodyComponent);
        bodyComponent.body.setUserData(player);

        PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);
        player.add(playerComponent);

        CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);
        player.add(collisionComponent);

        engine.addEntity(player);

        return player;
    }

    public Entity createPipe(Vector2 position, PIPE type) {
        Entity pipe = engine.createEntity();

        GameObjectComponent gameObjectComponent = engine.createComponent(GameObjectComponent.class);
        gameObjectComponent.gameObject = GameObjectComponent.PIPE;
        pipe.add(gameObjectComponent);

        VelocityComponent vl = engine.createComponent(VelocityComponent.class);
        vl.vx = GameDefine.PIPE_SPEED;
        vl.vy = 0.0f;
        pipe.add(vl);

        TextureRegionComponent tr = engine.createComponent(TextureRegionComponent.class);
        tr.textureRegion = FBirdAssetManager.getInstance().getTextureRegion(FBirdAssetManager.PIPE_IMAGE);
        pipe.add(tr);

        TransformationComponent transform = engine.createComponent(TransformationComponent.class);
        float y = type == PIPE.UP ? type.getTop() + position.y : type.getTop() - position.y;
        transform.position = new Vector3(position.x, y, gameObjectComponent.gameObject);
        transform.rotation = type.getAngle() / MathUtils.radiansToDegrees;
        transform.size = new Vector2(2, 2 * tr.textureRegion.getRegionHeight() / tr.textureRegion.getRegionWidth());
        pipe.add(transform);

        BodyComponent bodyComponent = engine.createComponent(BodyComponent.class);
        bodyComponent.body = this.bodyFactory.makeBoxPolyBody(transform, BodyFactory.STONE, BodyDef.BodyType.KinematicBody);
        bodyComponent.body.setTransform(transform.position.x, transform.position.y, transform.rotation);
        pipe.add(bodyComponent);
        bodyComponent.body.setUserData(pipe);

        PipeComponent pipeComponent = engine.createComponent(PipeComponent.class);
        pipe.add(pipeComponent);

        engine.addEntity(pipe);
        return pipe;
    }

    public Entity createBugger(Vector2 position, PIPE type) {
        Entity bugger = engine.createEntity();

        GameObjectComponent go = engine.createComponent(GameObjectComponent.class);
        go.gameObject = GameObjectComponent.BUGGER;
        bugger.add(go);

        VelocityComponent vl = engine.createComponent(VelocityComponent.class);
        vl.vx = GameDefine.PIPE_SPEED;
        vl.vy = 0.0f;
        bugger.add(vl);

        TextureRegionComponent tr = engine.createComponent(TextureRegionComponent.class);
        tr.textureRegion = FBirdAssetManager.getInstance().getTextureRegion("redbird-upflap");
        bugger.add(tr);

        TransformationComponent tf = engine.createComponent(TransformationComponent.class);
        float y = type == PIPE.UP ? type.getTop() + position.y : type.getTop() - position.y;
        tf.position = new Vector3(position.x, y, go.gameObject);
        tf.rotation = type.getAngle() / MathUtils.radiansToDegrees;
        tf.size = new Vector2(1, 1);
        bugger.add(tf);

        BodyComponent bd = engine.createComponent(BodyComponent.class);
        bd.body = this.bodyFactory.makeBoxPolyBody(tf, BodyFactory.AIR, BodyDef.BodyType.KinematicBody);
        bd.body.setTransform(tf.position.x, tf.position.y, tf.rotation);
        bodyFactory.makeAllFixturesSensors(bd.body);
        bugger.add(bd);
        bd.body.setUserData(bugger);

        BuggerComponent bg = engine.createComponent(BuggerComponent.class);
        bugger.add(bg);

        engine.addEntity(bugger);
        return bugger;
    }

    public Entity createBackground() {
        Entity background = engine.createEntity();

        TextureRegionComponent tr = engine.createComponent(TextureRegionComponent.class);
        TransformationComponent transform = engine.createComponent(TransformationComponent.class);
        GameObjectComponent gameObjectComponent = engine.createComponent(GameObjectComponent.class);

        gameObjectComponent.gameObject = GameObjectComponent.BACKGROUND;
        background.add(gameObjectComponent);

        tr.textureRegion = FBirdAssetManager.getInstance().getTextureRegion("background-day");
        background.add(tr);

        transform.position.set(new Vector3(GameDefine.SCREEN_WIDTH / 2f, GameDefine.SCREEN_HEIGHT / 2f, gameObjectComponent.gameObject));
        transform.size.set(new Vector2(GameDefine.SCREEN_WIDTH, GameDefine.SCREEN_HEIGHT));
        background.add(transform);

        engine.addEntity(background);

        return background;
    }

    public Entity createPlatform(Vector2 position) {
        Entity platform = engine.createEntity();
        TransformationComponent tf = engine.createComponent(TransformationComponent.class);
        GameObjectComponent go = engine.createComponent(GameObjectComponent.class);
        TextureRegionComponent tr = engine.createComponent(TextureRegionComponent.class);
        BodyComponent bd = engine.createComponent(BodyComponent.class);

        go.gameObject = GameObjectComponent.PLATFORM;
        platform.add(go);

        tf.position.set(new Vector3(position.x / 2f, position.y, go.gameObject));
        tf.size.set(new Vector2(GameDefine.SCREEN_WIDTH, 3.0f));
        platform.add(tf);

        bd.body = bodyFactory.makeBoxPolyBody(tf, BodyFactory.STONE, BodyDef.BodyType.StaticBody);
        bd.body.setUserData(platform);

        tr.textureRegion = FBirdAssetManager.getInstance().getTextureRegion("base");
        platform.add(tr);

        engine.addEntity(platform);
        return platform;
    }
}
