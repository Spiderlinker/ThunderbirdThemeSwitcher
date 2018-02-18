package de.spiderlinker.thunderbirdthemeswitcher;

import com.sun.javafx.PlatformUtil;

import java.io.File;

public final class Config {

  private Config() {
    // config class
  }

  public static final String URL_THEME = "https://github.com/spymastermatt/thunderbird-monterail";
  public static final String URL_THEME_DOWNLOAD = URL_THEME + "/archive/master.zip";
  public static final String THEME_FOLDER_NAME = "chrome";

  public static final String USER_HOME = System.getProperty("user.home");
  public static final String DIR_TB_PROFILES_WIN = USER_HOME + "\\Appdata\\Roaming\\Thunderbird\\Profiles\\";
  public static final String DIR_TB_PROFILES_UNIX = USER_HOME + "/.thunderbird/";
  public static final String DIR_TB_PROFILES_MAC = USER_HOME + "/Library/Thunderbird/Profiles/";

  public static final String DIR_PROFILES = getTBProfilePathDependingOnOS();

  public static final String DIR_TEMP = DIR_PROFILES + "TBThemeSwitcher";

  public static final String DIR_TEMP_UNZIP = DIR_TEMP + File.separator + "unzip";
  public static final String DIR_UNZIP_THEME = DIR_TEMP_UNZIP + File.separator + THEME_FOLDER_NAME;

  public static final String FILE_TEMP_DOWNLOAD = DIR_TEMP + File.separator + "theme.zip";
  public static final String FILE_THEME_CONFIGURATION = THEME_FOLDER_NAME + File.separator + "userChrome.css";

  public static final String RELATIVE_DIR_AVAILABLE_THEMES = THEME_FOLDER_NAME + File.separator + "themes";
  public static final String RELATIVE_DIR_THEME_SCREENSHOTS = THEME_FOLDER_NAME + File.separator + "screenshots";

  public static final String THEME_CONFIGURATION_FIND_PREFIX = "@import \"themes/";
  public static final String THEME_CONFIGURATION_FIND_SUFFIX = ".css\";";

  public static String getTBProfilePathDependingOnOS() {
    return PlatformUtil.isWindows() ? DIR_TB_PROFILES_WIN //
        : PlatformUtil.isUnix() ? DIR_TB_PROFILES_UNIX //
        : PlatformUtil.isMac() ? DIR_TB_PROFILES_MAC //
        : "";

  }

}
