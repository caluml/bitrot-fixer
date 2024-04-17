package bitrotfixer;

import bitrotfixer.impl.Crc32Checksummer;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class TwoFlippedBitsTest {

	private static final String INPUT_FILE = "src/test/resources/two-bits";
	private static final String OUTPUT_FILE = "src/test/resources/two-bits-fixed";

	@Test
	public void Can_correct_2_flipped_bits_against_CRC32_checksum() throws Exception {
		// Given
		System.out.println(new Crc32Checksummer().checksum(Files.readAllBytes(Path.of("src/test/resources/two-bits-orig"))));
		String validChecksum = "558161692";
		File outputFile = new File(OUTPUT_FILE);
		outputFile.delete();

		// When
		Main.main(new String[]{INPUT_FILE, "crc32:" + validChecksum, "2"});

		// Then
		assertThat(outputFile).exists();
		assertThat(new Crc32Checksummer().checksum(Files.readAllBytes(outputFile.toPath()))).isEqualTo(validChecksum);
	}


	@Test
	public void Incorrect_checksum_results_in_no_fixed_file() throws Exception {
		// Given
		String validChecksum = "incorrect-checksum";
		File outputFile = new File(OUTPUT_FILE);
		outputFile.delete();

		// When
		Main.main(new String[]{INPUT_FILE, "md5:" + validChecksum, "2"});

		// Then
		assertThat(outputFile).doesNotExist();
	}
}
