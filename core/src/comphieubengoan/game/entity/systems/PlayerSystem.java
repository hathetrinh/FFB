package comphieubengoan.game.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import comphieubengoan.game.controller.KeyBoardInputController;
import comphieubengoan.game.entity.components.BodyComponent;
import comphieubengoan.game.entity.components.GameObjectComponent;
import comphieubengoan.game.entity.components.Mapper;
import comphieubengoan.game.entity.components.TransformationComponent;
import lombok.extern.java.Log;

@Log
public class PlayerSystem extends IteratingSystem {

    private ComponentMapper<GameObjectComponent> gameObjectComponents = Mapper.gameObjectComponents;
    private ComponentMapper<BodyComponent> bodyComponentComponents = Mapper.bodyComponents;

    private KeyBoardInputController controller;

    public PlayerSystem(KeyBoardInputController controller) {
        super(Family.all(GameObjectComponent.class, BodyComponent.class, TransformationComponent.class).get());
        this.controller = controller;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (gameObjectComponents.get(entity).gameObject != GameObjectComponent.PLAYER) {
            return;
        }

        BodyComponent b2body = bodyComponentComponents.get(entity);

        if (controller.isMouse1Down) {
            b2body.body.applyLinearImpulse(0, 0.75f * b2body.body.getMass(), b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
        }
    }
}
