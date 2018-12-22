package comphieubengoan.game.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import comphieubengoan.game.entity.components.*;

public class AnimatorSystem extends IteratingSystem {

    private ComponentMapper<AnimationComponent> animationComponentComponents = Mapper.animationComponents;
    private ComponentMapper<StateComponent> stateComponentComponents = Mapper.stateComponentComponents;
    private ComponentMapper<TextureRegionComponent> textureRegionComponents = Mapper.textureRegionComponents;

    public AnimatorSystem() {
        super(Family.all(AnimationComponent.class, TextureRegionComponent.class, StateComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationComponent animationComponent = animationComponentComponents.get(entity);
        StateComponent stateComponent = stateComponentComponents.get(entity);

        if (animationComponent.animations.containsKey(stateComponent.state)) {
            TextureRegionComponent textureRegion = textureRegionComponents.get(entity);
            textureRegion.setTextureRegion(animationComponent.animations.get(stateComponent.get()).getKeyFrame(stateComponent.time, stateComponent.loop));
        }

        stateComponent.time += deltaTime;
    }
}
