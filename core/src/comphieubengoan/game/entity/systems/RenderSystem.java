package comphieubengoan.game.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import comphieubengoan.game.GameDefine;
import comphieubengoan.game.entity.components.*;
import utils.ZComparator;

import java.util.Comparator;

public class RenderSystem extends SortedIteratingSystem {

    private OrthographicCamera cam;
    private SpriteBatch spriteBatch;
    private Array<Entity> renderQueue = new Array<>();
    private Comparator<Entity> comparator;

    private ComponentMapper<TextureRegionComponent> textureRegionComponentComponent = Mapper.textureRegionComponents;
    private ComponentMapper<TransformationComponent> transformationComponents = Mapper.transformationComponents;

    public RenderSystem(OrthographicCamera cam, SpriteBatch spriteBatch) {
        super(Family.all(TransformationComponent.class, TextureRegionComponent.class).get(), new ZComparator());
        this.cam = cam;
        this.spriteBatch = spriteBatch;
        this.comparator = new ZComparator();
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
            TransformationComponent transform = transformationComponents.get(entity);
            TextureRegionComponent textureRegion = textureRegionComponentComponent.get(entity);
            if (!transform.isHidden) {
                float width = textureRegion.getTextureRegion().getRegionWidth()/GameDefine.PPM;
                float height = textureRegion.getTextureRegion().getRegionHeight()/GameDefine.PPM;
                spriteBatch.draw(textureRegion.getTextureRegion(),
                        transform.position.x,
                        transform.position.y,
                        0,
                        0,
                        width,
                        height,
                        transform.scale.x,
                        transform.scale.y,
                        transform.rotation);
            }
        }

        spriteBatch.end();
    }

}
