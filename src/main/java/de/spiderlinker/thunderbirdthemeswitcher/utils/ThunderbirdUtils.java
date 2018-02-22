package de.spiderlinker.thunderbirdthemeswitcher.utils;

import de.spiderlinker.thunderbirdthemeswitcher.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ThunderbirdUtils {

  private static final String PROFILE_SUFFIX = ".default";

  private ThunderbirdUtils() {
    // Utility class
  }

  public static Collection<File> getProfileFolders() {
    Collection<File> collectedProfileFolders = new ArrayList<>();
    File[] profileFolders = getFilesInProfilesFolderMatchingSuffix();
    if (profileFolders != null) {
      Collections.addAll(collectedProfileFolders, profileFolders);
    }
    return collectedProfileFolders;
  }

  private static File[] getFilesInProfilesFolderMatchingSuffix() {
    return new File(Config.DIR_PROFILES).listFiles((dir, name) -> dir.isDirectory() && name.endsWith(PROFILE_SUFFIX));
  }

}
