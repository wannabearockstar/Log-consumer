package consumer.config;

import consumer.file.MyTailer;
import consumer.file.TailerListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by wannabe on 03.03.16.
 */
@Configuration
public class ConsumerConfig {

	@Bean
	public BlockingDeque<String> messages() {
		return new LinkedBlockingDeque<>(100);
	}

	@Bean
	public MyTailer tailer(TailerListener tailerListener,
												 @Value("${log.path:}") String logPath
	) throws IOException {
		return MyTailer.instance(new File(logPath), 1000, tailerListener);
	}
}
