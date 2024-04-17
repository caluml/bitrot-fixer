package bitrotfixer;

public class BitFlipper {

	static void flipBit(
		byte[] data,
		int i) {
		int byteOffset = i / 8;
		int bit = (i - (byteOffset * 8));
		data[byteOffset] = (byte) (data[byteOffset] ^ 1 << bit);
	}
}
