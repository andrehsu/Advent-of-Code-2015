import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by Andre on 12/6/2016.
 */
public class NaughtyOrNice {
	public static final List<String> input = Input.readAllLines("Day 5/input.txt");
	
	public static int niceStrings(List<String> strings) {
			return (int) strings.parallelStream().filter(NaughtyOrNice::isNice).count();
	}
	
	private static boolean isNice(String string) {
		boolean hasThreeVowels = false, hasLetterTwiceInWord = false, hasNaughtyWords = false;
		
		String[] naughtyWords = new String[]{"ab", "cd", "pq", "xy"};
		for (String naughtyWord : naughtyWords) {
			if (string.contains(naughtyWord)) {
				hasNaughtyWords = true;
			}
		}
		
		int vowelsCount = 0;
		char lastChar = 0;
		for (int i = 0; i < string.length(); i++) {
			String c = String.valueOf(string.charAt(i));
			if (c.matches("[aeiou]")) {
				vowelsCount++;
			}
			if (c.charAt(0) == lastChar) {
				hasLetterTwiceInWord = true;
			}
			lastChar = c.charAt(0);
		}
		if (vowelsCount >= 3) {
			hasThreeVowels = true;
		}
		
		if (string.matches("(\\w{2})")) {
			hasLetterTwiceInWord = true;
		}
		
		return hasThreeVowels && hasLetterTwiceInWord && !hasNaughtyWords;
	}
	
	public static int niceStrings2(List<String> strings) {
		return (int) strings.parallelStream().filter(NaughtyOrNice::isNice2).count();
	}
	
	private static boolean isNice2(String string) {
		boolean hasLetterPairAppearTwice = false, repeatedWithLetterInBetween = false;
		
		if (string.matches("^.*?([a-z]{2}).*?(\\1).*$")) {
			hasLetterPairAppearTwice = true;
		}
		
		for (int i = 0; i < string.length() - 2; i++) {
			if (string.charAt(i) == string.charAt(i + 2)) {
				repeatedWithLetterInBetween = true;
			}
		}
		
		return hasLetterPairAppearTwice && repeatedWithLetterInBetween;
	}
}

class RunDay5_Part1 {
	public static void main(String[] args) {
		System.out.println(NaughtyOrNice.niceStrings(NaughtyOrNice.input));
	}
}

class RunDay5_Part2 {
	public static void main(String[] args) {
		System.out.println(NaughtyOrNice.niceStrings2(NaughtyOrNice.input));
	}
}