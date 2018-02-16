package de.spiderlinker.thunderbirdthemeswitcher.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
  public void downloadTheme() throws IOException {
    ThemeDownloader.downloadTheme(testDownloadFile.toURI().toString(), testDestinationFile.getAbsolutePath());

    List<String> lines = Files.readAllLines(testDestinationFile.toPath());
    System.out.println("Reading files from downloaded file: " + lines);
    Assert.assertFalse(lines.isEmpty());
    Assert.assertEquals(testString, lines.get(0));
  }

}