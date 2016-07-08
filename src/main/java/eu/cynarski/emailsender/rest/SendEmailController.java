package eu.cynarski.emailsender.rest;

import eu.cynarski.emailsender.jms.Queue;
import eu.cynarski.emailsender.jms.message.EmailJmsMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@RestController
public class SendEmailController {
    private static final Logger logger = LoggerFactory.getLogger(SendEmailController.class);

    @Autowired
    ConfigurableApplicationContext context;

    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public String receive(
            @RequestBody
            EmailToSend emailToSend) throws AddressException {

        for (String recipient : emailToSend.getRecipients()) {
            EmailJmsMessage emailJmsMessage = new EmailJmsMessage(emailToSend.getHtml(), emailToSend.getAttachments(),
                    emailToSend.getSubject(), new InternetAddress(recipient));
            sendJmsMessage(emailJmsMessage);
        }
        return "E-Mail request stored";
    }

    private void sendJmsMessage(final EmailJmsMessage emailJmsMessage) {
        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
        jmsTemplate.send(Queue.EMAIL_QUEUE, session -> {
            return session.createObjectMessage(emailJmsMessage);
        });
    }
}
