package comphieubengoan.game.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Pool;

public class PlayerComponent implements Component, Pool.Poolable {

    public OrthographicCamera cam;
    public boolean isDead = false;
    public boolean isGround = false;
    public int point = 0;

    @Override
    public void reset() {
        isDead = false;
        point = 0;
        cam = null;
        isGround = false;
    }
}
