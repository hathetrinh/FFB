package com.phieubengoan.game.crazybird.loader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.phieubengoan.game.crazybird.AppPreferences;
import com.phieubengoan.game.crazybird.utils.MyButton;

import java.util.HashMap;
import java.util.Map;

public class FBirdAssetManager implements Disposable {

    private static FBirdAssetManager instance;
    public AssetManager assetManager;

    public enum ANI_TYPES {
        bird,
        spaceship,
        countdown,
        coin,
        explosion
    }

    private Map<ANI_TYPES, Animation> animationMap = new HashMap<>();

    private AnimationResource angelAnimationResource = new AnimationResource(ANI_TYPES.bird, "player", 4, "sprites", "crazy_angel", 1 / 4f);
    private AnimationResource attackerAnimationResource = new AnimationResource(ANI_TYPES.spaceship, "spaceship", 2, "sprites", "crazy_angel", 1 / 10f);
    private AnimationResource countDownAnimationResource = new AnimationResource(ANI_TYPES.countdown, "number", 3, "sprites", "crazy_angel", 1f);
    private AnimationResource coinAnimationResource = new AnimationResource(ANI_TYPES.coin, "coin3d", 6, "sprites", "crazy_angel", 1 / 2f);
    private AnimationResource deadAnimationResource = new AnimationResource(ANI_TYPES.explosion, "explosion", 4, "sprites", "crazy_angel", 1/4f);

    private MyButton btnGPlay, btnExit, btnLeaderBoard, btnShare, btnGMenu, btnMenu, btnPlay, btnPause;

    private static final String GAME_TEXTURE_ITEMS = "sprites/crazy_angel.atlas";

    private static final String GAME_SKIN = "skin/plain-james-ui";

    public enum Audios {
        die,
        hit,
        point,
        swoosh,
        wing;

        public String getName() {
            return "audio/" + this.toString() + ".wav";
        }
    }

    private FBirdAssetManager() {
        assetManager = new AssetManager();
    }

    public void load() {
        this.loadMusics();
        this.loadSkin();
        this.loadAtlas();
        this.loadAnimation();
    }

    public Animation getAnimation(ANI_TYPES types) {
        return this.animationMap.get(types);
    }

    public static FBirdAssetManager getInstance() {
        if (instance == null) {
            instance = new FBirdAssetManager();
        }
        return instance;
    }

    private void loadMusics() {
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
        return new Image(assetManager.get(GAME_TEXTURE_ITEMS, TextureAtlas.class).findRegion(name));
    }

    public TextureRegion getTextureRegion(String name) {
        return assetManager.get(GAME_TEXTURE_ITEMS, TextureAtlas.class).findRegion(name);
    }

    public TextureRegion getNumber(int number) {
        return assetManager.get(GAME_TEXTURE_ITEMS, TextureAtlas.class).findRegion("number0" + number);
    }

    private void loadAtlas() {
        this.assetManager.load(GAME_TEXTURE_ITEMS, TextureAtlas.class);
        this.assetManager.finishLoading();
    }

    private void loadAnimation() {
        for (ANI_TYPES type : ANI_TYPES.values()) {
            if (this.animationMap.get(type) == null) {
                AnimationResource animationResource;

                switch (type) {
                    case coin:
                        animationResource = coinAnimationResource;
                        break;
                    case bird:
                        animationResource = angelAnimationResource;
                        break;
                    case spaceship:
                        animationResource = attackerAnimationResource;
                        break;
                    case countdown:
                        animationResource = countDownAnimationResource;
                        TextureRegion[] angelTexture = new TextureRegion[animationResource.numImage];
                        int j = 0;
                        for (int i = animationResource.numImage; i >0; i--) {
                            angelTexture[j++] = assetManager.get("sprites/" + animationResource.atlasName, TextureAtlas.class).findRegion(animationResource.getImageName(i));
                        }
                        this.animationMap.put(type, new Animation(animationResource.time, angelTexture));
                        break;
                    case explosion:
                        animationResource = deadAnimationResource;
                        break;
                    default:
                        animationResource = angelAnimationResource;
                }
                if (type != ANI_TYPES.countdown) {
                    TextureRegion[] angelTexture = new TextureRegion[animationResource.numImage];
                    for (int i = 0; i < animationResource.numImage; i++) {
                        angelTexture[i] = assetManager.get("sprites/" + animationResource.atlasName, TextureAtlas.class).findRegion(animationResource.getImageName(i + 1));
                    }
                    this.animationMap.put(type, new Animation(animationResource.time, angelTexture));
                }
            }
        }
    }

    private void loadSkin() {
        SkinLoader.SkinParameter params = new SkinLoader.SkinParameter(GAME_SKIN + ".atlas");
        this.assetManager.load(GAME_SKIN + ".json", Skin.class, params);
        this.assetManager.finishLoading();
    }

    public Skin getSkin() {
        return this.assetManager.get(GAME_SKIN + ".json", Skin.class);
    }

    @Override
    public void dispose() {
        this.assetManager.dispose();
        this.animationMap.clear();
        this.animationMap = null;
        instance = null;
    }

    public TextureRegion getWood1() {
        return this.getTextureRegion("wood01");
    }

    public TextureRegion getWood2() {
        return this.getTextureRegion("wood02");
    }

    public TextureRegion getWood3() {
        return this.getTextureRegion("wood03");
    }

    public TextureRegion getCactus() {
        return this.getTextureRegion("cactus");
    }

    public TextureRegion getAirShip() {
        return this.getTextureRegion("airship");
    }

    public TextureRegion getBackground() {
        return this.getTextureRegion("background");
    }

    public TextureRegion getUFO() {
        return this.getTextureRegion("ufo");
    }

    public TextureRegion getTree() {
        return this.getTextureRegion("tree");
    }

    public MyButton getButtonPlay() {
        if (btnPlay == null) {
            btnPlay = new MyButton(getTextureRegion("btn_play_clicked"), getTextureRegion("btn_play_clicked"));
        }
        return btnPlay;
    }

    public MyButton getButtonExit() {
        if (btnExit == null) {
            btnExit = new MyButton(getTextureRegion("btn_exit_normal"), getTextureRegion("btn_exit_normal"));
        }
        return btnExit;
    }

    public MyButton getButtonLeaderBoard() {
        if (btnLeaderBoard == null) {
            btnLeaderBoard = new MyButton(getTextureRegion("btn_gameplay_normal"), getTextureRegion("btn_gameplay_clicked"));
        }
        return btnLeaderBoard;
    }

    public MyButton getButtonShare() {
        if (btnShare == null) {
            btnShare = new MyButton(getTextureRegion("btn_share_normal"), getTextureRegion("btn_share_clicked"));
        }
        return btnShare;
    }

    public MyButton getButtonMenu() {
        if (btnMenu == null) {
            btnMenu = new MyButton(getTextureRegion("btn_menu_normal"), getTextureRegion("btn_menu_normal"));
        }
        return btnMenu;
    }

    public MyButton getButtonPause() {
        if (btnPause == null) {
            btnPause = new MyButton(getTextureRegion("btn_pause_normal"), getTextureRegion("btn_pause_clicked"));
        }
        return btnPause;
    }

    public MyButton getButtonGPlay() {
        if (btnGPlay == null) {
            btnGPlay = new MyButton(getTextureRegion("btn_g_play_normal"), getTextureRegion("btn_g_play_normal"));
        }
        return btnGPlay;
    }

    public MyButton getButtonGMenu() {
        if (btnGMenu == null) {
            btnGMenu = new MyButton(getTextureRegion("btn_g_menu_normal"), getTextureRegion("btn_g_menu_normal"));
        }
        return btnGMenu;
    }
}
