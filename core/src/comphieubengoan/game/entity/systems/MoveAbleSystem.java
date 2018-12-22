package comphieubengoan.game.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import comphieubengoan.game.entity.components.Mapper;
import comphieubengoan.game.entity.components.TransformationComponent;
import comphieubengoan.game.entity.components.VelocityComponent;

public class MoveAbleSystem extends IteratingSystem {

    private ComponentMapper<TransformationComponent> pm = Mapper.transformationComponents;
    private ComponentMapper<VelocityComponent> vm = Mapper.velocityComponents;

    public MoveAbleSystem() {
        super(Family.all(TransformationComponent.class, VelocityComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformationComponent transform = pm.get(entity);
        VelocityComponent vl = vm.get(entity);

        transform.position.x += vl.getVx() * deltaTime;
        transform.position.y += vl.getVy() * deltaTime;
    }

}
