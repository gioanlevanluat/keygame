package com.keygame.ultils;

import jakarta.mail.Folder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtils {

    static Logger logger = LoggerFactory.getLogger(FileUtils.class);
    public static String downloadFile(String url, String fileName, String folderPath, boolean isReplace) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        Path path = Paths.get(folderPath, fileName);
        if (path.toFile().exists() && !isReplace) {
            return fileName;
        }
        InputStream in = null;
        try {
            in = new URL(url).openStream();
            Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            logger.error("ERROR save file url {} folder {} err:", url, folderPath, e);
        }
        return null;
    }
}
