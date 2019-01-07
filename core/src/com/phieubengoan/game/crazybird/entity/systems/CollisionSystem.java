package com.phieubengoan.game.crazybird.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import com.phieubengoan.game.crazybird.entity.components.AnimationInfo;
import com.phieubengoan.game.crazybird.entity.components.BuggerComponent;
import com.phieubengoan.game.crazybird.entity.components.BulletComponent;
import com.phieubengoan.game.crazybird.entity.components.CollisionComponent;
import com.phieubengoan.game.crazybird.entity.components.EnemyComponent;
import com.phieubengoan.game.crazybird.entity.components.Mapper;
import com.phieubengoan.game.crazybird.entity.components.PlayerComponent;

public class CollisionSystem extends IteratingSystem {

    ComponentMapper<CollisionComponent> collisionComponents = Mapper.collisionComponents;
    ComponentMapper<PlayerComponent> playerComponents = Mapper.playerComponents;
    ComponentMapper<AnimationInfo> animationInfoComponents = Mapper.animationInfoComponents;

    public CollisionSystem() {
        super(Family.all(CollisionComponent.class, PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CollisionComponent collisionComponent = collisionComponents.get(entity);
        PlayerComponent playerComponent = playerComponents.get(entity);
        AnimationInfo animationInfo = animationInfoComponents.get(entity);

        if (collisionComponent.collisionEntity != null && playerComponent != null) {
            Entity collisionEntity = collisionComponent.collisionEntity;
            if (collisionEntity != null) {
                if (collisionEntity.getComponent(EnemyComponent.class) != null) {
//                    animationInfo.loop = false;
                    playerComponent.die();
                    animationInfo.state = playerComponent.state.ordinal();
                } else if (collisionEntity.getComponent(BuggerComponent.class) != null) {
                    playerComponent.point++;
                    BuggerComponent buggerComponent = collisionEntity.getComponent(BuggerComponent.class);
                    buggerComponent.die();
                } else if (collisionEntity.getComponent(BulletComponent.class) != null) {
//                    animationInfo.loop = false;
                    playerComponent.die();
                    animationInfo.state = playerComponent.state.ordinal();
                } else {
                    animationInfo.loop = false;
                    playerComponent.die();
                    playerComponent.isGround = true;
                }
            }
            collisionComponent.reset();
        }
    }
}
