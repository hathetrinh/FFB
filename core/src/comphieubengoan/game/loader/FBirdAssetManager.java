/*
 * Copyright (c) 2018
 */

package comphieubengoan.game.loader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

import comphieubengoan.game.AppPreferences;
import comphieubengoan.game.GameDefine;

import java.util.HashMap;
import java.util.Map;

public class FBirdAssetManager implements Disposable {

    private static FBirdAssetManager instance;
    public AssetManager assetManager;
    private Animation countDownAnimation;
    private Animation coinAnimation;
    private Map<BirdAnimationColor, Animation> birdAnimation = new HashMap<>();

    public static final String PIPE_IMAGE = "pipe-green";

    public static final String AUDIO_LOCATION = "audio/";

    public static String[] COIN_TEXTURE = new String[]{"coin1", "coin2", "coin3", "coin4", "coin5", "coin6"};

    public enum Audios {
        die,
        hit,
        point,
        swoosh,
        wing;

        public String getName() {
            return AUDIO_LOCATION + this.toString() + ".wav";
        }
    }

    public enum NumberImages {

        ZERO(0),
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9);

        private int number;

        NumberImages(int number) {
            this.number = number;
        }

        public String getNumber() {
            return number + "";
        }
    }

    public enum BirdAnimationColor {

        RED("red"),
        YELLOW("yellow"),
        BLUE("blue");

        private String[] _suffixes = {"bird-upflap", "bird-midflap", "bird-downflap"};
        private String color;

        BirdAnimationColor(String color) {
            this.color = color;
        }

        public String[] getSprites() {
            String[] birdSprites = new String[3];
            int i = 0;
            for (String suffix : _suffixes) {
                birdSprites[i++] = color + suffix;
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

    public void loadMusics() {
        for (Audios audio : Audios.values()) {
            this.assetManager.load(audio.getName(), Music.class);
            this.assetManager.finishLoading();
        }
    }

    public void playMusic(Audios audios) {
        getMusic(audios).play();
    }

    public Music getMusic(Audios audios) {
        boolean isMusicEnable = AppPreferences.getInstance().isMusicEnable();
        Music music = assetManager.get(audios.getName(), Music.class);
        music.setVolume(isMusicEnable ? AppPreferences.getInstance().getMusicVolume() : 0);

        return music;
    }

    public Image getImage(String name) {
        return new Image(assetManager.get(GameDefine.GAME_ATLAS, TextureAtlas.class).findRegion(name));
    }

    public TextureRegion getTextureRegion(String name) {
        return assetManager.get(GameDefine.GAME_ATLAS, TextureAtlas.class).findRegion(name);
    }


    public void loadTexture() {
        this.assetManager.load(GameDefine.GAME_ATLAS, TextureAtlas.class);
        this.assetManager.finishLoading();
    }

    public Animation getCountDownAnimation() {
        if (countDownAnimation == null) {
            TextureRegion[] countDownFrame = new TextureRegion[3];

            for (int i = 0; i < 3; i++) {
                countDownFrame[i] = assetManager.get(GameDefine.GAME_ATLAS, TextureAtlas.class).findRegion(NumberImages.values()[i + 1].getNumber());
            }
            countDownAnimation = new Animation(1f, countDownFrame);
        }
        return countDownAnimation;
    }

    public Animation getBirdAnimation(BirdAnimationColor color) {
        if (this.birdAnimation.get(color) == null) {
            TextureRegion[] birdFrame = new TextureRegion[color.getSprites().length];
            for (int i = 0; i < color.getSprites().length; i++) {
                birdFrame[i] = assetManager.get(GameDefine.GAME_ATLAS, TextureAtlas.class).findRegion(color.getSprites()[i]);
            }
            Animation birdAnimation = new Animation(0.1f, birdFrame);
            this.birdAnimation.put(color, birdAnimation);
        }
        return this.birdAnimation.get(color);
    }

    public Animation getCoinAnimation() {
        if (this.coinAnimation != null) {
            return this.coinAnimation;
        }

        TextureRegion[] coinFrame = new TextureRegion[6];
        for (int i = 0; i < 6; i++) {
            coinFrame[i] = assetManager.get(GameDefine.GAME_ATLAS, TextureAtlas.class).findRegion(COIN_TEXTURE[i]);
        }
        coinAnimation = new Animation(0.5f, coinFrame);
        return this.coinAnimation;
    }

    public void loadSkin() {
        SkinLoader.SkinParameter params = new SkinLoader.SkinParameter("skin/glassy-ui.atlas");
        this.assetManager.load(GameDefine.GAME_SKIN, Skin.class, params);
        this.assetManager.finishLoading();
    }

    public Skin getSkin() {
        return this.assetManager.get(GameDefine.GAME_SKIN, Skin.class);
    }

    public void dispose() {
        this.assetManager.dispose();
    }
}
