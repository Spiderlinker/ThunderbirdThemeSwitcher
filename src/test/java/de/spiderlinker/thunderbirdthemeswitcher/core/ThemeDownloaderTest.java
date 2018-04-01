package de.spiderlinker.thunderbirdthemeswitcher.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

public class ThemeDownloaderTest {

  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();

  private File testDownloadFile;
  private File testDestinationFile;

  private String testString = "This is a sample String.. Hello world :)";

  @Before
  public void setupUp() throws IOException {
    testDownloadFile = tempFolder.newFile("testDownloadFile.txt");
    testDestinationFile = tempFolder.newFile("testDestinationFile.txt");

    Files.write(testDownloadFile.toPath(), Collections.singleton(testString));
    System.out.println("Temp folder: " + tempFolder.getRoot().getAbsolutePath());
  }

  @Test
  public void downloadThemeAllFine() throws IOException {
    ThemeDownloader.downloadTheme(testDownloadFile.toURI().toString(), testDestinationFile.getAbsolutePath());

    List<String> lines = Files.readAllLines(testDestinationFile.toPath());
    System.out.println("Reading files from downloaded file: " + lines);
    Assert.assertFalse(lines.isEmpty());
    Assert.assertEquals(testString, lines.get(0));
  }

  @Test(expected = MalformedURLException.class)
  public void downloadThemeSourceNull() throws IOException {
    ThemeDownloader.downloadTheme(null, testDestinationFile.getAbsolutePath());
  }

  @Test(expected = MalformedURLException.class)
  public void downloadThemeSourceEmpty() throws IOException {
    ThemeDownloader.downloadTheme("", testDestinationFile.getAbsolutePath());
  }

  @Test(expected = FileNotFoundException.class)
  public void downloadThemeSourceNotExist() throws IOException {
    ThemeDownloader.downloadTheme(new File("notExisting").toURI().toString(), testDestinationFile.getAbsolutePath());
  }

  @Test(expected = NullPointerException.class)
  public void downloadThemeDestinationNull() throws IOException {
    ThemeDownloader.downloadTheme(testDownloadFile.toURI().toString(), null);
  }

  @Test(expected = FileNotFoundException.class)
  public void downloadThemeDestinationEmpty() throws IOException {
    ThemeDownloader.downloadTheme(testDownloadFile.toURI().toString(), "");
  }

}