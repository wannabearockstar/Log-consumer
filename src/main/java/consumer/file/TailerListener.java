package consumer.file;

/**
 * Created by wannabe on 09.03.16.
 */
public interface TailerListener {

	void handle(String line);
}
