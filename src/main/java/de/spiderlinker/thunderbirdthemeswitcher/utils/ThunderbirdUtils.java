package de.spiderlinker.thunderbirdthemeswitcher.utils;

import de.spiderlinker.thunderbirdthemeswitcher.Config;
import org.ini4j.Ini;
import org.ini4j.Profile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class ThunderbirdUtils {

  private static final String PROFILE_SECTION        = "Profile";
  private static final String IS_RELATIVE            = "IsRelative";
  private static final String PATH                   = "Path";
  private static final String VALUE_PATH_IS_RELATIVE = "1";

  static {
    disableIni4jRemovesBackslashesFromFile();
  }

  private ThunderbirdUtils() {
    // Utility class
  }

  private static void disableIni4jRemovesBackslashesFromFile() {
    org.ini4j.Config.getGlobal().setEscape(false);
  }

  public static Collection<File> getProfileFolders() {
    Collection<File> profileFolders = new ArrayList<>();

    try {
      Ini iniContainingProfiles = new Ini(Config.getTBProfilesIniFileDependingOnOS());
      profileFolders.addAll(getAllProfileFolders(iniContainingProfiles));
    } catch (IOException e) {
      e.printStackTrace();
    }

    return profileFolders;
  }

  private static Collection<File> getAllProfileFolders(Ini iniContainingProfiles) {
    Collection<File> profileFolders = new ArrayList<>();

    int index = 0;
    Profile.Section profileSection;
    while ((profileSection = getProfileWithIndex(iniContainingProfiles, index++)) != null) {
      File profileFolder = getProfileFolder(profileSection);

      if (profileFolder.exists()) {
        profileFolders.add(profileFolder);
      }
    }
    return profileFolders;
  }

  private static Profile.Section getProfileWithIndex(Ini iniFile, int index) {
    return iniFile.get(PROFILE_SECTION + index);
  }

  private static File getProfileFolder(Profile.Section profileSection) {
    boolean isRelative = VALUE_PATH_IS_RELATIVE.equals(profileSection.get(IS_RELATIVE));
    String path = profileSection.get(PATH);

    return isRelative  //
        ? new File(Config.getTBFolderPathDependingOnOS(), path) //
        : new File(path);
  }

}
