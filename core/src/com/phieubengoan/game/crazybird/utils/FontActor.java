package com.phieubengoan.game.crazybird.utils;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.Disposable;
import com.phieubengoan.game.crazybird.GameDefine;

public class FontActor extends Widget implements Disposable {

    private BitmapFont font;
    private MyFont fontBuilder;
    private String text;

    public FontActor(int fontSize,  String text) {
        fontBuilder = new MyFont.MyFontBuilder(GameDefine.DEFAULT_FONT_NAME).setFontSize(fontSize).build();
        font = fontBuilder.getFont();
        this.text = text;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        font.draw(batch, text, getX(), getY());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void dispose() {
        this.font.dispose();
        this.fontBuilder.dispose();
    }
}
