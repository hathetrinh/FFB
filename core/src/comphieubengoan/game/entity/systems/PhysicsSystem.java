package comphieubengoan.game.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import comphieubengoan.game.entity.components.BodyComponent;
import comphieubengoan.game.entity.components.Mapper;
import comphieubengoan.game.entity.components.TransformationComponent;

public class PhysicsSystem extends IteratingSystem {

    private World world;
    private Array<Entity> entities;
    private ComponentMapper<TransformationComponent> ts = Mapper.transformationComponents;
    private ComponentMapper<BodyComponent> bd = Mapper.bodyComponents;

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
        world.step(1/60f, 6, 2);

        for (Entity entity : entities) {
            TransformationComponent tfm = ts.get(entity);
            BodyComponent bodyComp = bd.get(entity);
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
