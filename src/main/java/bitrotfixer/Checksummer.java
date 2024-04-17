package bitrotfixer;

public interface Checksummer {

	boolean matches(String algorithm);

	String checksum(byte[] data);
}
