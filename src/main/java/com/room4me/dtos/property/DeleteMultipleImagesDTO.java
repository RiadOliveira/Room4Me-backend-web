package com.room4me.dtos.property;

import java.util.List;
import java.util.UUID;

public class DeleteMultipleImagesDTO {
    private List<UUID> imagesIds;

    public DeleteMultipleImagesDTO() {
        super();
    }

    public DeleteMultipleImagesDTO(List<UUID> imagesIds) {
        this.imagesIds = imagesIds;
    }

    public List<UUID> getImagesIds() {
        return imagesIds;
    }

    public void setImagesIds(List<UUID> imagesIds) {
        this.imagesIds = imagesIds;
    }
}
