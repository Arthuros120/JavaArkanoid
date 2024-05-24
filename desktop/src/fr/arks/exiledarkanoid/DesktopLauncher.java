package fr.arks.exiledarkanoid;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import fr.arks.exiledarkanoid.gameplay.ExiledArkanoid;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("ExiledArkanoid");
		config.setWindowedMode(480, 800);
		config.useVsync(true);
		new Lwjgl3Application(new ExiledArkanoid(), config);
	}
}
