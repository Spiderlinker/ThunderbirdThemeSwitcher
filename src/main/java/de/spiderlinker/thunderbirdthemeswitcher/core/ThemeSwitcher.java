package de.spiderlinker.thunderbirdthemeswitcher.core;

import de.spiderlinker.thunderbirdthemeswitcher.Config;
import de.spiderlinker.thunderbirdthemeswitcher.utils.ThunderbirdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ThemeSwitcher {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ThemeSwitcher.class);
	
	public static void switchThemeTo(String theme) {
		LOGGER.info("Switching theme to " + theme);
		ThunderbirdUtils.getProfileFolders().forEach(profileFolder -> {
			try {
				switchThemeInFileTo(new File(profileFolder, Config.FILE_THEME_CONFIGURATION).toPath(), theme);
			} catch (IOException e) {
				LOGGER.error("Could not switch theme!", e);
			}
		});
	}

	private static void switchThemeInFileTo(Path pathToThemeFile, String theme) throws IOException {
		String newLine = Config.THEME_CONFIGURATION_FIND_PREFIX + theme + Config.THEME_CONFIGURATION_FIND_SUFFIX;

		List<String> lines = Files.readAllLines(pathToThemeFile);
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			if (line.startsWith(Config.THEME_CONFIGURATION_FIND_PREFIX)) {
				lines.set(i, newLine);
				LOGGER.info("Theme switched in " + pathToThemeFile + " to " + theme);
				break;
			}
		}

		Files.write(pathToThemeFile, lines);
	}

}
