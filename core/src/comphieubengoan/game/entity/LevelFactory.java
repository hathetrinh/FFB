package comphieubengoan.game.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import comphieubengoan.game.AppPreferenes;
import comphieubengoan.game.entity.components.*;
import comphieubengoan.game.loader.FBirdAssetManager;

public class LevelFactory {

    private PooledEngine engine;

    public LevelFactory(PooledEngine engine) {
        this.engine = engine;
    }

    public Entity createPlayer() {
        Entity player = engine.createEntity();

        VelocityComponent velocityComponent = engine.createComponent(VelocityComponent.class);
        AnimationComponent animationComponent = engine.createComponent(AnimationComponent.class);
        TransformationComponent transform = engine.createComponent(TransformationComponent.class);
        StateComponent stateComponent = engine.createComponent(StateComponent.class);
        TextureRegionComponent textureRegionComponent = engine.createComponent(TextureRegionComponent.class);

        GameObjectComponent gameObjectComponent = engine.createComponent(GameObjectComponent.class);
        gameObjectComponent.gameObject = GameObjectComponent.PLAYER;
        player.add(gameObjectComponent);

        player.add(textureRegionComponent);

        velocityComponent.setVx(0.0f);
        velocityComponent.setVy(0.0f);

        player.add(velocityComponent);

        stateComponent.loop = true;
        stateComponent.set(StateComponent.STATE_NORMAL);
        player.add(stateComponent);

        Animation ani = FBirdAssetManager.getInstance().getBirdAnimation(FBirdAssetManager.BirdAnimationColor.valueOf(AppPreferenes.getInstance().getActorColor()));
        animationComponent.animations.put(stateComponent.get(), ani);

        player.add(animationComponent);

        transform.position = new Vector3(0.0f, 0.0f, gameObjectComponent.gameObject);
        player.add(transform);
        engine.addEntity(player);

        return player;
    }

    public Entity createPipe() {
        Entity pipe = engine.createEntity();
        VelocityComponent vl = engine.createComponent(VelocityComponent.class);
        TransformationComponent transform = engine.createComponent(TransformationComponent.class);
        TextureRegionComponent tr = engine.createComponent(TextureRegionComponent.class);
        GameObjectComponent gameObjectComponent = engine.createComponent(GameObjectComponent.class);

        gameObjectComponent.gameObject = GameObjectComponent.PIPE;
        pipe.add(gameObjectComponent);

        vl.setVy(0f);
        vl.setVx(2.0f);
        pipe.add(vl);

        transform.position = new Vector3(0.0f, 0.0f, gameObjectComponent.gameObject);
        pipe.add(transform);

        tr.setTextureRegion(FBirdAssetManager.getInstance().getTextureRegion(FBirdAssetManager.PIPE_IMAGE));
        pipe.add(tr);

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

        transform.position.set(new Vector3(0.0f, 0.0f, gameObjectComponent.gameObject));
        transform.scale.set(new Vector2((float) Gdx.graphics.getWidth() / tr.getTextureRegion().getRegionWidth(), (float) Gdx.graphics.getHeight() / tr.getTextureRegion().getRegionHeight()));
        background.add(transform);

        engine.addEntity(background);

        return background;
    }
}
