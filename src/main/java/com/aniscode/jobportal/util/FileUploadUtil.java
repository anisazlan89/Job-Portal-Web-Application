package com.aniscode.jobportal.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {

    public static void saveFile(String uploadDir, String filename, MultipartFile multipartFile) throws Exception {
        // Implementation for saving the file to the specified directory
        // This is a placeholder for actual file saving logic

        //upload path
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()){
            Path filePath = uploadPath.resolve(filename);
            System.out.println("FilePath " + filePath);
            System.out.println("fileName " + filename);

            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        }catch(IOException ioe){
            throw new IOException("Could not save file: " + filename, ioe);
        }
    }
}
