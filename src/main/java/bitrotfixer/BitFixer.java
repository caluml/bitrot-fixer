package bitrotfixer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class BitFixer {

	private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

	private final Checksummer checksummer;

	public BitFixer(
		Checksummer checksummer) {
		this.checksummer = checksummer;
	}

	byte[] fixBit(
		byte[] data,
		String expected,
		int numBits) throws InterruptedException {
		if (checksummer.checksum(data).equals(expected)) {
			System.out.println("It already matches the checksum");
			System.exit(0);
		}

		System.out.println("Data length " + data.length * 8 + " bits");
		System.out.println("Found " + AVAILABLE_PROCESSORS + " cores");

		ExecutorService oneBitChecksumExecutor = new ThreadPoolExecutor(AVAILABLE_PROCESSORS, AVAILABLE_PROCESSORS, 0L, TimeUnit.MILLISECONDS,
			new LimitedQueue<>(AVAILABLE_PROCESSORS * 4));

		AtomicReferenceArray<byte[]> solution = new AtomicReferenceArray<>(1);

		// Try a single bit
		long start = System.currentTimeMillis();
		for (int i = 0; i < data.length * 8; i++) {
			if (solution.get(0) == null) {
				oneBitChecksumExecutor.submit(new OneBitChecksumThead(solution, checksummer, data.clone(), expected, start, i));
			}
		}
		oneBitChecksumExecutor.shutdown();
		oneBitChecksumExecutor.awaitTermination(5, TimeUnit.SECONDS);
		if (solution.get(0) != null) {
			return solution.get(0);
		}

		System.out.println("Didn't find a single bit flip that would match the given checksum after " + (System.currentTimeMillis() - start) + " ms");
		if (numBits == 1) {
			System.out.println("Set the numbits parameter to 2 to try combinations of two flipped bits");
			return null;
		}


		// Try 2 bits. Very slow.
		ExecutorService twoBitChecksumExecutor = new ThreadPoolExecutor(AVAILABLE_PROCESSORS, AVAILABLE_PROCESSORS, 0L, TimeUnit.MILLISECONDS,
			new LimitedQueue<>(AVAILABLE_PROCESSORS * 4));

		start = System.currentTimeMillis();
		for (int i = 0; i < data.length * 8; i++) {
			if (solution.get(0) != null) break;
			for (int j = i + 1; j < data.length * 8; j++) {
				if (solution.get(0) == null) {
					twoBitChecksumExecutor.submit(new TwoBitChecksumThead(solution, checksummer, data.clone(), expected, start, i, j));
				}
			}
		}

		twoBitChecksumExecutor.shutdown();
		twoBitChecksumExecutor.awaitTermination(5, TimeUnit.SECONDS);
		if (solution.get(0) != null) {
			return solution.get(0);
		}

		System.out.println("Didn't find two bit flips that would match the given checksum after " + (System.currentTimeMillis() - start) + " ms");
		return null;
	}

}
