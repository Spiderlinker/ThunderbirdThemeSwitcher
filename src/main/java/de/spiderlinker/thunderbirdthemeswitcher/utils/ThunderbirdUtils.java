package de.spiderlinker.thunderbirdthemeswitcher.utils;

import de.spiderlinker.thunderbirdthemeswitcher.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class ThunderbirdUtils {

  private static String PROFILE_SUFFIX = ".default";

  public static Collection<File> getProfileFolders() {
    Collection<File> collectedProfileFolders = new ArrayList<>();
    File[] profileFolders = getFilesInProfilesFolderMatchingSuffix();
    if (profileFolders != null) {
      for (File profileFolder : profileFolders) {
        collectedProfileFolders.add(profileFolder);
      }
    }
    return collectedProfileFolders;
  }

  private static File[] getFilesInProfilesFolderMatchingSuffix() {
    return new File(Config.DIR_PROFILES).listFiles((dir, name) -> dir.isDirectory() && name.endsWith(PROFILE_SUFFIX));
  }

}
