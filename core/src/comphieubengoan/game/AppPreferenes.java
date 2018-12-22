package comphieubengoan.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import comphieubengoan.game.loader.FBirdAssetManager;

public class AppPreferenes {
    private static final String PRE_MUSIC = "music";
    private static final String PRE_SOUND = "sound";
    private static final String PRE_MUSIC_ENABLE = "music.enable";
    private static final String PRE_SOUND_ENABLE = "sound.enable";
    private static final String PRES_APP = "MyGdxGame";
    private static final String PRE_ACTOR_COLOR = "actor_color";

    private static AppPreferenes instance = new AppPreferenes();

    private AppPreferenes() {

    }

    public static AppPreferenes getInstance() {
        return instance;
    }

    protected Preferences getPrefers() {
        return Gdx.app.getPreferences(PRES_APP);
    }

    public boolean isSoundEnable() {
        return this.getPrefers().getBoolean(PRE_SOUND_ENABLE, true);
    }

    public void setSoundEnable(boolean isEnable) {
        this.getPrefers().putBoolean(PRE_SOUND_ENABLE, isEnable);
        this.getPrefers().flush();
    }

    public boolean isMusicEnable() {
        return this.getPrefers().getBoolean(PRE_MUSIC_ENABLE, true);
    }

    public void setMusicEnable(boolean isEnable) {
        this.getPrefers().putBoolean(PRE_MUSIC_ENABLE, isEnable);
        this.getPrefers().flush();
    }

    public void setMusicVolume(float volume) {
        this.getPrefers().putFloat(PRE_MUSIC, volume);
        this.getPrefers().flush();
    }

    public float getMusicVolume() {
        return this.getPrefers().getFloat(PRE_MUSIC, 0.5f);
    }

    public void setSoundVolume(Float volume) {
        this.getPrefers().putFloat(PRE_SOUND, volume);
        this.getPrefers().flush();
    }

    public float getSoundVolume() {
        return this.getPrefers().getFloat(PRE_SOUND, 0.5f);
    }

    public String getActorColor() {
        return this.getPrefers().getString(PRE_ACTOR_COLOR, FBirdAssetManager.BirdAnimationColor.BLUE.getColor());
    }

    public void setActorColor(String color) {
        this.getPrefers().putString(PRE_ACTOR_COLOR, color);
        this.getPrefers().flush();
    }
}
