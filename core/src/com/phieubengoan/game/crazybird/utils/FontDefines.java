package com.phieubengoan.game.crazybird.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Disposable;

public class FontDefines implements Disposable {

    public BitmapFont fontMenuItem, fontMenuTitle;

    private static FontDefines instance;

    public static FontDefines getInstance() {
        if (instance == null) {
            instance = new FontDefines();
        }
        return instance;
    }

    private FontDefines() {
        fontMenuItem = new BitmapFont(Gdx.files.internal("font/f_menu_item.fnt"), Gdx.files.internal("font/f_menu_item.png"), false, true);
        fontMenuTitle = new BitmapFont(Gdx.files.internal("font/f_menu_title.fnt"), Gdx.files.internal("font/f_menu_title.png"), false, true);
    }

    @Override
    public void dispose() {
        this.fontMenuItem.dispose();
    }
}
