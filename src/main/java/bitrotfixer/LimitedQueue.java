package bitrotfixer;

import java.util.concurrent.LinkedBlockingQueue;

public class LimitedQueue<E> extends LinkedBlockingQueue<E> {

	// Found at https://stackoverflow.com/questions/4521983/java-executorservice-that-blocks-on-submission-after-a-certain-queue-size/4522411#4522411

	public LimitedQueue(int maxSize) {
		super(maxSize);
	}

	@Override
	public boolean offer(E e) {
		// turn offer() and add() into a blocking calls (unless interrupted)
		try {
			put(e);
			return true;
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
		return false;
	}

}