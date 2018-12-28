package comphieubengoan.game.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import comphieubengoan.game.entity.components.BodyComponent;
import comphieubengoan.game.entity.components.GameObjectComponent;
import comphieubengoan.game.entity.components.Mapper;
import comphieubengoan.game.entity.components.VelocityComponent;

public class MoveAbleSystem extends IteratingSystem {

    private ComponentMapper<VelocityComponent> vm = Mapper.velocityComponents;
    private ComponentMapper<BodyComponent> bm = Mapper.bodyComponents;
    private ComponentMapper<GameObjectComponent> gm = Mapper.gameObjectComponents;

    public MoveAbleSystem() {
        super(Family.all(VelocityComponent.class, BodyComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        VelocityComponent vl = vm.get(entity);
        BodyComponent bd = bm.get(entity);
        GameObjectComponent go = gm.get(entity);

        if (go.gameObject != GameObjectComponent.PLAYER) {
            bd.body.setLinearVelocity(new Vector2(vl.vx, vl.vy));
        }
    }

}
