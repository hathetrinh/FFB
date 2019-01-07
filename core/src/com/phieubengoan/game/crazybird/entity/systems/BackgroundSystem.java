package com.phieubengoan.game.crazybird.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import com.phieubengoan.game.crazybird.GameDefine;
import com.phieubengoan.game.crazybird.entity.components.BackgroundComponent;
import com.phieubengoan.game.crazybird.entity.components.Mapper;
import com.phieubengoan.game.crazybird.entity.components.TransformationComponent;
import com.phieubengoan.game.crazybird.entity.components.VelocityComponent;

public class BackgroundSystem extends IteratingSystem {

    private ComponentMapper<BackgroundComponent> backgroundComponents = Mapper.backgroundComponents;
    private ComponentMapper<VelocityComponent> velocityComponents = Mapper.velocityComponents;
    private ComponentMapper<TransformationComponent> transformationComponents = Mapper.transformationComponents;

    public BackgroundSystem() {
        super(Family.all(BackgroundComponent.class, VelocityComponent.class, TransformationComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        VelocityComponent velocityComponent = velocityComponents.get(entity);
        BackgroundComponent backgroundComponent = backgroundComponents.get(entity);
        TransformationComponent transformationComponent = transformationComponents.get(entity);

        transformationComponent.position.x += velocityComponent.vx;
        transformationComponent.position.y += velocityComponent.vy;

        if (transformationComponent.position.x <= - GameDefine.GAME_SCREEN_WIDTH) {
            transformationComponent.position.x = 2 * GameDefine.GAME_SCREEN_WIDTH;
        }
    }
}
