package de.spiderlinker.thunderbirdthemeswitcher.core;

import de.spiderlinker.thunderbirdthemeswitcher.Config;
import de.spiderlinker.thunderbirdthemeswitcher.utils.ThunderbirdUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ThemeUninstaller {

  private static final Logger LOGGER = LoggerFactory.getLogger(ThemeUninstaller.class);

  public static void uninstallTheme() {
    LOGGER.info("Uninstalling theme...");
    deleteDirectory(new File(Config.DIR_TEMP));
    ThunderbirdUtils.getProfileFolders()
        .forEach(ThemeUninstaller::deleteThemeDirectoryOf);
  }

  private static void deleteThemeDirectoryOf(File profileFolder) {
    deleteDirectory(new File(profileFolder, Config.THEME_FOLDER_NAME));
  }

  private static void deleteDirectory(File directory) {
    FileUtils.deleteQuietly(directory);
  }

}
