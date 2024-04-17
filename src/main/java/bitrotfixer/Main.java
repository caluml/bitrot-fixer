package bitrotfixer;


import bitrotfixer.impl.*;

import java.io.File;
import java.nio.file.Files;

public class Main {

	public static void main(
		String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("Usage: java -jar target/bitrot-fixer-*.jar <file> <algorithm:expectedchecksum>");
			System.err.println("E.g.   java -jar target/bitrot-fixer-*.jar /tmp/broken.file md5:3a34f6b9e8a09f11bbc70aa46603dc63");
			System.exit(1);
		}
		File file = new File(args[0]);
		if (!file.canRead()) {
			System.err.println("Cannot read " + file);
			System.exit(2);
		}

		String[] checksumArgs = args[1].split(":");
		String algorithm = checksumArgs[0];
		String expectedChecksum = checksumArgs[1];
		BitFixer bitFixer = getBitFixer(algorithm);
		System.out.println("Checking " + file + " for " + algorithm + ":" + expectedChecksum);

		byte[] data = Files.readAllBytes(file.toPath());
		int corruptBit = bitFixer.fixBit(data, expectedChecksum);
		if (corruptBit > -1) {
			// data[] is already corrected
			Files.write(new File(file.getAbsolutePath() + "-fixed").toPath(), data);
		} else {
			System.out.println("Unable to find a single bit corruption that resolves to " + args[1]);
		}
	}

	private static BitFixer getBitFixer(
		String algorithm) {
		switch (algorithm) {
			case "crc32":
				return new BitFixer(new Crc32Checksummer());
			case "crc32c":
				return new BitFixer(new Crc32CChecksummer());
			case "md5":
				return new BitFixer(new Md5Checksummer());
			case "sha1":
				return new BitFixer(new Sha1Checksummer());
			case "sha256":
				return new BitFixer(new Sha256Checksummer());
			case "sha384":
				return new BitFixer(new Sha384Checksummer());
			case "sha512":
				return new BitFixer(new Sha512Checksummer());
			case "sha3256":
				return new BitFixer(new Sha3256Checksummer());
			default:
				throw new RuntimeException("Unsupported algorithm + " + algorithm);
		}
	}

}
