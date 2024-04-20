package bitrotfixer;

import java.util.concurrent.atomic.AtomicReferenceArray;

import static bitrotfixer.BitFlipper.flipBit;

public class OneBitThead implements Runnable {


	private final AtomicReferenceArray<byte[]> solution;
	private final Hasher hasher;
	private final byte[] data;
	private final String expected;
	private final long start;
	private final int bitToFlip;

	public OneBitThead(
		AtomicReferenceArray<byte[]> solution,
		Hasher hasher,
		byte[] data,
		String expected,
		long start,
		int bitToFlip) {
		this.solution = solution;
		this.hasher = hasher;
		this.data = data;
		this.expected = expected;
		this.start = start;
		this.bitToFlip = bitToFlip;
	}

	@Override
	public void run() {
		flipBit(data, bitToFlip);
		if (hasher.hash(data).equals(expected)) {
			System.out.println("Corrupt bits detected at bits " + bitToFlip + " after " + (System.currentTimeMillis() - start) + " ms");
			solution.set(0, data.clone());
			Thread.currentThread().interrupt();
		}
	}
}
