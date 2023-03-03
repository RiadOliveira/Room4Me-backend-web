package com.room4me.services;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.room4me.enumerators.FileType;
import com.room4me.errors.ServerException;

@Service
public class FileServices {
    private static final String staticPath = "src/main/resources/static";

    private String getPathFromFileType(FileType fileType) {
        if(fileType.equals(FileType.Avatar)) return "avatar";
        return "propertyImage";
    }

    public void saveFile(
        MultipartFile file, FileType type,
        String fileName
    ) {
        try {
            String typePath = getPathFromFileType(type);

            File path = new File(
                staticPath + "/" + typePath +
                "/" + fileName
            );
            path.createNewFile();

            FileOutputStream output = new FileOutputStream(path);
            output.write(file.getBytes());

            output.close();
        } catch (Exception exception) {
            throw new ServerException(
                "Internal Server Error",
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public void delete(String fileName, FileType type) {
        String typePath = getPathFromFileType(type);
        File path = new File(
            staticPath + "/" + typePath + "/" + fileName
        );

        if(path.delete()) return;
        throw new ServerException(
            "Internal Server Error",
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
