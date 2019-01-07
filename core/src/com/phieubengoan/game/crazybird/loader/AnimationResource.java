package com.phieubengoan.game.crazybird.loader;

public class AnimationResource {
    public FBirdAssetManager.ANI_TYPES name;
    public String imageName;
    public int numImage;
    public String location;
    public String atlasName;
    public float time;

    public AnimationResource(FBirdAssetManager.ANI_TYPES name, String imageName, int numImage, String location, String atlasName, float time) {
        this.name = name;
        this.imageName = imageName;
        this.numImage = numImage;
        this.location = location;
        this.atlasName = atlasName + ".atlas";
        this.time = time;
    }

    public String getImageName(int i) {
        String suffix = i > 9 ? i + "" : "0" + i;
        return imageName + suffix;
    }

}
