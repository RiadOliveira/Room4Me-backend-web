package com.room4me.utils;

import java.util.Base64;
import org.springframework.web.multipart.MultipartFile;

import com.room4me.errors.ServerException;

public class ImageUtils {
    public static byte[] getBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (Exception exception) {
            throw ServerException.INTERNAL_SERVER_EXCEPTION;
        }
    }

    public static String convertBytesToBase64(
        byte[] fileBytes, String fileType
    ) {
        return "data:image/" + fileType + ";base64," +
        Base64.getEncoder().encodeToString(fileBytes);
    }
}
