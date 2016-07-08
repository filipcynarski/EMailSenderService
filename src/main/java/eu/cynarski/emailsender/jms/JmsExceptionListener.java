package eu.cynarski.emailsender.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

@Component
public class JmsExceptionListener implements ExceptionListener {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionListener.class);

    public void onException(final JMSException e) {
        logger.error("Unhandled JMSException", e);
    }
}