package bitrotfixer.impl;

import bitrotfixer.Hasher;
import org.apache.commons.codec.digest.DigestUtils;

public class Sha512Hasher implements Hasher {

	@Override
	public boolean matches(
		String algorithm) {
		return "sha512".equals(algorithm);
	}

	@Override
	public String hash(
		byte[] data) {
		return DigestUtils.sha512Hex(data);
	}

}
