package comphieubengoan.game.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import comphieubengoan.game.controller.KeyBoardInputController;
import comphieubengoan.game.entity.components.Mapper;
import comphieubengoan.game.entity.components.VelocityComponent;

public class TouchSystem extends IteratingSystem {

    public ComponentMapper<VelocityComponent> velocityComponentMapper = Mapper.velocityComponents;

    private KeyBoardInputController controller;

    public TouchSystem(KeyBoardInputController controller) {
        super(Family.all(VelocityComponent.class).get());
        this.controller = controller;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        VelocityComponent vl = velocityComponentMapper.get(entity);
        if (controller.isMouse1Down) {
            vl.setVy(50f);
        }
        if (!controller.isMouse1Down) {
            vl.setVy(0f);
        }
    }
}
