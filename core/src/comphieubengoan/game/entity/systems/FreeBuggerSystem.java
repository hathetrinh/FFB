package comphieubengoan.game.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import comphieubengoan.game.entity.LevelFactory;
import comphieubengoan.game.entity.components.BodyComponent;
import comphieubengoan.game.entity.components.BuggerComponent;
import comphieubengoan.game.entity.components.Mapper;

public class FreeBuggerSystem extends IteratingSystem {

    private ComponentMapper<BuggerComponent> bm = Mapper.buggerComponents;
    private ComponentMapper<BodyComponent> bdm = Mapper.bodyComponents;
    private LevelFactory levelFactory;

    public FreeBuggerSystem(LevelFactory levelFactory) {
        super(Family.all(BuggerComponent.class, BodyComponent.class).get());
        this.levelFactory = levelFactory;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BuggerComponent bg = bm.get(entity);
        BodyComponent bd = bdm.get(entity);

        if (!bg.isAlive) {
            this.getEngine().removeEntity(entity);
            this.levelFactory.getWorld().destroyBody(bd.body);
        }
    }
}
