import sun.plugin2.message.Message;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Andre on 12/6/2016.
 */
public class AdventCoins {
	public static final String input = "yzbqklnj";
	
	public static int calculate(String seed, int numOfZeroes) {
		System.out.printf("Calculating with \"%s\" as seed and %d zeroes%n", seed, numOfZeroes);
		StringBuilder zeroesSb = new StringBuilder();
		for (int i = 0; i < numOfZeroes; i++) {
			zeroesSb.append("0");
		}
		String zeroes = zeroesSb.toString();
		
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			String hashResult = DatatypeConverter.printHexBinary(md5.digest((seed + i).getBytes()));
			if (hashResult.substring(0, numOfZeroes).equals(zeroes)) {
				return i;
			}
		}
		
		return -1;
	}
}

class RunDay4_Part1 {
	public static void main(String[] args) {
		System.out.println(AdventCoins.calculate(AdventCoins.input, 5));
	}
}

class RunDay4_Part2 {
	public static void main(String[] args) {
		System.out.println(AdventCoins.calculate(AdventCoins.input, 6));
	}
}