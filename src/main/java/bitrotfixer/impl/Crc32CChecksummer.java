package bitrotfixer.impl;

import bitrotfixer.Checksummer;

import java.util.zip.CRC32C;

public class Crc32CChecksummer implements Checksummer {

	@Override
	public boolean matches(
		String algorithm) {
		return "crc32c".equals(algorithm);
	}

	@Override
	public String checksum(
		byte[] data) {
		CRC32C crc32c = new CRC32C();
		crc32c.update(data);
		return String.valueOf(crc32c.getValue());
	}
}
