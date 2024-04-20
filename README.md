# Bitrot Fixer

If you have a file (jpeg, mp3, etc) which has become corrupted by [bit rot](https://en.wikipedia.org/wiki/Data_rot), and
you know what the correct checksum should be, this CLI project will try and flip every bit in the file until it finds
the one that is incorrect.

### Args:<br>

Mandatory: file to check<br>
Mandatory: correct checksum<br>
Optional: 1 or 2 flipped bits to try<br>

### Usage

```bash
./mvnw clean package
java -jar target/bitrot-fixer-1.0-SNAPSHOT.jar src/test/resources/test-image.jpeg md5:0084e07dd2b96c17e7b1315929c95ddb 1
```

#### Native
To build a native image, download the Graal JDK
```bash
JAVA_HOME=/path/to/graaljdk ./mvnw -DskipTests -Pnative clean package
target/bitrot-fixer src/test/resources/test-image.jpeg md5:0084e07dd2b96c17e7b1315929c95ddb 1
```

#### Currently supported checksum algorithms:

* crc32
* crc32c
* md5
* sha1
* sha256
* sha384
* sha512
* sha3_256

#### Current limitations

* It's very slow
* Inefficient - only uses a single thread