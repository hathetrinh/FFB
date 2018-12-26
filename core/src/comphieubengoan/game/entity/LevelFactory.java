package comphieubengoan.game.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import comphieubengoan.game.AppPreferenes;
import comphieubengoan.game.GameDefine;
import comphieubengoan.game.entity.components.*;
import comphieubengoan.game.loader.FBirdAssetManager;
import utils.BodyFactory;

public class LevelFactory {

    private PooledEngine engine;
    private BodyFactory bodyFactory;

    public LevelFactory(PooledEngine engine, BodyFactory bodyFactory) {
        this.engine = engine;
        this.bodyFactory = bodyFactory;
    }

    public Entity createPlayer() {
        Entity player = engine.createEntity();

        VelocityComponent velocityComponent = engine.createComponent(VelocityComponent.class);
        AnimationComponent animationComponent = engine.createComponent(AnimationComponent.class);
        TransformationComponent transform = engine.createComponent(TransformationComponent.class);
        StateComponent stateComponent = engine.createComponent(StateComponent.class);
        TextureRegionComponent textureRegionComponent = engine.createComponent(TextureRegionComponent.class);
        BodyComponent bodyComponent = engine.createComponent(BodyComponent.class);
        PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);

        GameObjectComponent gameObjectComponent = engine.createComponent(GameObjectComponent.class);
        gameObjectComponent.gameObject = GameObjectComponent.PLAYER;
        player.add(gameObjectComponent);

        player.add(textureRegionComponent);

        velocityComponent.setVx(0.0f);
        velocityComponent.setVy(-4.8f);

        player.add(velocityComponent);

        stateComponent.loop = true;
        stateComponent.set(StateComponent.STATE_NORMAL);
        player.add(stateComponent);

        Animation ani = FBirdAssetManager.getInstance().getBirdAnimation(FBirdAssetManager.BirdAnimationColor.valueOf(AppPreferenes.getInstance().getActorColor()));
        animationComponent.animations.put(stateComponent.get(), ani);

        player.add(animationComponent);

        transform.position = new Vector3(10.0f, 20.0f, gameObjectComponent.gameObject);
        transform.size = new Vector2(1, 1);
        player.add(transform);

        bodyComponent.body = this.bodyFactory.makeBoxPolyBody(transform, BodyFactory.STONE, BodyDef.BodyType.DynamicBody);
        player.add(bodyComponent);
        bodyComponent.body.setUserData(player);

        player.add(playerComponent);

        engine.addEntity(player);

        return player;
    }

    public Entity createPipe() {
        Entity pipe = engine.createEntity();
        VelocityComponent vl = engine.createComponent(VelocityComponent.class);
        TransformationComponent transform = engine.createComponent(TransformationComponent.class);
        TextureRegionComponent tr = engine.createComponent(TextureRegionComponent.class);
        GameObjectComponent gameObjectComponent = engine.createComponent(GameObjectComponent.class);
        BodyComponent bodyComponent = engine.createComponent(BodyComponent.class);

        gameObjectComponent.gameObject = GameObjectComponent.PIPE;
        pipe.add(gameObjectComponent);

        vl.setVy(0.0f);
        vl.setVx(1.0f);
        pipe.add(vl);

        tr.setTextureRegion(FBirdAssetManager.getInstance().getTextureRegion(FBirdAssetManager.PIPE_IMAGE));
        pipe.add(tr);

        transform.position = new Vector3(0.0f, 5.0f, gameObjectComponent.gameObject);
        transform.size = new Vector2(2, 2 * tr.getTextureRegion().getRegionHeight() / tr.getTextureRegion().getRegionWidth());
        transform.rotation = -90;
        pipe.add(transform);

        bodyComponent.body = this.bodyFactory.makeBoxPolyBody(transform, BodyFactory.STONE, BodyDef.BodyType.KinematicBody);
        pipe.add(bodyComponent);
        bodyComponent.body.setUserData(pipe);

        engine.addEntity(pipe);
        return pipe;
    }

    public Entity createBackground() {
        Entity background = engine.createEntity();

        TextureRegionComponent tr = engine.createComponent(TextureRegionComponent.class);
        TransformationComponent transform = engine.createComponent(TransformationComponent.class);
        GameObjectComponent gameObjectComponent = engine.createComponent(GameObjectComponent.class);

        gameObjectComponent.gameObject = GameObjectComponent.BACK_GROUND;
        background.add(gameObjectComponent);

        tr.setTextureRegion(FBirdAssetManager.getInstance().getTextureRegion("background-day"));
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
        tf.position.set(new Vector3(position.x / 2f, position.y, GameObjectComponent.PLAYER));
        tf.size.set(new Vector2(GameDefine.SCREEN_WIDTH, 1.0f));
        platform.add(tf);

        BodyComponent bd = engine.createComponent(BodyComponent.class);
        bd.body = bodyFactory.makeBoxPolyBody(tf, BodyFactory.STONE, BodyDef.BodyType.StaticBody);
        return platform;
    }
}
