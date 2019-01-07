package com.phieubengoan.game.crazybird.entity.components;

public class ZIndexDefinition {
    public static int BACKGROUND = 100;
    public static int PLATFORM = BACKGROUND--;
    public static int ENEMY = PLATFORM--;
    public static int BULLET = ENEMY--;
    public static int BUGGER = BULLET--;
    public static int PLAYER = BUGGER--;
}

