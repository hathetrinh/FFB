package comphieubengoan.game.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;
import comphieubengoan.game.entity.components.BodyComponent;
import comphieubengoan.game.entity.components.Mapper;
import comphieubengoan.game.entity.components.TransformationComponent;

public class FreePipeSystem extends IteratingSystem {

    private ComponentMapper<TransformationComponent> tm = Mapper.transformationComponents;
    private ComponentMapper<BodyComponent> bm = Mapper.bodyComponents;
    private World world;

    public FreePipeSystem(World world) {
        super(Family.all(TransformationComponent.class, BodyComponent.class).get());
        this.world = world;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformationComponent tc = tm.get(entity);
        BodyComponent bd = bm.get(entity);

        if (tc.position.x < -tc.size.x) {
            getEngine().removeEntity(entity);
            world.destroyBody(bd.body);
        }
    }
}
