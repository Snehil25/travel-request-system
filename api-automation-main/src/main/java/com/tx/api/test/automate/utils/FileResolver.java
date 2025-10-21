package com.tx.api.test.automate.utils;

import groovy.util.logging.Slf4j;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@lombok.extern.slf4j.Slf4j
@Slf4j
public class FileResolver {
    public static File resolve(String fileUri) throws Exception{
        if(fileUri.startsWith("local:")){
            String localPath=fileUri.substring("local:".length());
            log.info(" Resolving local file path: {} ",localPath);
            File file = new File(localPath);
            if(!file.exists()){
                throw new Exception(" Local file not found at path: "+localPath);
            }
            return file;
        }
        if (fileUri.startsWith("git:")){
            String gitUrl = fileUri.substring("git:".length());
            log.info("Downloading file from git URL:{}",gitUrl);
            return downloadFromUrl(gitUrl);
        }
        log.warn("No prefix found for file path '{}', Treating as standard local path.",fileUri);
        return new File(fileUri);
    }
    private static File downloadFromUrl(String urlString) throws Exception{
        URL url = new URL(urlString);
        File temp = File.createTempFile("Downloaded-",".tmp");
        temp.deleteOnExit();
        try (InputStream in=url.openStream()){
            Files.copy(in,temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        log.info("File successfully downloaded to temporary path: {}",temp.getAbsolutePath());
        return temp;
    }
}
