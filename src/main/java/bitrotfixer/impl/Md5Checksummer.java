package bitrotfixer.impl;

import bitrotfixer.Checksummer;
import org.apache.commons.codec.digest.DigestUtils;

public class Md5Checksummer implements Checksummer {

	@Override
	public boolean matches(
		String algorithm) {
		return "md5".equals(algorithm);
	}

	@Override
	public String checksum(
		byte[] data) {
		return DigestUtils.md5Hex(data);
	}

}
