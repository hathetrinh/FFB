/*
 * Copyright (c) 2018
 */

package comphieubengoan.game.loader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import comphieubengoan.game.GameDefine;

import java.util.HashMap;
import java.util.Map;

public class FBirdAssetManager implements Disposable {

    private static FBirdAssetManager instance;
    public AssetManager assetManager;
    private Animation countDownAnimation;
    private Map<BirdAnimationColor, Animation> birdAnimation = new HashMap<>();

    public static final String PIPE_IMAGE = "pipe-green";

    public static final String AUDIO_LOCATION = "audio/";

    public enum Audios {
        die, hit, point, swoosh, wing;

        public String getName() {
            return AUDIO_LOCATION + this.toString() + ".wav";
        }
    }


    public enum NumberImages {

        ZERO(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9);

        private int number;

        NumberImages(int number) {
            this.number = number;
        }

        public String getNumber() {
            return number + "";
        }
    }

    public enum BirdAnimationColor {

        RED("red"), YELLOW("yellow"), BLUE("blue");

        private String[] _surfixs = {"bird-upflap", "bird-midflap", "bird-downflap"};
        private String color;

        BirdAnimationColor(String color) {
            this.color = color;
        }

        public String[] getSprites() {
            String[] birdSprites = new String[3];
            int i = 0;
            for (String surfix : _surfixs) {
                birdSprites[i++] = color + surfix;
            }
            return birdSprites;
        }

        public String getColor() {
            return this.color;
        }

    }


    private FBirdAssetManager() {
        assetManager = new AssetManager();
    }

    public static FBirdAssetManager getInstance() {
        if (instance == null) {
            instance = new FBirdAssetManager();
        }
        return instance;
    }

    public void loadSounds() {
        for (Audios audio : Audios.values()) {
            this.assetManager.load(audio.getName(), Sound.class);
            this.assetManager.finishLoading();
        }
    }

    public Image getImage(String name) {
        return new Image(assetManager.get(GameDefine.gameAtlas, TextureAtlas.class).findRegion(name));
    }

    public TextureRegion getTextureRegion(String name) {
        return assetManager.get(GameDefine.gameAtlas, TextureAtlas.class).findRegion(name);
    }


    public void loadTexture() {
        this.assetManager.load(GameDefine.gameAtlas, TextureAtlas.class);
        this.assetManager.finishLoading();
    }

    public Animation getCountDownAnimation() {
        if (countDownAnimation == null) {
            TextureRegion[] countDownFrame = new TextureRegion[3];

            for (int i = 0; i < 3; i++) {
                countDownFrame[i] = assetManager.get(GameDefine.gameAtlas, TextureAtlas.class).findRegion(NumberImages.values()[i + 1].getNumber());
            }
            countDownAnimation = new Animation(1f, countDownFrame);
        }
        return countDownAnimation;
    }

    public Animation getBirdAnimation(BirdAnimationColor color) {
        if (this.birdAnimation.get(color) == null) {
            TextureRegion[] birdFrame = new TextureRegion[color.getSprites().length];
            for (int i = 0; i < color.getSprites().length; i++) {
                birdFrame[i] = assetManager.get(GameDefine.gameAtlas, TextureAtlas.class).findRegion(color.getSprites()[i]);
            }
            Animation birdAnimation = new Animation(0.5f, birdFrame);
            this.birdAnimation.put(color, birdAnimation);
        }
        return this.birdAnimation.get(color);
    }

    public void loadSkin() {
        SkinLoader.SkinParameter params = new SkinLoader.SkinParameter("skin/glassy-ui.atlas");
        this.assetManager.load(GameDefine.gameSkin, Skin.class, params);
        this.assetManager.finishLoading();
    }

    public Skin getSkin() {
        return this.assetManager.get(GameDefine.gameSkin, Skin.class);
    }

    public void dispose() {
        this.assetManager.dispose();
    }
}
