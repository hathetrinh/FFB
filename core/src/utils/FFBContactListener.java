package utils;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import comphieubengoan.game.MyGdxGame;
import comphieubengoan.game.entity.components.GameObjectComponent;
import comphieubengoan.game.entity.components.PlayerComponent;
import lombok.extern.java.Log;

@Log
public class FFBContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa.getBody().getUserData() instanceof Entity) {

        }
        if (fa.getBody().getUserData() instanceof Entity && fb.getBody().getUserData() instanceof Entity) {
            Entity ea = (Entity) fa.getBody().getUserData();
            Entity eb = (Entity) fb.getBody().getUserData();
            doContactAction(ea, eb);
        }
    }

    public void doContactAction(Entity ea, Entity eb) {
        GameObjectComponent objectA = ea.getComponent(GameObjectComponent.class);
        GameObjectComponent objectB = eb.getComponent(GameObjectComponent.class);

        if (objectA.gameObject == GameObjectComponent.PLAYER && objectB.gameObject == GameObjectComponent.PIPE) {
            PlayerComponent pl = ea.getComponent(PlayerComponent.class);
            pl.isDead = true;
        } else if (objectA.gameObject == GameObjectComponent.PIPE && objectB.gameObject == GameObjectComponent.PLAYER) {
            PlayerComponent pl = eb.getComponent(PlayerComponent.class);
            pl.isDead = true;
        } else if (objectA.gameObject == GameObjectComponent.PLAYER && objectB.gameObject == GameObjectComponent.POINT_ZONE) {
            PlayerComponent pl = ea.getComponent(PlayerComponent.class);
            pl.point++;
        } else if (objectA.gameObject == GameObjectComponent.POINT_ZONE && objectB.gameObject == GameObjectComponent.PLAYER) {
            PlayerComponent pl = eb.getComponent(PlayerComponent.class);
            pl.point++;
        }

    }

    @Override
    public void endContact(Contact contact) {
        log.info("End Contact");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
