package eu.cynarski.emailsender.jms.message;

import eu.cynarski.emailsender.rest.Attachment;

import javax.mail.internet.InternetAddress;
import java.io.Serializable;
import java.util.List;

public class EmailJmsMessage implements Serializable {
    private String html;
    private List<Attachment> attachments;
    private String subject;
    private InternetAddress recipient;

    public EmailJmsMessage(String html, List<Attachment> attachments, String subject, InternetAddress recipient) {
        this.html = html;
        this.attachments = attachments;
        this.subject = subject;
        this.recipient = recipient;
    }

    public String getHtml() {
        return html;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public String getSubject() {
        return subject;
    }

    public InternetAddress getRecipient() {
        return recipient;
    }
}
