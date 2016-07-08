package eu.cynarski.emailsender.send;

import eu.cynarski.emailsender.rest.Attachment;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Component
public class EmailMessage {
    @Value("${mail.username}")
    private String userName;
    @Value("${mail.password}")
    private String password;
    @Value("${mail.smtp.host}")
    private String smtpHost;
    @Value("${mail.smtp.port}")
    private String smtpPort;
    @Value("${mail.smtp.auth}")
    private String smtpAuth;
    @Value("${mail.smtp.starttls.enable}")
    private String smtpStarttls;
    @Value("${mail.from}")
    private String mailFrom;
    @Value("${mail.from.name}")
    private String mailFromName;

    public void sendEmail(String html, List<Attachment> attachments, InternetAddress recipient, String title)
            throws MessagingException, UnsupportedEncodingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);
        properties.put("mail.smtp.auth", smtpAuth);
        properties.put("mail.smtp.starttls.enable", smtpStarttls);
        properties.put("mail.user", userName);
        properties.put("mail.password", password);

        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };

        Session session = Session.getInstance(properties, auth);

        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(mailFrom, mailFromName));

        InternetAddress[] toAddresses = { recipient };

        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(javax.mail.internet.MimeUtility.encodeText(title, "UTF-8", "Q"));
        msg.setSentDate(new Date());

        MimeBodyPart wrap = new MimeBodyPart();

        MimeMultipart cover = new MimeMultipart("alternative");

        MimeBodyPart htmlContent = new MimeBodyPart();
        MimeBodyPart textContent = new MimeBodyPart();
        cover.addBodyPart(textContent);
        cover.addBodyPart(htmlContent);

        wrap.setContent(cover);

        MimeMultipart content = new MimeMultipart("related");
        content.addBodyPart(wrap);

        for (Attachment attachment : attachments) {
            ByteArrayDataSource dSource = new ByteArrayDataSource(Base64.decodeBase64(attachment.getBase64Image()),
                    attachment.getMimeType());
            MimeBodyPart filePart = new MimeBodyPart();
            filePart.setDataHandler(new DataHandler(dSource));
            filePart.setFileName(attachment.getFileName());
            filePart.setHeader("Content-ID", "<" + attachment.getCid() + ">");
            filePart.setDisposition(MimeBodyPart.INLINE);
            content.addBodyPart(filePart);
        }

        htmlContent.setContent(html, "text/html; charset=UTF-8");
        textContent.setContent(
                "Twoj klient poczty nie wspiera formatu HTML/Your e-mail client doesn't support HTML format.",
                "text/plain; charset=UTF-8");

        msg.setContent(content);

        Transport.send(msg);
    }
}
