package eu.cynarski.emailsender;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;

import javax.jms.ConnectionFactory;

@SpringBootApplication
@EnableJms
public class Application {
    @Bean
    JmsListenerContainerFactory<?> jmsContainerFactory(ConnectionFactory connectionFactory) {
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

    @Bean
    ConnectionFactory jmsConnectionFactory() {
        PooledConnectionFactory pool = new PooledConnectionFactory();
        pool.setConnectionFactory(new ActiveMQConnectionFactory("vm://localhost"));
        return pool;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
    }
}