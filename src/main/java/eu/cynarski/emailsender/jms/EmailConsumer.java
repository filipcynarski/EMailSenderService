package eu.cynarski.emailsender.jms;

import eu.cynarski.emailsender.jms.message.EmailJmsMessage;
import eu.cynarski.emailsender.send.EmailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

@Component
public class EmailConsumer {
    private static final Logger logger = LoggerFactory.getLogger(EmailConsumer.class);

    @Autowired
    EmailMessage emailMessage;

    @JmsListener(destination = Queue.EMAIL_QUEUE, containerFactory = "jmsContainerFactory")
    public void receiveMessage(Message message) {
        if (message instanceof ObjectMessage) {
            ObjectMessage objectMessage = (ObjectMessage) message;
            try {
                if (objectMessage.getObject() instanceof EmailJmsMessage) {
                    try {
                        EmailJmsMessage emailJmsMessage = (EmailJmsMessage) objectMessage.getObject();
                        emailMessage.sendEmail(emailJmsMessage.getHtml(), emailJmsMessage.getAttachments(),
                                emailJmsMessage.getRecipient(), emailJmsMessage.getSubject());
                    } catch (Exception e) {
                        logger.error("Email messaging exception", e);
                    }
                }
            } catch (JMSException ex) {
                logger.error("JMSException thrown during Email JMS message acknowledgment:", ex);
            }
        } else {
            logger.error("JMS: not an object message - nothing to do");
        }
    }
}
