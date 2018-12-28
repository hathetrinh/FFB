package comphieubengoan.game.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import comphieubengoan.game.entity.components.*;
import comphieubengoan.game.loader.FBirdAssetManager;
import lombok.extern.java.Log;

@Log
public class CollisionSystem extends IteratingSystem {

    ComponentMapper<CollisionComponent> pCollisionMapper = Mapper.collisionComponents;
    ComponentMapper<PlayerComponent> pPlayerMapper = Mapper.playerComponents;
    ComponentMapper<GameObjectComponent> pGameObjectMapper = Mapper.gameObjectComponents;

    public CollisionSystem() {
        super(Family.all(CollisionComponent.class, PlayerComponent.class, GameObjectComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CollisionComponent collisionComponent = pCollisionMapper.get(entity);
        GameObjectComponent mainGameObject = pGameObjectMapper.get(entity);
        PlayerComponent player = pPlayerMapper.get(entity);

        if (collisionComponent.collisionEntity != null && mainGameObject.gameObject == GameObjectComponent.PLAYER) {
            Entity collisionEntity = collisionComponent.collisionEntity;
            if (collisionEntity != null) {
                GameObjectComponent collisionGameObject = collisionEntity.getComponent(GameObjectComponent.class);
                if (collisionGameObject.gameObject == GameObjectComponent.PIPE) {
                    player.isDead = true;
                    FBirdAssetManager.getInstance().playMusic(FBirdAssetManager.Audios.hit);
                } else if (collisionGameObject.gameObject == GameObjectComponent.BUGGER) {
                    player.point++;
                    BuggerComponent bc = collisionEntity.getComponent(BuggerComponent.class);
                    bc.isAlive = false;
                    FBirdAssetManager.getInstance().playMusic(FBirdAssetManager.Audios.point);
                } else if (collisionGameObject.gameObject == GameObjectComponent.PLATFORM) {
                    player.isGround = true;
                    player.isDead = true;
                    FBirdAssetManager.getInstance().playMusic(FBirdAssetManager.Audios.die);
                }
            }
            collisionComponent.reset();
        }
    }
}
