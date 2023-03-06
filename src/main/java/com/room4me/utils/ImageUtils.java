package com.room4me.utils;

import com.room4me.dtos.property.ImageDTO;
import com.room4me.entities.Image;
import com.room4me.services.FileAPIServices;

public class ImageUtils {
    public static void setCompleteImageLink(Image image) {
        image.setFileLink(
            FileAPIServices.getFullLinkFromUniquePart(image.getFileLink())
        );
    }

    public static void setCompleteImageLink(ImageDTO image) {
        image.setFileLink(
            FileAPIServices.getFullLinkFromUniquePart(image.getFileLink())
        );
    }
}
