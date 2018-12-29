package comphieubengoan.game.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import comphieubengoan.game.entity.components.*;

public class AnimatorSystem extends IteratingSystem {

    private ComponentMapper<AnimationComponent> animationComponentComponents = Mapper.animationComponents;
    private ComponentMapper<GameAnimationType> stateComponentComponents = Mapper.stateComponentComponents;
    private ComponentMapper<TextureRegionComponent> textureRegionComponents = Mapper.textureRegionComponents;

    public AnimatorSystem() {
        super(Family.all(AnimationComponent.class, TextureRegionComponent.class, GameAnimationType.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationComponent animationComponent = animationComponentComponents.get(entity);
        GameAnimationType gameAnimationType = stateComponentComponents.get(entity);

        if (animationComponent.animations.containsKey(gameAnimationType.state)) {
            TextureRegionComponent textureRegion = textureRegionComponents.get(entity);
            textureRegion.textureRegion = (TextureRegion) animationComponent.animations.get(gameAnimationType.get()).getKeyFrame(gameAnimationType.time, gameAnimationType.loop);
        }

        gameAnimationType.time += deltaTime;
    }
}
