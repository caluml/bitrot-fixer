package bitrotfixer.impl;

import bitrotfixer.Hasher;

import java.util.zip.CRC32C;

public class Crc32CHasher implements Hasher {

	@Override
	public boolean matches(
		String algorithm) {
		return "crc32c".equals(algorithm);
	}

	@Override
	public String hash(
		byte[] data) {
		CRC32C crc32c = new CRC32C();
		crc32c.update(data);
		return String.valueOf(crc32c.getValue());
	}
}
