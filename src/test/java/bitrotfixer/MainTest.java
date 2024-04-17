package bitrotfixer;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MainTest {

	@Test
	public void Can_correct_flipped_bit_against_MD5_checksum() throws Exception {
		// Given
		String validChecksum = "0084e07dd2b96c17e7b1315929c95ddb";
		File outputFile = new File("src/test/resources/test-image.jpeg-fixed");
		outputFile.delete();

		// When
		Main.main(new String[]{"src/test/resources/test-image.jpeg", "md5:" + validChecksum});

		// Then
		assertThat(outputFile).exists();
		assertThat(DigestUtils.md5Hex(Files.readAllBytes(outputFile.toPath()))).isEqualTo(validChecksum);
	}

	@Test
	public void Can_correct_flipped_bit_against_SHA1_checksum() throws Exception {
		// Given
		String validChecksum = "6d63cc3f08ebfdc381c7a745590362a97498b278";
		File outputFile = new File("src/test/resources/test-image.jpeg-fixed");
		outputFile.delete();

		// When
		Main.main(new String[]{"src/test/resources/test-image.jpeg", "sha1:" + validChecksum});

		// Then
		assertThat(outputFile).exists();
		assertThat(DigestUtils.sha1Hex(Files.readAllBytes(outputFile.toPath()))).isEqualTo(validChecksum);
	}

	@Test
	public void Can_correct_flipped_bit_against_SHA256_checksum() throws Exception {
		// Given
		String validChecksum = "96f46b349129f9da7d7f3d640a7f6e814e880c916b5c91870d3b50b28877362d";
		File outputFile = new File("src/test/resources/test-image.jpeg-fixed");
		outputFile.delete();

		// When
		Main.main(new String[]{"src/test/resources/test-image.jpeg", "sha256:" + validChecksum});

		// Then
		assertThat(outputFile).exists();
		assertThat(DigestUtils.sha256Hex(Files.readAllBytes(outputFile.toPath()))).isEqualTo(validChecksum);
	}

	@Test
	public void Can_correct_flipped_bit_against_SHA384_checksum() throws Exception {
		// Given
		String validChecksum = "dfcac28061fd7e063c9aacbf616614a3582f41cada01a07ebd8dc5d3711e68b6bbc98fe96c2251fef1aa8b978e1b9d94";
		File outputFile = new File("src/test/resources/test-image.jpeg-fixed");
		outputFile.delete();

		// When
		Main.main(new String[]{"src/test/resources/test-image.jpeg", "sha384:" + validChecksum});

		// Then
		assertThat(outputFile).exists();
		assertThat(DigestUtils.sha384Hex(Files.readAllBytes(outputFile.toPath()))).isEqualTo(validChecksum);
	}

	@Test
	public void Can_correct_flipped_bit_against_SHA512() throws Exception {
		// Given
		String validChecksum = "757d88b1b077d7fad6195a96f52850b0c978d3ac52accf6fb872d8bbddbf7bcf29aa943ed9b6732205e14c09633dd905c6998f522f7862d800eef9ff986b01cb";
		File outputFile = new File("src/test/resources/test-image.jpeg-fixed");
		outputFile.delete();

		// When
		Main.main(new String[]{"src/test/resources/test-image.jpeg", "sha512:" + validChecksum});

		// Then
		assertThat(outputFile).exists();
		assertThat(DigestUtils.sha512Hex(Files.readAllBytes(outputFile.toPath()))).isEqualTo(validChecksum);
	}

	@Test
	public void Can_correct_flipped_bit_against_SHA3_256_checksum() throws Exception {
		// Given
		String validChecksum = "571392708d4bfe11426c4746aa44728254f5473e87521623dddad290e513b317";
		File outputFile = new File("src/test/resources/test-image.jpeg-fixed");
		outputFile.delete();

		// When
		Main.main(new String[]{"src/test/resources/test-image.jpeg", "sha3256:" + validChecksum});

		// Then
		assertThat(outputFile).exists();
		assertThat(DigestUtils.sha3_256Hex(Files.readAllBytes(outputFile.toPath()))).isEqualTo(validChecksum);
	}

	@Test
	public void Incorrect_checksum_results_in_no_fixed_file() throws Exception {
		// Given
		String validChecksum = "incorrect-checksum";
		File outputFile = new File("src/test/resources/test-image.jpeg-fixed");
		outputFile.delete();

		// When
		Main.main(new String[]{"src/test/resources/test-image.jpeg", "md5:" + validChecksum});

		// Then
		assertThat(outputFile).doesNotExist();
	}

	@Test
	public void Unknown_algorithm_throws_Exception() {
		// Given
		String checksum = "70bc8f4b72a86921468bf8e8441dce51";
		File outputFile = new File("src/test/resources/test-image.jpeg-fixed");
		outputFile.delete();

		// When
		RuntimeException thrown = assertThrows(RuntimeException.class,
			() -> Main.main(new String[]{"src/test/resources/test-image.jpeg", "UNKNOWN:" + checksum}));

		assertThat(thrown.getMessage()).isEqualTo("Unsupported algorithm + UNKNOWN");
	}

}
