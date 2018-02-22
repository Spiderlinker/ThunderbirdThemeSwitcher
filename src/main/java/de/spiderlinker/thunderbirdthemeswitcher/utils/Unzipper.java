package de.spiderlinker.thunderbirdthemeswitcher.utils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzipper {

  private static final int BUFFER_SIZE = 4096;

  private Unzipper() {
    // Utility class
  }

  public static void unzip(String zipFilePath, String destDirectory) throws IOException {
    createDirectory(destDirectory);

    try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
      ZipEntry entry;
      while ((entry = zipIn.getNextEntry()) != null) {
        extractEntryOfZipToDirectory(zipIn, entry, destDirectory);
      }
    }
  }

  private static void createDirectory(String directory){
    File dir = new File(directory);
    if(!dir.exists()){
      dir.mkdir();
    }
  }

  private static void extractEntryOfZipToDirectory(ZipInputStream zipInput, ZipEntry entry, String destinationDir) throws IOException {
    String filePath = destinationDir + File.separator + entry.getName();
    if (!entry.isDirectory()) {
      extractFile(zipInput, filePath);
    } else {
      createDirectory(filePath);
    }
    zipInput.closeEntry();
  }

  private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
    try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
      byte[] bytesIn = new byte[BUFFER_SIZE];
      int read;
      while ((read = zipIn.read(bytesIn)) != -1) {
        bos.write(bytesIn, 0, read);
      }
    }
  }
}
