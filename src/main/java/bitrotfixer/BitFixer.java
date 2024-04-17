package bitrotfixer;

import static bitrotfixer.BitFlipper.flipBit;

public class BitFixer {

	private final Checksummer checksummer;

	public BitFixer(
		Checksummer checksummer) {
		this.checksummer = checksummer;
	}

	boolean fixBit(
		byte[] data,
		String expected,
		int numBits) {
		if (checksummer.checksum(data).equals(expected)) {
			System.out.println("It already matches the checksum");
			System.exit(0);
		}

		System.out.println("Data length " + data.length * 8 + " bits");
		// Try a single bit
		long start = System.currentTimeMillis();
		for (int i = 0; i < data.length * 8; i++) {
			flipBit(data, i); // Flip bit
			if (checksummer.checksum(data).equals(expected)) {
				System.out.println("Corrupt bit detected at bit " + i + " after " + (System.currentTimeMillis() - start) + " ms");
				return true;
			}
			flipBit(data, i); // Flip it back
		}
		System.out.println("Didn't find a single bit flip that would match the given checksum after " + (System.currentTimeMillis() - start) + " ms");
		if (numBits == 1) {
			System.out.println("Set the numbits parameter to 2 to try combinations of two flipped bits");
			return false;
		}

		// Try 2 bits. Very slow.
		start = System.currentTimeMillis();
		for (int i = 0; i < data.length * 8; i++) {
			flipBit(data, i); // Flip first bit
			for (int j = i + 1; j < data.length * 8; j++) {
				flipBit(data, j); // Flip second bit
				if (checksummer.checksum(data).equals(expected)) {
					System.out.println("Corrupt bits detected at bits " + i + ", " + j + " after " + (System.currentTimeMillis() - start) + " ms");
					return true;
				}
				flipBit(data, j); // Flip second bit back
			}
			flipBit(data, i); // Flip first bit back
		}

		System.out.println("Didn't find two bit flips that would match the given checksum after " + (System.currentTimeMillis() - start) + " ms");
		return false;
	}

}
