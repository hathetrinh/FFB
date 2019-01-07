package com.phieubengoan.game.crazybird.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.phieubengoan.game.crazybird.GameDefine;
import com.phieubengoan.game.crazybird.entity.components.AnimationComponent;
import com.phieubengoan.game.crazybird.entity.components.AnimationInfo;
import com.phieubengoan.game.crazybird.entity.components.BackgroundComponent;
import com.phieubengoan.game.crazybird.entity.components.BodyComponent;
import com.phieubengoan.game.crazybird.entity.components.BuggerComponent;
import com.phieubengoan.game.crazybird.entity.components.BulletComponent;
import com.phieubengoan.game.crazybird.entity.components.CollisionComponent;
import com.phieubengoan.game.crazybird.entity.components.EnemyComponent;
import com.phieubengoan.game.crazybird.entity.components.PipeComponent;
import com.phieubengoan.game.crazybird.entity.components.PlayerComponent;
import com.phieubengoan.game.crazybird.entity.components.TextureRegionComponent;
import com.phieubengoan.game.crazybird.entity.components.TransformationComponent;
import com.phieubengoan.game.crazybird.entity.components.VelocityComponent;
import com.phieubengoan.game.crazybird.entity.components.ZIndexDefinition;
import com.phieubengoan.game.crazybird.loader.FBirdAssetManager;
import com.phieubengoan.game.crazybird.utils.BodyFactory;

public class LevelFactory {

    public enum LOCATION {
        BOTTOM(0, 0),
        TOP(GameDefine.GAME_SCREEN_HEIGHT, 180);

        LOCATION(float top, float angle) {
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

        PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);
        playerComponent.state = PlayerComponent.PlayerState.FLY;
        player.add(playerComponent);

        TextureRegionComponent textureRegionComponent = engine.createComponent(TextureRegionComponent.class);
        player.add(textureRegionComponent);

        VelocityComponent velocityComponent = engine.createComponent(VelocityComponent.class);
        velocityComponent.vx = 0.0f;
        velocityComponent.vy = GameDefine.GRAVITY;
        player.add(velocityComponent);

        AnimationInfo animationInfo = engine.createComponent(AnimationInfo.class);
        animationInfo.loop = true;
        animationInfo.set(playerComponent.state.ordinal());
        player.add(animationInfo);

        Animation ani = FBirdAssetManager.getInstance().getAnimation(FBirdAssetManager.ANI_TYPES.bird);
        AnimationComponent animationComponent = engine.createComponent(AnimationComponent.class);
        animationComponent.animations.put(animationInfo.get(), ani);
        Animation dead = FBirdAssetManager.getInstance().getAnimation(FBirdAssetManager.ANI_TYPES.explosion);
        dead.setPlayMode(Animation.PlayMode.NORMAL);
        animationComponent.animations.put(PlayerComponent.PlayerState.DIE.ordinal(), FBirdAssetManager.getInstance().getAnimation(FBirdAssetManager.ANI_TYPES.explosion));
        player.add(animationComponent);

        TransformationComponent transform = engine.createComponent(TransformationComponent.class);
        transform.position = new Vector3(GameDefine.GAME_SCREEN_WIDTH / 2f - 2, GameDefine.GAME_SCREEN_HEIGHT / 2f, ZIndexDefinition.PLAYER);
        transform.size = new Vector2(1.5f, 1.5f);
        player.add(transform);

        BodyComponent bodyComponent = engine.createComponent(BodyComponent.class);
        bodyComponent.body = this.bodyFactory.makeShapeBody(transform, BodyFactory.WOOD, BodyDef.BodyType.DynamicBody, BodyFactory.SHAPE.CIRCLE);
        player.add(bodyComponent);
        bodyComponent.body.setUserData(player);

        CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);
        player.add(collisionComponent);

        engine.addEntity(player);

        return player;
    }

    public Entity createAnimationEnemy(FBirdAssetManager.ANI_TYPES animation, Vector2 position, Vector2 speed, Vector2 size, LOCATION location) {
        Entity enemy = engine.createEntity();

        EnemyComponent enemyComponent = engine.createComponent(EnemyComponent.class);
        enemyComponent.state = EnemyComponent.EnemyState.RUN;
        enemyComponent.isShutter = false;
        enemy.add(enemyComponent);

        VelocityComponent vl = engine.createComponent(VelocityComponent.class);
        vl.vx = speed.x;
        vl.vy = speed.y;
        enemy.add(vl);

        TextureRegionComponent tr = engine.createComponent(TextureRegionComponent.class);
        enemy.add(tr);

        AnimationInfo animationType = engine.createComponent(AnimationInfo.class);
        animationType.set(enemyComponent.state.ordinal());
        animationType.loop = true;
        enemy.add(animationType);

        AnimationComponent an = engine.createComponent(AnimationComponent.class);
        an.animations.put(animationType.state, FBirdAssetManager.getInstance().getAnimation(animation));
        enemy.add(an);

        TransformationComponent transform = engine.createComponent(TransformationComponent.class);
        float y = location == LOCATION.BOTTOM ? location.getTop() + position.y : location.getTop() - position.y;
        transform.position = new Vector3(position.x, y, ZIndexDefinition.ENEMY);
        transform.size = size;
        enemy.add(transform);

        BodyComponent bodyComponent = engine.createComponent(BodyComponent.class);
        bodyComponent.body = this.bodyFactory.makeBoxPolyBody(transform, BodyFactory.STONE, BodyDef.BodyType.KinematicBody);
        bodyComponent.body.setTransform(transform.position.x, transform.position.y, transform.rotation);
        enemy.add(bodyComponent);
        bodyComponent.body.setUserData(enemy);

        PipeComponent pipeComponent = engine.createComponent(PipeComponent.class);
        enemy.add(pipeComponent);

        engine.addEntity(enemy);
        return enemy;
    }

    public Entity createAnimationEnemy(FBirdAssetManager.ANI_TYPES animation, Vector2 position, Vector2 size, LOCATION location) {
        return createAnimationEnemy(animation, position, new Vector2(GameDefine.BUGGER_SPEED, 0), size, location);
    }


    public Entity createTextureEnemy(Vector2 position, Vector2 speed, Vector2 size, TextureRegion texture, LOCATION location) {
        return createTextureEnemy(position, speed, size, texture, location, BodyFactory.SHAPE.RECTANGLE);
    }

    public Entity createTextureEnemy(Vector2 position, Vector2 speed, Vector2 size, TextureRegion texture, LOCATION location, BodyFactory.SHAPE shap) {
        Entity enemy = engine.createEntity();

        EnemyComponent enemyComponent = engine.createComponent(EnemyComponent.class);
        enemyComponent.state = EnemyComponent.EnemyState.RUN;
        enemyComponent.isShutter = false;
        enemy.add(enemyComponent);

        VelocityComponent vl = engine.createComponent(VelocityComponent.class);
        vl.vx = speed.x;
        vl.vy = speed.y;
        enemy.add(vl);

        TextureRegionComponent tr = engine.createComponent(TextureRegionComponent.class);
        tr.textureRegion = texture;
        enemy.add(tr);

        AnimationInfo animationType = engine.createComponent(AnimationInfo.class);
        animationType.set(enemyComponent.state.ordinal());
        animationType.loop = false;
        enemy.add(animationType);

        TransformationComponent transform = engine.createComponent(TransformationComponent.class);
        float y = location == LOCATION.BOTTOM ? location.getTop() + position.y : location.getTop() - position.y;
        transform.position = new Vector3(position.x, y, ZIndexDefinition.ENEMY);
        transform.size = size;
        enemy.add(transform);

        BodyComponent bodyComponent = engine.createComponent(BodyComponent.class);
        bodyComponent.body = this.bodyFactory.makeShapeBody(transform, BodyFactory.STONE, BodyDef.BodyType.KinematicBody, shap);
        bodyComponent.body.setTransform(transform.position.x, transform.position.y, transform.rotation);
        enemy.add(bodyComponent);
        bodyComponent.body.setUserData(enemy);

        PipeComponent pipeComponent = engine.createComponent(PipeComponent.class);
        enemy.add(pipeComponent);

        engine.addEntity(enemy);
        return enemy;
    }

    public Entity createBugger(Vector2 position, LOCATION type) {
        Entity bugger = engine.createEntity();

        BuggerComponent buggerComponent = engine.createComponent(BuggerComponent.class);
        buggerComponent.state = BuggerComponent.BuggerState.SHOW;
        bugger.add(buggerComponent);

        VelocityComponent vl = engine.createComponent(VelocityComponent.class);
        vl.vx = GameDefine.BUGGER_SPEED;
        vl.vy = 0.0f;
        bugger.add(vl);

        TextureRegionComponent tr = engine.createComponent(TextureRegionComponent.class);
        bugger.add(tr);

        AnimationInfo animationType = engine.createComponent(AnimationInfo.class);
        animationType.set(buggerComponent.state.ordinal());
        animationType.loop = true;
        bugger.add(animationType);

        AnimationComponent an = engine.createComponent(AnimationComponent.class);
        an.animations.put(animationType.state, FBirdAssetManager.getInstance().getAnimation(FBirdAssetManager.ANI_TYPES.coin));
        bugger.add(an);

        TransformationComponent tf = engine.createComponent(TransformationComponent.class);
        float y = type == LOCATION.BOTTOM ? type.getTop() + position.y : type.getTop() - position.y;
        tf.position = new Vector3(position.x, y, ZIndexDefinition.BUGGER);
        tf.rotation = type.getAngle() / MathUtils.radiansToDegrees;
        tf.size = new Vector2(1, 32 / 24f);
        bugger.add(tf);

        BodyComponent bd = engine.createComponent(BodyComponent.class);
        bd.body = this.bodyFactory.makeBoxPolyBody(tf, BodyFactory.AIR, BodyDef.BodyType.KinematicBody);
        bd.body.setTransform(tf.position.x, tf.position.y, tf.rotation);
        bodyFactory.makeAllFixturesSensors(bd.body);
        bugger.add(bd);
        bd.body.setUserData(bugger);

        engine.addEntity(bugger);
        return bugger;
    }

    public Entity createBackground() {
        Entity background = engine.createEntity();

        BackgroundComponent backgroundComponent = engine.createComponent(BackgroundComponent.class);
        background.add(backgroundComponent);

        TextureRegionComponent tr = engine.createComponent(TextureRegionComponent.class);
        TransformationComponent transform = engine.createComponent(TransformationComponent.class);

        tr.textureRegion = FBirdAssetManager.getInstance().getBackground();
        background.add(tr);

        transform.position.set(new Vector3(2 * GameDefine.GAME_SCREEN_WIDTH, GameDefine.GAME_SCREEN_HEIGHT / 2f - 1, ZIndexDefinition.BACKGROUND));
        transform.size.set(new Vector2(4 * GameDefine.GAME_SCREEN_WIDTH, GameDefine.GAME_SCREEN_HEIGHT + 2));
        background.add(transform);


        VelocityComponent vl = engine.createComponent(VelocityComponent.class);
        vl.vx = -1 / 100f;
        vl.vy = 0.0f;
        background.add(vl);

        engine.addEntity(background);

        return background;
    }

    public Entity createPlatform(Vector2 position) {
        Entity platform = engine.createEntity();

        TransformationComponent tf = engine.createComponent(TransformationComponent.class);
        tf.position.set(new Vector3(position.x / 2f, position.y, ZIndexDefinition.PLATFORM));
        tf.size.set(new Vector2(GameDefine.GAME_SCREEN_WIDTH, 1.0f));
        platform.add(tf);

        BodyComponent bd = engine.createComponent(BodyComponent.class);
        bd.body = bodyFactory.makeBoxPolyBody(tf, BodyFactory.STONE, BodyDef.BodyType.StaticBody);
        bd.body.setUserData(platform);

        engine.addEntity(platform);
        return platform;
    }


    public Entity createBullet(float posx, float posy, BulletComponent.Owner owner) {
        Entity bullet = engine.createEntity();

        BulletComponent bulletComponent = engine.createComponent(BulletComponent.class);
        bulletComponent.state = BulletComponent.BulletState.HIDE;
        bulletComponent.velocity = new Vector2(5f, 5f);
        bulletComponent.owner = owner;
        bulletComponent.isDead = false;
        bullet.add(bulletComponent);

        TransformationComponent tf = engine.createComponent(TransformationComponent.class);
        tf.position.set(new Vector3(posx + 1, posy, ZIndexDefinition.BULLET));
        tf.size.set(new Vector2(1 / 2f, 1 / 2f));
        bullet.add(tf);

        TextureRegionComponent tr = engine.createComponent(TextureRegionComponent.class);
        tr.textureRegion = FBirdAssetManager.getInstance().getTextureRegion("bullet");
        bullet.add(tr);


        BodyComponent bd = engine.createComponent(BodyComponent.class);
        bd.body = bodyFactory.makeBoxPolyBody(tf, BodyFactory.RUBBER, BodyDef.BodyType.KinematicBody);
        bodyFactory.makeAllFixturesSensors(bd.body);
        bd.body.setUserData(bullet);
        bullet.add(bd);

        engine.addEntity(bullet);
        return bullet;
    }
}
