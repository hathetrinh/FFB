package comphieubengoan.game.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class GameObjectComponent implements Component, Pool.Poolable {

    public static final int UNDEFINE = 0;
    public static final int BACK_GROUND = 1;
    public static final int PIPE = 2;
    public static final int PLAYER = 3;
    public static final int POINT_ZONE = 4;

    public int gameObject = UNDEFINE;

    @Override
    public void reset() {
        gameObject = UNDEFINE;
    }
}
