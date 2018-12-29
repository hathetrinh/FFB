package comphieubengoan.game.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.audio.Music;

import comphieubengoan.game.GameDefine;
import comphieubengoan.game.controller.KeyBoardInputController;
import comphieubengoan.game.entity.components.BodyComponent;
import comphieubengoan.game.entity.components.Mapper;
import comphieubengoan.game.entity.components.PlayerComponent;
import comphieubengoan.game.loader.FBirdAssetManager;

public class PlayerSystem extends IteratingSystem {

    private ComponentMapper<BodyComponent> bm = Mapper.bodyComponents;
    private ComponentMapper<PlayerComponent> pm = Mapper.playerComponents;
    private Music jump;

    private KeyBoardInputController controller;

    public PlayerSystem(KeyBoardInputController controller) {
        super(Family.all(BodyComponent.class, PlayerComponent.class).get());
        this.controller = controller;
        this.jump = FBirdAssetManager.getInstance().getMusic(FBirdAssetManager.Audios.wing);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        BodyComponent bc = bm.get(entity);
        PlayerComponent pc = pm.get(entity);
        if (controller.isMouse1Down && !pc.isDead) {
            bc.body.applyLinearImpulse(0, GameDefine.JUMP_SPEED * bc.body.getMass(), bc.body.getWorldCenter().x, bc.body.getWorldCenter().y, true);
            if (!jump.isPlaying()) {
                jump.play();
            }
        }
    }
}
