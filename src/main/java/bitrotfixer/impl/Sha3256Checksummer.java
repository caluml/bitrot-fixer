package bitrotfixer.impl;

import bitrotfixer.Checksummer;
import org.apache.commons.codec.digest.DigestUtils;

public class Sha3256Checksummer implements Checksummer {

	@Override
	public boolean matches(
		String algorithm) {
		return "sha3_256".equals(algorithm);
	}

	@Override
	public String checksum(
		byte[] data) {
		return DigestUtils.sha3_256Hex(data);
	}

}
