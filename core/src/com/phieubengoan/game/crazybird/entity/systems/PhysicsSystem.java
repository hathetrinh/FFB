package com.phieubengoan.game.crazybird.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import com.phieubengoan.game.crazybird.entity.components.BodyComponent;
import com.phieubengoan.game.crazybird.entity.components.Mapper;
import com.phieubengoan.game.crazybird.entity.components.TransformationComponent;

public class PhysicsSystem extends IteratingSystem {

    private World world;
    private Array<Entity> entities;
    private ComponentMapper<TransformationComponent> transformationComponents = Mapper.transformationComponents;
    private ComponentMapper<BodyComponent> bodyComponents = Mapper.bodyComponents;

    public PhysicsSystem(World world) {
        super(Family.all(TransformationComponent.class, BodyComponent.class).get());
        this.world = world;
        entities = new Array<>();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        this.entities.add(entity);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        for (Entity entity : entities) {
            TransformationComponent tfm = transformationComponents.get(entity);
            BodyComponent bodyComp = bodyComponents.get(entity);
            Vector2 position = bodyComp.body.getPosition();
            tfm.position.x = position.x;
            tfm.position.y = position.y;
            tfm.rotation = bodyComp.body.getAngle() * MathUtils.radiansToDegrees;
            if (bodyComp.isDead) {
                world.destroyBody(bodyComp.body);
                getEngine().removeEntity(entity);
            }
        }
        entities.clear();
    }
}
