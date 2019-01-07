package com.phieubengoan.game.crazybird.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Array;

import com.phieubengoan.game.crazybird.entity.LevelFactory;
import com.phieubengoan.game.crazybird.entity.components.AnimationInfo;
import com.phieubengoan.game.crazybird.entity.components.BulletComponent;
import com.phieubengoan.game.crazybird.entity.components.EnemyComponent;
import com.phieubengoan.game.crazybird.entity.components.Mapper;
import com.phieubengoan.game.crazybird.entity.components.TransformationComponent;

public class EnemySystem extends IteratingSystem {

    private LevelFactory levelFactory;
    private ComponentMapper<EnemyComponent> enemyComponents = Mapper.enemyComponents;
    private ComponentMapper<AnimationInfo> animationInfoComponents = Mapper.animationInfoComponents;
    private ComponentMapper<TransformationComponent> transformationComponents = Mapper.transformationComponents;
    private Array<Entity> entities;

    public EnemySystem(LevelFactory levelFactory) {
        super(Family.all(EnemyComponent.class, AnimationInfo.class, TransformationComponent.class).get());
        this.levelFactory = levelFactory;
        entities = new Array<>();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for (Entity entity : entities) {
            EnemyComponent enemyComponent = enemyComponents.get(entity);
            AnimationInfo animationInfo = animationInfoComponents.get(entity);
            TransformationComponent transformationComponent = transformationComponents.get(entity);
        }
        entities.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        entities.add(entity);
    }
}
