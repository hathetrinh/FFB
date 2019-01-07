package com.phieubengoan.game.crazybird.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

import com.phieubengoan.game.crazybird.entity.components.BodyComponent;
import com.phieubengoan.game.crazybird.entity.components.Mapper;
import com.phieubengoan.game.crazybird.entity.components.PlayerComponent;
import com.phieubengoan.game.crazybird.entity.components.VelocityComponent;

public class MoveAbleSystem extends IteratingSystem {

    private ComponentMapper<VelocityComponent> velocityComponents = Mapper.velocityComponents;
    private ComponentMapper<BodyComponent> bodyComponents = Mapper.bodyComponents;

    public MoveAbleSystem() {
        super(Family.all(VelocityComponent.class, BodyComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (entity.getComponent(PlayerComponent.class) != null) {
            return;
        }

        VelocityComponent velocityComponent = velocityComponents.get(entity);
        BodyComponent bodyComponent = bodyComponents.get(entity);
        bodyComponent.body.setLinearVelocity(new Vector2(velocityComponent.vx, velocityComponent.vy));
    }

}
