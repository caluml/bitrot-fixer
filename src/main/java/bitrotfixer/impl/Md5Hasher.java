package bitrotfixer.impl;

import bitrotfixer.Hasher;
import org.apache.commons.codec.digest.DigestUtils;

public class Md5Hasher implements Hasher {

	@Override
	public boolean matches(
		String algorithm) {
		return "md5".equals(algorithm);
	}

	@Override
	public String hash(
		byte[] data) {
		return DigestUtils.md5Hex(data);
	}

}
