package PolyUrl.ptitu;

import PolyUrl.storage.ContentType;

import java.util.Objects;

public class Ptitu {
    private String url;
    private String longUrl;
    private String ownerMail;
    private ContentType contentType;

    public Ptitu(String url, String longUrl, String ownerMail, ContentType contentType) {
        this.url = url;
        this.longUrl = longUrl;
        this.ownerMail = ownerMail;
        this.contentType = contentType;
    }

    public String getUrl() {
        return url;
    }

    public String getOwnerMail() {
        return ownerMail;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public String getLongUrl() {
        return longUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ptitu ptitu = (Ptitu) o;
        return longUrl.equals(ptitu.longUrl) &&
                ownerMail.equals(ptitu.ownerMail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(longUrl, ownerMail);
    }
}
