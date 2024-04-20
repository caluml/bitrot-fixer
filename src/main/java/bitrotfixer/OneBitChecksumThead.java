package bitrotfixer;

import java.util.concurrent.atomic.AtomicReferenceArray;

import static bitrotfixer.BitFlipper.flipBit;

public class OneBitChecksumThead implements Runnable {


	private final AtomicReferenceArray<byte[]> solution;
	private final Checksummer checksummer;
	private final byte[] data;
	private final String expected;
	private final long start;
	private final int bitToFlip;

	public OneBitChecksumThead(
		AtomicReferenceArray<byte[]> solution,
		Checksummer checksummer,
		byte[] data,
		String expected,
		long start,
		int bitToFlip) {
		this.solution = solution;
		this.checksummer = checksummer;
		this.data = data;
		this.expected = expected;
		this.start = start;
		this.bitToFlip = bitToFlip;
	}

	@Override
	public void run() {
		flipBit(data, bitToFlip);
		if (checksummer.checksum(data).equals(expected)) {
			System.out.println("Corrupt bits detected at bits " + bitToFlip + " after " + (System.currentTimeMillis() - start) + " ms");
			solution.set(0, data.clone());
			Thread.currentThread().interrupt();
		}
	}
}
