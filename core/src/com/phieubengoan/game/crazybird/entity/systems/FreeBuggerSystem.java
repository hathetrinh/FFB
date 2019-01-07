package com.phieubengoan.game.crazybird.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.phieubengoan.game.crazybird.entity.LevelFactory;
import com.phieubengoan.game.crazybird.entity.components.BodyComponent;
import com.phieubengoan.game.crazybird.entity.components.BuggerComponent;
import com.phieubengoan.game.crazybird.entity.components.Mapper;

public class FreeBuggerSystem extends IteratingSystem {

    private ComponentMapper<BuggerComponent> buggerComponents = Mapper.buggerComponents;
    private ComponentMapper<BodyComponent> bodyComponents = Mapper.bodyComponents;
    private LevelFactory levelFactory;

    public FreeBuggerSystem(LevelFactory levelFactory) {
        super(Family.all(BuggerComponent.class, BodyComponent.class).get());
        this.levelFactory = levelFactory;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BuggerComponent buggerComponent = buggerComponents.get(entity);
        BodyComponent bodyComponent = bodyComponents.get(entity);

        if (!buggerComponent.isAlive) {
            this.getEngine().removeEntity(entity);
            this.levelFactory.getWorld().destroyBody(bodyComponent.body);
        }
    }
}
