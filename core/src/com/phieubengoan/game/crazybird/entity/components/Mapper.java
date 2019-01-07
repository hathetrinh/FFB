package com.phieubengoan.game.crazybird.entity.components;

import com.badlogic.ashley.core.ComponentMapper;

public class Mapper {
    public static final ComponentMapper<AnimationComponent> animationComponents = ComponentMapper.getFor(AnimationComponent.class);
    public static final ComponentMapper<TransformationComponent> transformationComponents = ComponentMapper.getFor(TransformationComponent.class);
    public static final ComponentMapper<TextureRegionComponent> textureRegionComponents = ComponentMapper.getFor(TextureRegionComponent.class);
    public static final ComponentMapper<VelocityComponent> velocityComponents = ComponentMapper.getFor(VelocityComponent.class);
    public static final ComponentMapper<AnimationInfo> animationInfoComponents = ComponentMapper.getFor(AnimationInfo.class);
    public static final ComponentMapper<BodyComponent> bodyComponents = ComponentMapper.getFor(BodyComponent.class);
    public static final ComponentMapper<PlayerComponent> playerComponents = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<CollisionComponent> collisionComponents = ComponentMapper.getFor(CollisionComponent.class);
    public static final ComponentMapper<BuggerComponent> buggerComponents = ComponentMapper.getFor(BuggerComponent.class);
    public static final ComponentMapper<BulletComponent> bulletComponents = ComponentMapper.getFor(BulletComponent.class);
    public static final ComponentMapper<EnemyComponent> enemyComponents = ComponentMapper.getFor(EnemyComponent.class);
    public static final ComponentMapper<BackgroundComponent> backgroundComponents = ComponentMapper.getFor(BackgroundComponent.class);
}
