package bitrotfixer;

import java.util.concurrent.atomic.AtomicReferenceArray;

import static bitrotfixer.BitFlipper.flipBit;

public class TwoBitChecksumThead implements Runnable {


	private final AtomicReferenceArray<byte[]> solution;
	private final Checksummer checksummer;
	private final byte[] data;
	private final String expected;
	private final long start;
	private final int i;
	private final int j;

	public TwoBitChecksumThead(AtomicReferenceArray<byte[]> solution,
														 Checksummer checksummer,
														 byte[] data,
														 String expected,
														 long start,
														 int i,
														 int j) {
		this.solution = solution;
		this.checksummer = checksummer;
		this.data = data;
		this.expected = expected;
		this.start = start;
		this.i = i;
		this.j = j;
	}

	@Override
	public void run() {
		flipBit(data, i); // Flip first bit
		flipBit(data, j); // Flip second bit
		if (checksummer.checksum(data).equals(expected)) {
			System.out.println("Corrupt bits detected at bits " + i + ", " + j + " after " + (System.currentTimeMillis() - start) + " ms");
			solution.set(0, data.clone());
			Thread.currentThread().interrupt();
		}
	}
}
