package com.phieubengoan.game.crazybird.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import com.phieubengoan.game.crazybird.entity.LevelFactory;
import com.phieubengoan.game.crazybird.entity.components.BodyComponent;
import com.phieubengoan.game.crazybird.entity.components.BulletComponent;
import com.phieubengoan.game.crazybird.entity.components.Mapper;

public class BulletSystem extends IteratingSystem {

    private ComponentMapper<BulletComponent> bulletComponents = Mapper.bulletComponents;
    private ComponentMapper<BodyComponent> bodyComponents = Mapper.bodyComponents;
    private LevelFactory levelFactory;

    public BulletSystem(LevelFactory levelFactory) {
        super(Family.all(BulletComponent.class, BodyComponent.class).get());
        this.levelFactory = levelFactory;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BulletComponent bulletComponent = bulletComponents.get(entity);
        BodyComponent bodyComponent = bodyComponents.get(entity);
        bodyComponent.body.setLinearVelocity(bulletComponent.velocity);

        if (bulletComponent.isDead) {
            this.getEngine().removeEntity(entity);
            this.levelFactory.getWorld().destroyBody(bodyComponent.body);
        }
    }
}
