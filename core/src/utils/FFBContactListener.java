package utils;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import comphieubengoan.game.entity.components.CollisionComponent;

public class FFBContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa.getBody().getUserData() instanceof Entity && fb.getBody().getUserData() instanceof Entity) {
            Entity ea = (Entity) fa.getBody().getUserData();
            Entity eb = (Entity) fb.getBody().getUserData();
            updateEntityCollision(ea, eb);
        }
    }

    public void updateEntityCollision(Entity ea, Entity eb) {
        CollisionComponent ca = ea.getComponent(CollisionComponent.class);
        CollisionComponent cb = eb.getComponent(CollisionComponent.class);

        if (ca != null) {
            ca.collisionEntity = eb;
        } else if (cb != null) {
            cb.collisionEntity = ea;
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
