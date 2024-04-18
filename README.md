# Bitrot Fixer

If you have a file (jpeg, mp3, etc) which has become corrupted by [bit rot](https://en.wikipedia.org/wiki/Data_rot), and
you know what the correct checksum should be, this CLI project will try and flip every bit in the file until it finds
the one that is incorrect.

Runs the checksum operations across all available cores for speed improvements

### Args:<br>

Mandatory: file to check<br>
Mandatory: correct checksum<br>
Optional: 1 or 2 flipped bits to try<br>

### Usage

```bash
./mvn clean package
java -jar target/bitrot-fixer-*.jar /path/to/file md5:bc838b7c7e59458afd78c94db41203f5 1
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

* Trying two bit-flips takes a very long time on large files, due to all the possible combinations (<filesize>²)