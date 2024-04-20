package bitrotfixer.impl;

import bitrotfixer.Hasher;
import org.apache.commons.codec.digest.DigestUtils;

public class Sha3256Hasher implements Hasher {

	@Override
	public boolean matches(
		String algorithm) {
		return "sha3_256".equals(algorithm);
	}

	@Override
	public String hash(
		byte[] data) {
		return DigestUtils.sha3_256Hex(data);
	}

}
