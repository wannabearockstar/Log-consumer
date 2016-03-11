package consumer.file;

import consumer.exception.TailerAlreadyWorkingException;
import consumer.exception.TailerNotWorkingException;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by wannabe on 09.03.16.
 */
public class MyTailer implements Runnable {

	private final int crunchifyRunEveryNSeconds;
	private final AtomicBoolean shouldIRun = new AtomicBoolean(false);
	private final File crunchifyFile;
	private final TailerListener tailerListener;
	private long lastKnownPosition = 0;

	private MyTailer(File crunchifyFile, int myInterval, TailerListener tailerListener) {
		this.crunchifyFile = crunchifyFile;
		this.crunchifyRunEveryNSeconds = myInterval;
		this.tailerListener = tailerListener;
	}

	public static MyTailer instance(File crunchifyFile, int myInterval, TailerListener tailerListener) {
		return new MyTailer(crunchifyFile, myInterval, tailerListener);
	}

	public void stopRunning() {
		if (!shouldIRun.compareAndSet(true, false)) {
			throw new TailerNotWorkingException();
		}
	}

	public void run() {
		if (!shouldIRun.compareAndSet(false, true)) {
			throw new TailerAlreadyWorkingException();
		}
		try {
			while (shouldIRun.get()) {
				Thread.sleep(crunchifyRunEveryNSeconds);
				if (crunchifyFile.length() > lastKnownPosition) {
					RandomAccessFile readWriteFileAccess = new RandomAccessFile(crunchifyFile, "rw");
					readWriteFileAccess.seek(lastKnownPosition);
					String crunchifyLine;
					while ((crunchifyLine = readWriteFileAccess.readLine()) != null) {
						tailerListener.handle(crunchifyLine);
					}
					lastKnownPosition = readWriteFileAccess.getFilePointer();
					readWriteFileAccess.close();
				}
			}
		} catch (Exception e) {
			stopRunning();
		}
	}
}
