package com.phieubengoan.game.crazybird.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;

import com.phieubengoan.game.crazybird.entity.components.BodyComponent;
import com.phieubengoan.game.crazybird.entity.components.Mapper;
import com.phieubengoan.game.crazybird.entity.components.TransformationComponent;

public class FreeEnemySystem extends IteratingSystem {

    private ComponentMapper<TransformationComponent> transformationComponents = Mapper.transformationComponents;
    private ComponentMapper<BodyComponent> bodyComponents = Mapper.bodyComponents;
    private World world;

    public FreeEnemySystem(World world) {
        super(Family.all(TransformationComponent.class, BodyComponent.class).get());
        this.world = world;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformationComponent transformationComponent = transformationComponents.get(entity);
        BodyComponent bodyComponent = bodyComponents.get(entity);
        if (transformationComponent.position.x < -transformationComponent.size.x) {
            getEngine().removeEntity(entity);
            world.destroyBody(bodyComponent.body);
        }
    }
}
