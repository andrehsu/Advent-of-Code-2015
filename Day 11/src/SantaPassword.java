/**
 * Created by andre on 12/18/2016.
 */
public class SantaPassword {
	public static final String input = "cqjxjnds";
	
	private static String increment(String string) {
		char[] chars = string.toCharArray();
		
		chars[string.length() - 1] = ((char) (chars[string.length() - 1] + 1));
		for (int i = string.length() - 2; i >= 0; i--) {
			if (chars[i + 1] > 'z') {
				chars[i + 1] = 'a';
				chars[i] = ((char) (chars[i] + 1));
			}
		}
		
		if (chars[0] == 'z' + 1)
			return "Out of bounds";
		
		return new String(chars);
	}
	
	private static boolean isValid(String string) {
		boolean part1 = false, part2 = false, part3 = false;
		
		if (string.length() < 3) {
			return false;
		}
		
		for (int i = 0; i < string.length() - 3; i++) {
			if (string.charAt(i) == string.charAt(i + 1) - 1 && string.charAt(i + 1) - 1 == string.charAt(i + 2) - 2) {
				part1 = true;
			}
		}
		
		if (!(string.contains("i") || string.contains("o") || string.contains("l")))
			part2 = true;
		
		char firstMatch = 0;
		for (int i = 0; i < string.length() - 1; i++) {
			if (string.charAt(i) == string.charAt(i + 1) && firstMatch == 0) {
				firstMatch = string.charAt(i);
			}
			if (string.charAt(i) == string.charAt(i + 1) && string.charAt(i) != firstMatch) {
				part3 = true;
			}
		}
		
		return part1 && part2 && part3;
	}
	
	public static String nextValidPassword(String string) {
		while (true) {
			string = increment(string);
			if (isValid(string))
				return string;
		}
	}
	
	private static class Test {
		public static void main(String[] args) {
			System.out.println(increment("abc"));
			System.out.println(increment("zzz"));
			System.out.println(isValid("hijklmmn"));
			System.out.println(isValid("abbceffg"));
		}
	}
}

class RunDay11_Part1 {
	public static void main(String[] args) {
		System.out.println(SantaPassword.nextValidPassword(SantaPassword.input));
	}
}

class RunPart11_Part2 {
	public static final String part1Result = "cqjxxyzz";
	
	public static void main(String[] args) {
		System.out.println(SantaPassword.nextValidPassword(part1Result));
	}
}