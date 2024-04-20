package bitrotfixer;

import java.util.concurrent.atomic.AtomicReferenceArray;

import static bitrotfixer.BitFlipper.flipBit;

public class TwoBitThead implements Runnable {


	private final AtomicReferenceArray<byte[]> solution;
	private final Hasher hasher;
	private final byte[] data;
	private final String expected;
	private final long start;
	private final int firstBitToFlip;
	private final int secondBitToFlip;

	public TwoBitThead(
		AtomicReferenceArray<byte[]> solution,
		Hasher hasher,
		byte[] data,
		String expected,
		long start,
		int firstBitToFlip,
		int secondBitToFlip) {
		this.solution = solution;
		this.hasher = hasher;
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
		if (hasher.hash(data).equals(expected)) {
			System.out.println("Corrupt bits detected at bits " + firstBitToFlip + " and " + secondBitToFlip + " after " + (System.currentTimeMillis() - start) + " ms");
			solution.set(0, data.clone());
			Thread.currentThread().interrupt();
		}
	}
}
