package eu.cynarski.emailsender.rest;

import java.io.Serializable;

public class Attachment implements Serializable {
    private String cid;
    private String base64Image;
    private String mimeType;
    private String fileName;

    public String getCid() {
        return cid;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getFileName() {
        return fileName;
    }
}
