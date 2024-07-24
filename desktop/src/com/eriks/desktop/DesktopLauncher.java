package com.eriks.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.eriks.core.GameController;
import com.eriks.core.TaskController;
import com.eriks.core.ui.UIController;
import com.eriks.core.util.LoggerConfig;
import com.eriks.desktop.db.DesktopPackageRepository;
import com.eriks.desktop.db.DesktopParamRepository;
import com.eriks.desktop.db.DesktopCardRepository;
import com.eriks.desktop.db.DesktopTaskRepository;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DesktopLauncher {
	private static Logger logger = LoggerConfig.getLogger();

	public static void main (String[] arg) {
		try {
			Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
			config.setTitle("CSAlbum");
			config.setWindowedMode(1920, 1080);
			config.setResizable(false);
			config.useVsync(true);
			config.setWindowIcon("icon128.png", "icon32.png", "icon16.png");

			GameController.INSTANCE.setPackageRepository(new DesktopPackageRepository());
			GameController.INSTANCE.setParamRepository(new DesktopParamRepository());
			GameController.INSTANCE.setCardRepository(new DesktopCardRepository());
			TaskController.INSTANCE.setTaskRepository(new DesktopTaskRepository());

			GameController.INSTANCE.startup();

			new Lwjgl3Application(UIController.INSTANCE, config);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "An error occurred", e);
			e.printStackTrace();
		}
	}
}
