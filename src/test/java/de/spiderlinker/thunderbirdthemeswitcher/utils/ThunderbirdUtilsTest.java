package de.spiderlinker.thunderbirdthemeswitcher.utils;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Collection;

public class ThunderbirdUtilsTest {

  @Test
  public void getProfileFoldersNotNull() {
    Assert.assertNotNull(ThunderbirdUtils.getProfileFolders());
  }

  @Test
  public void getProfileFoldersContainsFoldersNameConvention() {
    Collection<File> profileFolders = ThunderbirdUtils.getProfileFolders();
    for (File profileFolder : profileFolders) {
      System.out.println(profileFolder.getAbsolutePath());
      Assert.assertTrue(profileFolder.getAbsolutePath().endsWith(".default"));
    }
  }

}