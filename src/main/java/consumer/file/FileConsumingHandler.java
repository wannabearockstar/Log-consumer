package consumer.file;

import consumer.jms.RabbitMqPublisher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wannabe on 03.03.16.
 */
@Component
public class FileConsumingHandler implements InitializingBean {

	@Autowired
	private MyTailer tailer;
	@Autowired
	private RabbitMqPublisher rabbitMqPublisher;

	@Override
	public void afterPropertiesSet() throws Exception {
		new Thread(tailer).start();
		rabbitMqPublisher.start();
	}
}
