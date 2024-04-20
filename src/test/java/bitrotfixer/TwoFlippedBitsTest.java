package bitrotfixer;

import bitrotfixer.impl.Crc32Hasher;
import bitrotfixer.impl.Md5Hasher;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

public class TwoFlippedBitsTest {

	private static final String SMALL_INPUT_FILE = "src/test/resources/two-bits";
	private static final String SMALL_OUTPUT_FILE = "src/test/resources/two-bits-fixed";
	private static final String LARGE_INPUT_FILE = "src/test/resources/test-image-two-flips.jpeg";
	private static final String LARGE_OUTPUT_FILE = "src/test/resources/test-image-two-flips.jpeg-fixed";

	@Test
	public void Can_correct_2_flipped_bits_against_CRC32_hash() throws Exception {
		// Given
		String validHash = "558161692";
		File outputFile = new File(SMALL_OUTPUT_FILE);
		outputFile.delete();

		// When
		Main.main(new String[]{SMALL_INPUT_FILE, "crc32:" + validHash, "2"});

		// Then
		assertThat(outputFile).exists();
		assertThat(new Crc32Hasher().hash(Files.readAllBytes(outputFile.toPath()))).isEqualTo(validHash);
	}

	@Test
	public void Can_correct_2_flipped_bits_against_MD5_hash() throws Exception {
		// Given
		String validHash = "8a673261e62cdfc9072b0dc3ee4d21eb";
		File outputFile = new File(LARGE_OUTPUT_FILE);
		outputFile.delete();

		// When
		Main.main(new String[]{LARGE_INPUT_FILE, "md5:" + validHash, "2"});

		// Then
		assertThat(outputFile).exists();
		assertThat(new Md5Hasher().hash(Files.readAllBytes(outputFile.toPath()))).isEqualTo(validHash);
	}


	@Test
	public void Incorrect_hash_results_in_no_fixed_file() throws Exception {
		// Given
		String validHash = "incorrect-hash";
		File outputFile = new File(SMALL_OUTPUT_FILE);
		outputFile.delete();

		// When
		Main.main(new String[]{SMALL_INPUT_FILE, "md5:" + validHash, "2"});

		// Then
		assertThat(outputFile).doesNotExist();
	}
}
