package com.aniscode.jobportal.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDownloadUtil {

    private Path foundfile;
    
    public Resource getFileAsResource(String downloadDir, String fileName) throws IOException {
        // Implementation to retrieve file as Resource
        Path path = Paths.get(downloadDir);
        Files.list(path).forEach(file -> {
            if (file.getFileName().toString().startsWith(fileName)) {
                foundfile = file;
            }
        });

        if (foundfile != null) {
            // Return the file as a Resource
            // (Implementation depends on the specific Resource type being used)
            return new UrlResource(foundfile.toUri());
        }
        return null; // Placeholder return
    }
}
