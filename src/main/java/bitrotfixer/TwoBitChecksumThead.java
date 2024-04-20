package bitrotfixer;

import java.util.concurrent.atomic.AtomicReferenceArray;

import static bitrotfixer.BitFlipper.flipBit;

public class TwoBitChecksumThead implements Runnable {


	private final AtomicReferenceArray<byte[]> solution;
	private final Checksummer checksummer;
	private final byte[] data;
	private final String expected;
	private final long start;
	private final int firstBitToFlip;
	private final int secondBitToFlip;

	public TwoBitChecksumThead(
		AtomicReferenceArray<byte[]> solution,
		Checksummer checksummer,
		byte[] data,
		String expected,
		long start,
		int firstBitToFlip,
		int secondBitToFlip) {
		this.solution = solution;
		this.checksummer = checksummer;
		this.data = data;
		this.expected = expected;
		this.start = start;
		this.firstBitToFlip = firstBitToFlip;
		this.secondBitToFlip = secondBitToFlip;
	}

	@Override
	public void run() {
		flipBit(data, firstBitToFlip); // Flip first bit
		flipBit(data, secondBitToFlip); // Flip second bit
		if (checksummer.checksum(data).equals(expected)) {
			System.out.println("Corrupt bits detected at bits " + firstBitToFlip + " and " + secondBitToFlip + " after " + (System.currentTimeMillis() - start) + " ms");
			solution.set(0, data.clone());
			Thread.currentThread().interrupt();
		}
	}
}
