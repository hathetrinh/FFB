package utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import comphieubengoan.game.entity.components.TransformationComponent;

public class BodyFactory {

    public static final int STEEL = 0;
    public static final int WOOD = 1;
    public static final int RUBBER = 2;
    public static final int STONE = 3;
    public static final int AIR = 4;

    public World world;

    public BodyFactory(World world) {
        this.world = world;
    }

    public FixtureDef makeFixture(int material, Shape shape) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        switch (material) {
            case STEEL:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0.3f;
                fixtureDef.restitution = 0.1f;
                break;
            case WOOD:
                fixtureDef.density = 0.5f;
                fixtureDef.friction = 0.7f;
                fixtureDef.restitution = 0.3f;
                break;
            case RUBBER:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0f;
                fixtureDef.restitution = 1f;
                break;
            case STONE:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0.5f;
                fixtureDef.restitution = 0f;
                break;
            case AIR:
                fixtureDef.density = 0.0f;
                fixtureDef.friction = 0.0f;
                fixtureDef.restitution = 0f;
                break;
            default:
                fixtureDef.density = 7f;
                fixtureDef.friction = 0.5f;
                fixtureDef.restitution = 0.3f;

        }

        return fixtureDef;
    }

    public Body makeBoxPolyBody(float posx, float posy, float width, float height, int material, BodyDef.BodyType bodyType, boolean fixedRotation) {
        return makeBoxPolyBody(posx, posy, width, height, new Vector2(0, 0), 0, material, bodyType, fixedRotation);
    }

    public Body makeBoxPolyBody(float posx, float posy, float width, float height, Vector2 centre, float angle, int material, BodyDef.BodyType bodyType, boolean fixedRotation) {
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = bodyType;
        boxBodyDef.position.x = posx;
        boxBodyDef.position.y = posy;
        boxBodyDef.fixedRotation = fixedRotation;

        Body boxBody = world.createBody(boxBodyDef);
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(width / 2, height / 2, centre, angle);
        boxBody.createFixture(makeFixture(material, poly));
        poly.dispose();
        return boxBody;
    }

    public Body makeBoxPolyBody(TransformationComponent transform,Vector2 centre, float angle, int material, BodyDef.BodyType bodyType) {
        return makeBoxPolyBody(transform.position.x, transform.position.y, transform.size.x, transform.size.y, centre, angle, material, bodyType, false);
    }

    public Body makeBoxPolyBody(TransformationComponent transform, int material, BodyDef.BodyType bodyType) {
        return makeBoxPolyBody(transform.position.x, transform.position.y, transform.size.x, transform.size.y, material, bodyType, false);
    }

    public void makeAllFixturesSensors(Body body) {
        for (Fixture fix : body.getFixtureList()) {
            fix.setSensor(true);
        }
    }
}
