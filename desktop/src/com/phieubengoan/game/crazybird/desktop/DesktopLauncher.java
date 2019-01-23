package com.phieubengoan.game.crazybird.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.phieubengoan.game.crazybird.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 780;
		config.height = 853;
		config.title = "Trinh bro";
		new LwjglApplication(MyGdxGame.getInstance(), config);
	}
}
