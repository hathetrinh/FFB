package comphieubengoan.game.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

public class TransformationComponent implements Component, Pool.Poolable {

    public Vector3 position = new Vector3(0f, 0f, 0f);
    public Vector2 scale = new Vector2(1.0f, 1.0f);
    public float rotation = 0.0f;
    public boolean isHidden = false;

    @Override
    public void reset() {
        isHidden = false;
        rotation = 0.0f;
        scale.set(1.0f, 1.0f);
    }
}
