# Bitrot Fixer

If you have a file (jpeg, mp3, etc) which has become corrupted by [bit rot](https://en.wikipedia.org/wiki/Data_rot), and you know what the correct checksum should
be, this project will try and flip every bit in the file until it finds the one that is incorrect.

### Usage

```bash
./mvn clean package
java -jar target/bitrot-fixer-*.jar /path/to/file md5:bc838b7c7e59458afd78c94db41203f5
```

#### Currently supported checksum algorithms:

* md5
* sha1
* sha256
* sha384
* sha512
* sha3_256


#### Current limitations

* It's very slow
* Inefficient - only uses a single thread
* Only works with 1 invalid bit at the moment