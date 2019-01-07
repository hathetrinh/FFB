package com.phieubengoan.game.crazybird.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import com.phieubengoan.game.crazybird.GameDefine;
import com.phieubengoan.game.crazybird.entity.LevelFactory;
import com.phieubengoan.game.crazybird.loader.FBirdAssetManager;
import com.phieubengoan.game.crazybird.utils.BodyFactory;

public class MapGeneratorSystem extends IteratingSystem {
    private LevelFactory levelFactory;
    private float timeLifeOfAttacker = 0.0f;
    private float timeLifeOfMoney = 0.0f;
    private float timeLifeOfBarrier = 0.0f;

    public MapGeneratorSystem(LevelFactory levelFactory) {
        super(Family.all().get());
        this.levelFactory = levelFactory;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        timeLifeOfAttacker += deltaTime;
        timeLifeOfMoney += deltaTime;
        timeLifeOfBarrier += deltaTime;

        if (timeLifeOfAttacker > 3.0f) {
            timeLifeOfAttacker = 0f;
            generateFlyEnemy();
        }

        if (timeLifeOfMoney > 1.5f) {
            timeLifeOfMoney = 0f;
            Random random = new Random();
            LevelFactory.LOCATION type = random.nextBoolean() ? LevelFactory.LOCATION.BOTTOM : LevelFactory.LOCATION.TOP;
            float posY = random.nextInt(10) + 3;
            levelFactory.createBugger(new Vector2(GameDefine.GAME_SCREEN_WIDTH + 2, posY), type);
        }

        if (timeLifeOfBarrier > 5.5f) {
            timeLifeOfBarrier = 0f;
            generateGroundEnemy();
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }

    public void generateGroundEnemy() {
        Random random = new Random();
        int kind = random.nextInt(8);
        float height;
        switch (kind) {
            case 0:
                height = 5f;
                levelFactory.createTextureEnemy(new Vector2(GameDefine.GAME_SCREEN_WIDTH + 2, 1 + height / 2), new Vector2(-2.0f, 0), new Vector2(2f, height), FBirdAssetManager.getInstance().getCactus(), LevelFactory.LOCATION.BOTTOM);
                break;
            case 1:
                height = 6f;
                levelFactory.createTextureEnemy(new Vector2(GameDefine.GAME_SCREEN_WIDTH + 2, 1 + height / 2), new Vector2(-2.0f, 0), new Vector2(2f, height), FBirdAssetManager.getInstance().getCactus(), LevelFactory.LOCATION.BOTTOM);
                break;
            case 2:
                height = 7f;
                levelFactory.createTextureEnemy(new Vector2(GameDefine.GAME_SCREEN_WIDTH + 2, 1 + height / 2), new Vector2(-2.0f, 0), new Vector2(2f, height), FBirdAssetManager.getInstance().getCactus(), LevelFactory.LOCATION.BOTTOM);
                break;
            case 3:
                height = 8f;
                levelFactory.createTextureEnemy(new Vector2(GameDefine.GAME_SCREEN_WIDTH + 2, 1 + height / 2), new Vector2(-2.0f, 0), new Vector2(2f, height), FBirdAssetManager.getInstance().getCactus(), LevelFactory.LOCATION.BOTTOM);
                break;
            case 4:
                height = 7f;
                levelFactory.createTextureEnemy(new Vector2(GameDefine.GAME_SCREEN_WIDTH + 2, 1 + height / 2), new Vector2(-2.0f, 0), new Vector2(2f, height), FBirdAssetManager.getInstance().getTree(), LevelFactory.LOCATION.BOTTOM, BodyFactory.SHAPE.TRIANGLE);
                break;
            case 5:
                height = 8f;
                levelFactory.createTextureEnemy(new Vector2(GameDefine.GAME_SCREEN_WIDTH + 2, 1 + height / 2), new Vector2(-2.0f, 0), new Vector2(2.3f, height), FBirdAssetManager.getInstance().getTree(), LevelFactory.LOCATION.BOTTOM, BodyFactory.SHAPE.TRIANGLE);
                break;
            case 6:
                height = 9f;
                levelFactory.createTextureEnemy(new Vector2(GameDefine.GAME_SCREEN_WIDTH + 2, 1 + height / 2), new Vector2(-2.0f, 0), new Vector2(2.7f, height), FBirdAssetManager.getInstance().getTree(), LevelFactory.LOCATION.BOTTOM, BodyFactory.SHAPE.TRIANGLE);
                break;
            case 7:
                height = 10f;
                levelFactory.createTextureEnemy(new Vector2(GameDefine.GAME_SCREEN_WIDTH + 2, 1 + height / 2), new Vector2(-2.0f, 0), new Vector2(3f, height), FBirdAssetManager.getInstance().getTree(), LevelFactory.LOCATION.BOTTOM, BodyFactory.SHAPE.TRIANGLE);
                break;
        }
    }

    public void generateFlyEnemy() {
        Random random = new Random();
        int kind = random.nextInt(11);
        switch (kind) {
            case 0:
                levelFactory.createAnimationEnemy(FBirdAssetManager.ANI_TYPES.spaceship, new Vector2(GameDefine.GAME_SCREEN_WIDTH + 2, 3), new Vector2(-3, 0), new Vector2(3, 2), LevelFactory.LOCATION.TOP);
                break;
            case 1:
                levelFactory.createAnimationEnemy(FBirdAssetManager.ANI_TYPES.spaceship, new Vector2(GameDefine.GAME_SCREEN_WIDTH + 2, 7), new Vector2(-3, 0), new Vector2(3, 2), LevelFactory.LOCATION.TOP);
                break;
            case 2:
                levelFactory.createAnimationEnemy(FBirdAssetManager.ANI_TYPES.spaceship, new Vector2(GameDefine.GAME_SCREEN_WIDTH + 2, 3), new Vector2(-4, 0), new Vector2(3, 2), LevelFactory.LOCATION.TOP);
                break;
            case 3:
                levelFactory.createAnimationEnemy(FBirdAssetManager.ANI_TYPES.spaceship, new Vector2(GameDefine.GAME_SCREEN_WIDTH + 2, 7), new Vector2(-4, 0), new Vector2(3, 2), LevelFactory.LOCATION.TOP);
                break;
            case 4:
                levelFactory.createAnimationEnemy(FBirdAssetManager.ANI_TYPES.spaceship, new Vector2(GameDefine.GAME_SCREEN_WIDTH + 2, 3), new Vector2(-5, 0), new Vector2(3, 2), LevelFactory.LOCATION.TOP);
                break;
            case 5:
                levelFactory.createAnimationEnemy(FBirdAssetManager.ANI_TYPES.spaceship, new Vector2(GameDefine.GAME_SCREEN_WIDTH + 2, 7), new Vector2(-5, 0), new Vector2(3, 2), LevelFactory.LOCATION.TOP);
                break;
            case 6:
                levelFactory.createAnimationEnemy(FBirdAssetManager.ANI_TYPES.spaceship, new Vector2(GameDefine.GAME_SCREEN_WIDTH + 2, 3), new Vector2(-6, 0), new Vector2(3, 2), LevelFactory.LOCATION.TOP);
                break;
            case 7:
                levelFactory.createAnimationEnemy(FBirdAssetManager.ANI_TYPES.spaceship, new Vector2(GameDefine.GAME_SCREEN_WIDTH + 2, 7), new Vector2(-6, 0), new Vector2(3, 2), LevelFactory.LOCATION.TOP);
                break;
            case 8:
                levelFactory.createTextureEnemy(new Vector2(GameDefine.GAME_SCREEN_WIDTH + 2, 6), new Vector2(-8.0f, 0), new Vector2(2f, 2f), FBirdAssetManager.getInstance().getAirShip(), LevelFactory.LOCATION.TOP);
                break;
            case 9:
                levelFactory.createTextureEnemy(new Vector2(GameDefine.GAME_SCREEN_WIDTH + 2, 9), new Vector2(-4.0f, 0), new Vector2(2f, 2f), FBirdAssetManager.getInstance().getAirShip(), LevelFactory.LOCATION.TOP);
                break;
            case 10:
                levelFactory.createTextureEnemy(new Vector2(GameDefine.GAME_SCREEN_WIDTH + 2, 10), new Vector2(-3.0f, 0), new Vector2(2f, 2f), FBirdAssetManager.getInstance().getUFO(), LevelFactory.LOCATION.TOP);
                break;
        }
    }
}
