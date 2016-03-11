package consumer.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.BlockingDeque;

/**
 * Created by wannabe on 03.03.16.
 */
@Component
public class JmsPublisher extends Thread {

	@Resource
	private BlockingDeque<String> messages;
	@Autowired
	private JmsTemplate jmsTemplate;

	@Override
	public void run() {
		while (true) {
			try {
				String message = messages.takeLast();
				jmsTemplate.send(session -> session.createTextMessage(message));
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
}
