package com.phieubengoan.game.crazybird.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.phieubengoan.game.crazybird.entity.components.TransformationComponent;

public class BodyFactory {

    public static final int STEEL = 0;
    public static final int WOOD = 1;
    public static final int RUBBER = 2;
    public static final int STONE = 3;
    public static final int AIR = 4;

    public World world;

    public enum SHAPE {
        RECTANGLE,
        TRIANGLE,
        CIRCLE
    }

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

    public Body makeBoxPolyBody(TransformationComponent transform, Vector2 centre, float angle, int material, BodyDef.BodyType bodyType) {
        return makeBoxPolyBody(transform.position.x, transform.position.y, transform.size.x, transform.size.y, centre, angle, material, bodyType, false);
    }

    public Body makeBoxPolyBody(TransformationComponent transform, int material, BodyDef.BodyType bodyType) {
        return makeBoxPolyBody(transform.position.x, transform.position.y, transform.size.x, transform.size.y, material, bodyType, false);
    }

    public Body makeShapeBody(TransformationComponent transform, int material, BodyDef.BodyType bodyType, SHAPE shape) {
        switch (shape) {
            case CIRCLE:
                return makeCirclePolyBody(transform, material, bodyType);
            case TRIANGLE:
                return makeTrianglePolyBody(transform, material, bodyType);
            case RECTANGLE:
                return makeBoxPolyBody(transform, material, bodyType);
            default:
                return makeBoxPolyBody(transform, material, bodyType);
        }
    }

    public Body makeTrianglePolyBody(TransformationComponent transform, int material, BodyDef.BodyType bodyType) {
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = bodyType;
        boxBodyDef.position.x = transform.position.x;
        boxBodyDef.position.y = transform.position.y;

        Body boxBody = world.createBody(boxBodyDef);
        PolygonShape shape = createTriangle(transform);
        boxBody.createFixture(makeFixture(material, shape));
        shape.dispose();
        return boxBody;
    }

    private PolygonShape createTriangle(TransformationComponent transform) {
        Vector2[] vertices = new Vector2[3];

        vertices[0] = new Vector2(0, transform.size.y / 2);
        vertices[1] = new Vector2(-transform.size.x / 2, -transform.size.y / 2);
        vertices[2] = new Vector2(transform.size.x / 2, -transform.size.y / 2);
        PolygonShape shape = new PolygonShape();
        shape.set(vertices);
        return shape;
    }

    public Body makeCirclePolyBody(TransformationComponent transform, int material, BodyDef.BodyType bodyType) {
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = bodyType;
        boxBodyDef.position.x = transform.position.x;
        boxBodyDef.position.y = transform.position.y;

        Body boxBody = world.createBody(boxBodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(transform.size.x / 2);
        boxBody.createFixture(makeFixture(material, circleShape));
        circleShape.dispose();
        return boxBody;
    }

    public void makeAllFixturesSensors(Body body) {
        for (Fixture fix : body.getFixtureList()) {
            fix.setSensor(true);
        }
    }
}
