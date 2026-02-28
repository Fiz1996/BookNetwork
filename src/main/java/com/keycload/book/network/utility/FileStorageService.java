package com.keycload.book.network.utility;

import com.keycload.book.network.book.Book;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageService {

    @Value("${application.file.upload.photos-output-path}")
    private String fileUploadPath;

    public String saveFile(
                          @Nonnull Book book ,
                          @Nonnull  MultipartFile sourceFile ,
                          @Nonnull Integer userId) {
        final String fileUploadSubPath = "users" + File.separator + userId;
        return uploadFile(sourceFile , fileUploadSubPath);
    }

    private String uploadFile(
            MultipartFile sourceFile,
            String fileUploadSubPath) {
        final String finalUploadPath = fileUploadPath + File.separator + fileUploadSubPath;
        File targetFolder = new File(finalUploadPath);
        if(!targetFolder.exists()) {

            boolean folderCreated = targetFolder.mkdirs();
            if(!folderCreated) {
                log.error("Failed to create folder {}", finalUploadPath);
            }
        }

        final String fileExtension = getFileExtension(sourceFile.getOriginalFilename());
        String targetFilePath = finalUploadPath + File.separator + System.currentTimeMillis() + "." + fileExtension;
        Path targetPath = Paths.get(targetFilePath);
        try {
            Files.write(targetPath, sourceFile.getBytes());
            log.info("File {} uploaded successfully", targetFilePath);
            return targetFilePath;
        } catch (Exception e) {
            log.error("Failed to write file {}", targetFilePath);
        }
        return finalUploadPath;
    }

    private String getFileExtension(@Nullable String fileName) {
        if(fileName == null || fileName.isEmpty()) {
            return "";
        }

        int lastDotIndex = fileName.lastIndexOf('.');
        if(lastDotIndex == -1) {
            return "";
        }
        return fileName.substring(lastDotIndex  +1 );
    }

    public static byte[] readFileFromLocation(String fileUrl) {
        return null;
    }

    public String saveFile(MultipartFile file, Integer id) {
        final String fileUploadSubPath = "users" + File.separator + id;
        return uploadFile(file, fileUploadSubPath);
    }
}
