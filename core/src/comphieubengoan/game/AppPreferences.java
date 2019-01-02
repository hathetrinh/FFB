package comphieubengoan.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Disposable;

import comphieubengoan.game.loader.FBirdAssetManager;

public class AppPreferences implements Disposable {
    private static final String PRE_MUSIC = "music";
    private static final String PRE_MUSIC_ENABLE = "music.enable";
    private static final String PRES_APP = "MyGdxGame";
    private static final String PRE_ACTOR_COLOR = "actor_color";
    private static final String MAX_POINT = "max_point";

    private Preferences preferences;

    private static AppPreferences instance;

    private AppPreferences() {

    }

    public static AppPreferences getInstance() {
        if (instance == null) {
            instance = new AppPreferences();
        }
        return instance;
    }

    protected Preferences getPrefers() {
        if (preferences == null)
            preferences = Gdx.app.getPreferences(PRES_APP);
        return preferences;
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

    public String getActorColor() {
        return this.getPrefers().getString(PRE_ACTOR_COLOR, FBirdAssetManager.BirdAnimationColor.BLUE.getColor());
    }

    public void setActorColor(String color) {
        this.getPrefers().putString(PRE_ACTOR_COLOR, color);
        this.getPrefers().flush();
    }

    public int getMaxPoint() {
        return this.getPrefers().getInteger(MAX_POINT, 0);
    }

    public void setMaxPoint(int point) {
        this.getPrefers().putInteger(MAX_POINT, point);
        this.getPrefers().flush();
    }

    @Override
    public void dispose() {
        instance = null;
        Gdx.app.log("trinhha", AppPreferences.class.getName());
    }
}
