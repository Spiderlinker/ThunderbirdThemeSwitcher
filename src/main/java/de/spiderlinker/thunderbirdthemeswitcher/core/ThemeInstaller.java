package de.spiderlinker.thunderbirdthemeswitcher.core;

import de.spiderlinker.thunderbirdthemeswitcher.Config;
import de.spiderlinker.thunderbirdthemeswitcher.utils.ThunderbirdUtils;
import de.spiderlinker.thunderbirdthemeswitcher.utils.Unzipper;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class ThemeInstaller {

  private static final Logger LOGGER = LoggerFactory.getLogger(ThemeInstaller.class);

  public static void installTheme() throws IOException {
    checkFolderStructure();
    downloadTheme();
    unzipDownloadedTheme();
    renameUnzippedFolder();
    copyUnzippedThemeToProfiles();

    deleteDownloadAndUnzipLocation();
  }

  private static void checkFolderStructure() {
    new File(Config.DIR_TEMP).mkdirs();
    new File(Config.DIR_TEMP_UNZIP).mkdirs();
  }

  private static void downloadTheme() throws IOException {
    LOGGER.info("Downloading theme from: {}", Config.URL_THEME_DOWNLOAD);
    ThemeDownloader.downloadTheme(Config.URL_THEME_DOWNLOAD, Config.FILE_TEMP_DOWNLOAD);
  }

  private static void unzipDownloadedTheme() throws IOException {
    LOGGER.info("Unzipping theme...");
    Unzipper.unzip(Config.FILE_TEMP_DOWNLOAD, Config.DIR_TEMP_UNZIP);
  }

  private static void renameUnzippedFolder() {
    File fileToRename = new File(Config.DIR_TEMP_UNZIP).listFiles()[0];
    File newFileName = new File(Config.DIR_UNZIP_THEME);
    if (!fileToRename.renameTo(newFileName)) {
      LOGGER.error("Could not rename unzipped folder! Further actions will fail! {} !=> {}", fileToRename, newFileName);
    }
  }

  private static void copyUnzippedThemeToProfiles() {
    LOGGER.info("Installing theme...");
    ThunderbirdUtils.getProfileFolders().forEach(ThemeInstaller::copyThemeToProfile);
  }

  private static void copyThemeToProfile(File profileFolder) {
    File unzippedTheme = new File(Config.DIR_UNZIP_THEME);
    File destinationFile = new File(profileFolder, Config.THEME_FOLDER_NAME);

    try {
      FileUtils.copyDirectory(unzippedTheme, destinationFile);
    } catch (IOException e) {
      LOGGER.error("Could not copy unzipped theme to profile folder: " + profileFolder, e);
    }

  }

  private static void deleteDownloadAndUnzipLocation() throws IOException {
    FileUtils.deleteDirectory(new File(Config.DIR_TEMP));
  }

}
