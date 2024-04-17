package bitrotfixer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BitFlipperTest {

	@Test
	public void Can_flip_bits() {
		byte[] bytes;

		bytes = new byte[2];
		assertThat(bytes).containsExactly(0, 0);

		bytes = new byte[2];
		BitFlipper.flipBit(bytes, 0);
		assertThat(bytes).containsExactly(1, 0);

		bytes = new byte[2];
		BitFlipper.flipBit(bytes, 1);
		assertThat(bytes).containsExactly(2, 0);

		bytes = new byte[2];
		BitFlipper.flipBit(bytes, 6);
		assertThat(bytes).containsExactly(64, 0);

		bytes = new byte[2];
		BitFlipper.flipBit(bytes, 7);
		assertThat(bytes).containsExactly(-128, 0);

		bytes = new byte[2];
		BitFlipper.flipBit(bytes, 8);
		assertThat(bytes).containsExactly(0, 1);

		bytes = new byte[2];
		BitFlipper.flipBit(bytes, 9);
		assertThat(bytes).containsExactly(0, 2);

		bytes = new byte[2];
		BitFlipper.flipBit(bytes, 15);
		assertThat(bytes).containsExactly(0, -128);
	}

}
