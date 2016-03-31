package consumer.jms;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.BlockingDeque;

/**
 * Created by wannabe on 31.03.16.
 */
@Component
public class RabbitMqPublisher extends Thread {

	@Resource
	private BlockingDeque<String> messages;
	@Autowired
	private AmqpTemplate amqpTemplate;
	@Value("${jms.destination}")
	private String destination;

	@Override
	public void run() {
		while (true) {
			try {
				String message = messages.takeLast();
				amqpTemplate.convertAndSend(destination, message);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
}
