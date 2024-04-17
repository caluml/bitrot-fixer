package bitrotfixer.impl;

import bitrotfixer.Checksummer;
import org.apache.commons.codec.digest.DigestUtils;

public class Sha1Checksummer implements Checksummer {

	@Override
	public boolean matches(
		String algorithm) {
		return "sha1".equals(algorithm);
	}

	@Override
	public String checksum(
		byte[] data) {
		return DigestUtils.sha1Hex(data);
	}

}
