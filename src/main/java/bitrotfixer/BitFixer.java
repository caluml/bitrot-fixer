package bitrotfixer;

import static bitrotfixer.BitFlipper.flipBit;

public class BitFixer {

	private final Checksummer checksummer;

	public BitFixer(
		Checksummer checksummer) {
		this.checksummer = checksummer;
	}

	int fixBit(
		byte[] data,
		String expected) {
		if (checksummer.checksum(data).equals(expected)) {
			System.out.println("It already matches the checksum");
			System.exit(0);
		}

		System.out.println("Data length " + data.length * 8 + " bits");
		long start = System.currentTimeMillis();
		for (int i = 0; i < data.length * 8; i++) {
			flipBit(data, i); // Flip bit
			if (checksummer.checksum(data).equals(expected)) {
				System.out.println("Corrupt bit detected at bit " + i + " after " + (System.currentTimeMillis() - start) + " ms");
				return i;
			}
			flipBit(data, i); // Flip it back
		}
		return -1;
	}

}
