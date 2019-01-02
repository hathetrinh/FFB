package comphieubengoan.game.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import comphieubengoan.game.GameDefine;
import comphieubengoan.game.entity.LevelFactory;

public class MapGeneratorSystem extends IteratingSystem {
    private LevelFactory levelFactory;
    private float timeLife = 0.0f;

    public MapGeneratorSystem(LevelFactory levelFactory) {
        super(Family.all().get());
        this.levelFactory = levelFactory;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        timeLife += deltaTime;

        if (timeLife > 2.5f) {
            timeLife = 0.0f;
            Random random = new Random();
            LevelFactory.PIPE type = random.nextBoolean() ? LevelFactory.PIPE.UP : LevelFactory.PIPE.DOWN;
            float posY = random.nextInt(7);
            levelFactory.createPipe(new Vector2(GameDefine.GAME_SCREEN_WIDTH + 2, posY), type);
            levelFactory.createBugger(new Vector2(GameDefine.GAME_SCREEN_WIDTH + 2, posY + 10), type);
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}
