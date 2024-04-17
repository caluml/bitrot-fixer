package bitrotfixer.impl;

import bitrotfixer.Checksummer;

import java.util.zip.CRC32;

public class Crc32Checksummer implements Checksummer {

	@Override
	public boolean matches(
		String algorithm) {
		return "crc32".equals(algorithm);
	}

	@Override
	public String checksum(
		byte[] data) {
		CRC32 crc32 = new CRC32();
		crc32.update(data);
		return String.valueOf(crc32.getValue());
	}
}
