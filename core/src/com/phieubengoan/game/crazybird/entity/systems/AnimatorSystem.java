package com.phieubengoan.game.crazybird.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import com.phieubengoan.game.crazybird.entity.components.AnimationComponent;
import com.phieubengoan.game.crazybird.entity.components.AnimationInfo;
import com.phieubengoan.game.crazybird.entity.components.Mapper;
import com.phieubengoan.game.crazybird.entity.components.TextureRegionComponent;

public class AnimatorSystem extends IteratingSystem {

    private ComponentMapper<AnimationComponent> animationComponents = Mapper.animationComponents;
    private ComponentMapper<AnimationInfo> stateComponents = Mapper.animationInfoComponents;
    private ComponentMapper<TextureRegionComponent> textureRegionComponents = Mapper.textureRegionComponents;
    private Array<Entity> entities;

    public AnimatorSystem() {
        super(Family.all(AnimationComponent.class, TextureRegionComponent.class, AnimationInfo.class).get());
        entities = new Array<>();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for (Entity entity : entities) {
            AnimationComponent animationComponent = animationComponents.get(entity);
            AnimationInfo animationInfo = stateComponents.get(entity);
            Animation animation = animationComponent.animations.get(animationInfo.get());
            if (animation != null) {
                TextureRegionComponent textureRegion = textureRegionComponents.get(entity);
                textureRegion.textureRegion = (TextureRegion) animation.getKeyFrame(animationInfo.time, animationInfo.loop);
                animationInfo.time += deltaTime;
            }
        }
        entities.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        entities.add(entity);
    }
}
