package com.finalwork.marvelview.api.json.character;

import com.finalwork.marvelview.api.json.Image;
import com.finalwork.marvelview.api.json.Url;
import com.finalwork.marvelview.api.json.section.SectionList;

public class CharacterData {
    public long id;
    public String name;
    public String description;
    public Image thumbnail;
    public SectionList comics;
    public SectionList series;
    public SectionList stories;
    public SectionList events;
    public Url[] urls;

    public String getThumbnail() {
        return Image.getUrl(thumbnail.path, Image.SIZE_STANDARD_LARGE, thumbnail.extension);
    }

    public String getImage() {
        return Image.getUrl(thumbnail.path, Image.SIZE_STANDARD_INCREDIBLE, thumbnail.extension);
    }
}
