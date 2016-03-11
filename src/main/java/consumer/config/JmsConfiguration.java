package consumer.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

/**
 * Created by wannabe on 03.03.16.
 */
@Configuration
@PropertySource("classpath:application.yaml")
@EnableIntegration
@EnableJms
public class JmsConfiguration {

	@Bean(name = "connectionFactory")
	public ConnectionFactory connectionFactory(
		@Value("${jms.url}") String brokerUrl
	) {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(brokerUrl);
		connectionFactory.setClientIDPrefix("consumer-");

		return connectionFactory;
	}

	@Bean(name = "accessLogsJmsContainterFactory")
	public JmsListenerContainerFactory<?> myJmsContainerFactory(ConnectionFactory connectionFactory) {
		SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		return factory;
	}

	@Bean
	public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, @Value("${jms.destination}") String destination) {
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
		jmsTemplate.setDefaultDestinationName(destination);
		return jmsTemplate;
	}
}
