package com.phieubengoan.game.crazybird.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import com.phieubengoan.game.crazybird.entity.components.Mapper;
import com.phieubengoan.game.crazybird.entity.components.TextureRegionComponent;
import com.phieubengoan.game.crazybird.entity.components.TransformationComponent;
import com.phieubengoan.game.crazybird.utils.ZComparator;

import java.util.Comparator;

public class RenderSystem extends SortedIteratingSystem {

    private OrthographicCamera cam;
    private SpriteBatch spriteBatch;
    private Array<Entity> renderQueue;
    private Comparator<Entity> comparator;

    private ComponentMapper<TextureRegionComponent> textureRegionComponentComponent = Mapper.textureRegionComponents;
    private ComponentMapper<TransformationComponent> transformationComponents = Mapper.transformationComponents;

    public RenderSystem(OrthographicCamera cam, SpriteBatch spriteBatch) {
        super(Family.all(TransformationComponent.class, TextureRegionComponent.class).get(), new ZComparator());
        this.cam = cam;
        this.spriteBatch = spriteBatch;
        this.comparator = new ZComparator();
        this.renderQueue = new Array<>();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        this.renderQueue.add(entity);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        cam.update();
        renderQueue.sort(comparator);
        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.enableBlending();
        spriteBatch.begin();

        for (Entity entity : renderQueue) {
            TransformationComponent tf = transformationComponents.get(entity);
            TextureRegionComponent tr = textureRegionComponentComponent.get(entity);
            float originX = tf.size.x / 2f;
            float originY = tf.size.y / 2f;
            if (!tf.isHidden && null != tr.textureRegion) {
                spriteBatch.draw(tr.textureRegion, tf.position.x - originX, tf.position.y - originY, originX, originY, tf.size.x, tf.size.y, tf.scale.x, tf.scale.y, tf.rotation);
            }
        }

        spriteBatch.end();
        renderQueue.clear();
    }
}
