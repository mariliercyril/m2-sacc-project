package PolyUrl;

public class Ptitu {
    private String url;
    private String ownerMail;
    private int numberAccesses;
    private ContentType contentType;


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
}
