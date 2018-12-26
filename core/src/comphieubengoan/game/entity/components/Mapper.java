package comphieubengoan.game.entity.components;

import com.badlogic.ashley.core.ComponentMapper;

public class Mapper {
    public static final ComponentMapper<AnimationComponent> animationComponents = ComponentMapper.getFor(AnimationComponent.class);
    public static final ComponentMapper<TransformationComponent> transformationComponents = ComponentMapper.getFor(TransformationComponent.class);
    public static final ComponentMapper<TextureRegionComponent> textureRegionComponents = ComponentMapper.getFor(TextureRegionComponent.class);
    public static final ComponentMapper<VelocityComponent> velocityComponents = ComponentMapper.getFor(VelocityComponent.class);
    public static final ComponentMapper<StateComponent> stateComponentComponents = ComponentMapper.getFor(StateComponent.class);
    public static final ComponentMapper<GameObjectComponent> gameObjectComponents = ComponentMapper.getFor(GameObjectComponent.class);
    public static final ComponentMapper<BodyComponent> bodyComponents = ComponentMapper.getFor(BodyComponent.class);
    public static final ComponentMapper<PlayerComponent> playerComponents = ComponentMapper.getFor(PlayerComponent.class);
}
