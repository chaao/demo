package guava;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

/**
 * @author lichao
 * @date 2014年8月5日
 */
public class Hash {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		byte[] source = "abc".getBytes();

		HashCode murmur = Hashing.murmur3_128(0x1234ABCD).hashBytes(source);
		System.out.println(murmur.asLong());
		
		HashCode md5 = Hashing.md5().hashBytes(source);
		System.out.println(md5);

		HashCode sha1 = Hashing.sha1().hashBytes(source);
		System.out.println(sha1);

		HashCode crc = Hashing.crc32().hashBytes(source);
		System.out.println(crc.asInt());

		int bucket = Hashing.consistentHash(murmur, 18);
		System.out.println(bucket);
		

	}

}
