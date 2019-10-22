package PolyUrl;

import java.util.Objects;

public class Ptitu {
    private String url;
    private String longUrl;
    private String ownerMail;
    private int numberAccesses;
    private ContentType contentType;

    public Ptitu(String url,String longUrl,String ownerMail,ContentType contentType){
        this.url = url;
        this.longUrl = longUrl;
        this.ownerMail = ownerMail;
        this.numberAccesses = 0;
        this.contentType = contentType;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOwnerMail() {
        return ownerMail;
    }

    public void setOwnerMail(String ownerMail) {
        this.ownerMail = ownerMail;
    }

    public int getNumberAccesses() {
        return numberAccesses;
    }

    public void setNumberAccesses(int numberAccesses) {
        this.numberAccesses = numberAccesses;
    }

    public void increaseNumberAccesses() {
        this.numberAccesses += 1;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
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
