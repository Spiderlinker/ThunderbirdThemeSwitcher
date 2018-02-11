package de.spiderlinker.thunderbirdthemeswitcher.core;

import de.spiderlinker.thunderbirdthemeswitcher.Config;
import de.spiderlinker.thunderbirdthemeswitcher.utils.ThunderbirdUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ThemeSwitcher {

	public static void switchThemeTo(String theme) {
		ThunderbirdUtils.getProfileFolders().forEach(profileFolder -> {
			try {
				switchThemeInFileTo(new File(profileFolder, Config.FILE_THEME_CONFIGURATION).toPath(), theme);
			} catch (IOException e) {
				e.printStackTrace();
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
				System.out.println("Theme switched in " + pathToThemeFile + " to " + theme);
				break;
			}
		}

		Files.write(pathToThemeFile, lines);
	}

}
