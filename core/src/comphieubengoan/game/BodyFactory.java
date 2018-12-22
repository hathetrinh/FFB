package comphieubengoan.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class BodyFactory {

    private static BodyFactory instance = new BodyFactory();
    public World world;

    private BodyFactory() {
        world = new World(new Vector2(0,-9.8f), true);
    }

    public static BodyFactory getInstance() {
        return instance;
    }

}
