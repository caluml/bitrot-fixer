package bitrotfixer;


import bitrotfixer.impl.*;

import java.io.File;
import java.nio.file.Files;

public class Main {

	public static void main(
		String[] args) throws Exception {
		if (args.length < 2) {
			System.err.println("Usage: java -jar target/bitrot-fixer-*.jar <file> <algorithm:expectedhash> [numbits]");
			System.err.println("E.g.   java -jar target/bitrot-fixer-*.jar /tmp/broken.file md5:3a34f6b9e8a09f11bbc70aa46603dc63");
			System.exit(1);
		}
		File file = new File(args[0]);
		if (!file.canRead()) {
			System.err.println("Cannot read " + file);
			System.exit(2);
		}

		if (!args[1].contains(":")) {
			System.err.println("Supply hash as <algorithm>:<hash>");
			System.exit(3);
		}

		int numBits = 1;
		if (args.length == 3) {
			numBits = Integer.parseInt(args[2]);
		}

		String[] hashArgs = args[1].split(":");
		String algorithm = hashArgs[0];
		String expectedHash = hashArgs[1];
		BitFixer bitFixer = getBitFixer(algorithm);
		System.out.println("Checking " + file + " for " + algorithm + ":" + expectedHash);

		byte[] data = Files.readAllBytes(file.toPath());

		byte[] fixed = bitFixer.fixBit(data, expectedHash, numBits);
		if (fixed == null) {
			System.out.println("Unable to fix corruption in " + file.getAbsolutePath() + " that resolves to " + args[1] + " after checking " + numBits + " bits");
		} else {
			File fixedFile = new File(file.getAbsolutePath() + "-fixed");
			Files.write(fixedFile.toPath(), fixed);
			if (!bitFixer.getHasher().hash(Files.readAllBytes(fixedFile.toPath())).equals(expectedHash)) {
				System.err.println("The fixed file hash doesn't match the expected hash. Something has gone wrong.");
				System.exit(99);
			}
		}
	}

	private static BitFixer getBitFixer(
		String algorithm) {
		switch (algorithm) {
			case "crc32":
				return new BitFixer(new Crc32Hasher());
			case "crc32c":
				return new BitFixer(new Crc32CHasher());
			case "md5":
				return new BitFixer(new Md5Hasher());
			case "sha1":
				return new BitFixer(new Sha1Hasher());
			case "sha256":
				return new BitFixer(new Sha256Hasher());
			case "sha384":
				return new BitFixer(new Sha384Hasher());
			case "sha512":
				return new BitFixer(new Sha512Hasher());
			case "sha3256":
				return new BitFixer(new Sha3256Hasher());
			default:
				throw new RuntimeException("Unsupported algorithm + " + algorithm);
		}
	}

}
