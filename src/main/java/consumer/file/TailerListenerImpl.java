package consumer.file;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.BlockingDeque;

/**
 * Created by wannabe on 03.03.16.
 */
@Component("tailerListener")
public class TailerListenerImpl implements TailerListener {

	@Resource
	private BlockingDeque<String> messages;

	@Override
	public void handle(String line) {
		try {
			messages.putFirst(line);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}
