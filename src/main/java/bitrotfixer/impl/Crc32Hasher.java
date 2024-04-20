package bitrotfixer.impl;

import bitrotfixer.Hasher;

import java.util.zip.CRC32;

public class Crc32Hasher implements Hasher {

	@Override
	public boolean matches(
		String algorithm) {
		return "crc32".equals(algorithm);
	}

	@Override
	public String hash(
		byte[] data) {
		CRC32 crc32 = new CRC32();
		crc32.update(data);
		return String.valueOf(crc32.getValue());
	}
}
