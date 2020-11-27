package com.finalwork.marvelview.api.json.section;

public class SectionSummary {
    public String resourceURI;
    public String name;

    public long getId() {
        String id = getLastPathSegment(resourceURI);
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String getLastPathSegment(String uri) {//通过截取url获取Id
        if (uri == null) {
            return "";
        }
        int indexOf = uri.lastIndexOf("/");
        return uri.substring(indexOf + 1);
    }

}
