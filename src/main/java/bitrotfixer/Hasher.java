package bitrotfixer;

public interface Hasher {

	boolean matches(String algorithm);

	String hash(byte[] data);
}
