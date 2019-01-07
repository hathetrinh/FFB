package com.phieubengoan.game.crazybird.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class AnimationActor extends Actor {
    private Animation animation;
    private TextureRegion textureRegion;
    private float time = 0f;
    private boolean looping;
    private float ppm;

    public AnimationActor(Animation animation) {
        this(animation, false);
    }

    public AnimationActor(Animation animation, boolean looping) {
       this(animation, looping, 1);
    }

    public AnimationActor(Animation animation, boolean looping, float ppm) {
        this.animation = animation;
        this.looping = looping;
        this.ppm = ppm;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        this.time += Gdx.graphics.getDeltaTime();
        textureRegion = (TextureRegion) animation.getKeyFrame(this.time, looping);
        batch.draw(textureRegion, getX(), getY(), this.getWidth() / ppm, this.getHeight() / ppm);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public boolean isAnimationFinished() {
        return animation.isAnimationFinished(time);
    }
}
