package de.spiderlinker.thunderbirdthemeswitcher.core;

import de.spiderlinker.thunderbirdthemeswitcher.Config;
import de.spiderlinker.thunderbirdthemeswitcher.utils.ThunderbirdUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.PrefixFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ThemeSearcher {

  private static final String THEME_FILE_REGEX = "[a-zA-Z0-9]+\\.css";

  private ThemeSearcher() {
    // Utility class
  }

  public static Map<String, String> getThemesAndImages() {
    Map<String, String> themesAndImages = new HashMap<>();

    ThunderbirdUtils.getProfileFolders().forEach(profileFolder -> getThemesOfProfile(profileFolder.getAbsolutePath()).forEach(theme -> themesAndImages.put(theme, null)));
    themesAndImages.keySet().forEach(theme -> themesAndImages.put(theme, getImageForTheme(theme)));

    return themesAndImages;
  }

  private static List<String> getThemesOfProfile(String profileFolder) {
    File themeFolder = new File(profileFolder, Config.RELATIVE_DIR_AVAILABLE_THEMES);

    if (!fileExistsAndIsDirectory(themeFolder)) {
      return new ArrayList<>();
    }

    return FileUtils.listFiles( //
        themeFolder, //
        new RegexFileFilter(THEME_FILE_REGEX), null) //
        .stream() //
        .map(File::getName) //
        .map(FilenameUtils::removeExtension) //
        .collect(Collectors.toList()); //
  }

  private static String getImageForTheme(String theme) {
    String imagePath = null;

    for (File profileFolder : ThunderbirdUtils.getProfileFolders()) {
      File screenshotFolder = new File(profileFolder, Config.RELATIVE_DIR_THEME_SCREENSHOTS);
      if (fileExistsAndIsDirectory(screenshotFolder)) {
        File imageFile = FileUtils.listFiles(screenshotFolder, new PrefixFileFilter(theme, IOCase.INSENSITIVE), null).stream().findFirst().orElse(null);
        if (imageFile != null) {
          imagePath = imageFile.getAbsolutePath();
          break;
        }
      }
    }

    return imagePath;
  }

  private static boolean fileExistsAndIsDirectory(File file) {
    return file != null && file.exists() && file.isDirectory();
  }

}
