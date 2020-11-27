package com.finalwork.marvelview.api.json.section;

import com.finalwork.marvelview.api.json.Image;

public class SectionData {
    public long id;
    public String title;
    public Image thumbnail;

    public String getThumbnail() {
        if (thumbnail == null) {
            return null;
        }
        return Image.getUrl(thumbnail.path, Image.SIZE_PORTRAIT_XLARGE, thumbnail.extension);
    }

    public String getImage() {
        if (thumbnail == null) {
            return null;
        }
        return Image.getUrl(thumbnail.path, Image.SIZE_DETAIL, thumbnail.extension);
    }

}
