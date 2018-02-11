package de.spiderlinker.thunderbirdthemeswitcher.core;

import java.io.File;
import java.io.IOException;

import de.spiderlinker.thunderbirdthemeswitcher.Config;
import de.spiderlinker.thunderbirdthemeswitcher.utils.ThunderbirdUtils;
import org.apache.commons.io.FileUtils;

public class ThemeUninstaller {

	public static void uninstallTheme() {
		System.out.println("Uninstalling theme...");
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
