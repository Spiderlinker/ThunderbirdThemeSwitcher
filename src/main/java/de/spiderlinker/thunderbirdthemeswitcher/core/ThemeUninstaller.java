package de.spiderlinker.thunderbirdthemeswitcher.core;

import java.io.File;
import java.io.IOException;

import de.spiderlinker.thunderbirdthemeswitcher.Config;
import de.spiderlinker.thunderbirdthemeswitcher.utils.ThunderbirdUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThemeUninstaller {

	private static final Logger LOGGER = LoggerFactory.getLogger(ThemeUninstaller.class);

	public static void uninstallTheme() {
		LOGGER.info("Uninstalling theme...");
		deleteDirectory(new File(Config.DIR_TEMP));
		ThunderbirdUtils.getProfileFolders()
				.forEach(profileFolder -> deleteDirectory(new File(profileFolder, Config.THEME_FOLDER_NAME)));
	}

	private static void deleteDirectory(File directory) {
		try {
			FileUtils.deleteDirectory(directory);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
