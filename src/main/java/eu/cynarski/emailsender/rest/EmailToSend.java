package eu.cynarski.emailsender.rest;

import java.util.List;

public class EmailToSend {
    private String html;
    private List<Attachment> attachments;
    private String subject;
    private List<Recipient> recipients;

    public String getHtml() {
        return html;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public String getSubject() {
        return subject;
    }

    public List<Recipient> getRecipients() {
        return recipients;
    }
}
