package de.spiderlinker.thunderbirdthemeswitcher.core;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class ThemeDownloader {

	public static void downloadTheme(String zipFileUrl, String destinationFile) throws IOException {
		URL url = new URL(zipFileUrl);
		try (ReadableByteChannel inputChannel = Channels.newChannel(url.openStream());
				FileOutputStream outputChannel = new FileOutputStream(destinationFile)) {
			outputChannel.getChannel().transferFrom(inputChannel, 0, Long.MAX_VALUE);
		}
	}

}
