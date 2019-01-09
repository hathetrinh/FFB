/*
package com.phieubengoan.game.crazybird.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Disposable;

public class MyFont implements Disposable {

    private FreeTypeFontGenerator generator;
    private FreeTypeFontParameter parameter;

    public MyFont(MyFontBuilder myFontBuilder) {
        generator = new FreeTypeFontGenerator(Gdx.files.internal(myFontBuilder.fontName));
        parameter = new FreeTypeFontParameter();
        parameter.size = myFontBuilder.fontSize;
    }

    public static class MyFontBuilder {
        private String fontName;
        private int fontSize;
        private boolean mono;
        private Color color = Color.WHITE;
        private float gamma = 1.8f;
        private int renderCount = 2;
        private float borderWidth = 0;
        private Color borderColor = Color.BLACK;
        private boolean borderStraight = false;
        private float borderGamma = 1.8f;
        private int shadowOffsetX = 0;
        private int shadowOffsetY = 0;
        private Color shadowColor = new Color(0, 0, 0, 0.75f);
        private int spaceX, spaceY;
        private boolean kerning = true;
        private PixmapPacker packer = null;
        private boolean flip = false;
        private boolean genMipMaps = false;
        private Texture.TextureFilter minFilter = Texture.TextureFilter.Nearest;
        private Texture.TextureFilter magFilter = Texture.TextureFilter.Nearest;
        private boolean incremental;

        public void setColor(Color color) {
            this.color = color;
        }

        public void setGamma(float gamma) {
            this.gamma = gamma;
        }

        public void setRenderCount(int renderCount) {
            this.renderCount = renderCount;
        }

        public void setBorderWidth(float borderWidth) {
            this.borderWidth = borderWidth;
        }

        public void setBorderColor(Color borderColor) {
            this.borderColor = borderColor;
        }

        public void setBorderStraight(boolean borderStraight) {
            this.borderStraight = borderStraight;
        }

        public void setBorderGamma(float borderGamma) {
            this.borderGamma = borderGamma;
        }

        public void setShadowOffsetX(int shadowOffsetX) {
            this.shadowOffsetX = shadowOffsetX;
        }

        public void setShadowOffsetY(int shadowOffsetY) {
            this.shadowOffsetY = shadowOffsetY;
        }

        public void setShadowColor(Color shadowColor) {
            this.shadowColor = shadowColor;
        }

        public void setSpaceX(int spaceX) {
            this.spaceX = spaceX;
        }

        public void setSpaceY(int spaceY) {
            this.spaceY = spaceY;
        }

        public void setKerning(boolean kerning) {
            this.kerning = kerning;
        }

        public void setPacker(PixmapPacker packer) {
            this.packer = packer;
        }

        public void setFlip(boolean flip) {
            this.flip = flip;
        }

        public void setGenMipMaps(boolean genMipMaps) {
            this.genMipMaps = genMipMaps;
        }

        public void setMinFilter(Texture.TextureFilter minFilter) {
            this.minFilter = minFilter;
        }

        public void setMagFilter(Texture.TextureFilter magFilter) {
            this.magFilter = magFilter;
        }

        public void setIncremental(boolean incremental) {
            this.incremental = incremental;
        }

        public void setMono(boolean mono) {
            this.mono = mono;
        }

        public String getFontName() {
            return fontName;
        }

        public MyFontBuilder(String fontName) {
            this.fontName = fontName;

        }

        public MyFontBuilder setFontSize(int fontSize) {
            this.fontSize = fontSize;
            return this;
        }

        public MyFontBuilder setFontName(String fontName) {
            this.fontName = fontName;
            return this;
        }

        public MyFont build() {
            return new MyFont(this);
        }
    }

    @Override
    public void dispose() {
        generator.dispose();
    }

    public BitmapFont getFont() {
        return generator.generateFont(parameter);
    }
}
*/
