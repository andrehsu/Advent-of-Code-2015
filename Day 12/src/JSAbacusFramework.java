import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

/**
 * Created by andre on 12/18/2016.
 */
public class JSAbacusFramework {
	public static final String input = Input.readFirstLine("Day 12/input.txt");
	private static final Pattern numberPattern = Pattern.compile("-?\\d+");
	
	public static int sumNumbers(String input) {
		int sum = 0;
		
		Matcher matcher = numberPattern.matcher(input);
		while (matcher.find()) {
			sum += parseInt(matcher.group());
		}
		
		return sum;
	}
	
	public static int sumNumbersPart2(String input) {
		int sum = 0;
		
		if (isObject(input)) {
			String[] tokens = toTokens(input);
			boolean ignore = false;
			for (String token : tokens) {
				if (isItem(token) && token.contains("red")) {
					ignore = true;
				}
			}
			
			if (!ignore) {
				for (String token : tokens) {
					if (isItem(token)) {
						sum += sumNumbers(token);
					} else {
						sum += sumNumbersPart2(token);
					}
				}
			}
		} else {
			String[] tokens = toTokens(input);
			for (String token : tokens) {
				if (isItem(token)) {
					sum += sumNumbers(token);
				} else {
					sum += sumNumbersPart2(token);
				}
			}
		}
		
		return sum;
	}
	
	
	private static boolean isArray(String string) {
		return string.matches(" *\\[.*\\] *");
	}
	
	private static boolean isObject(String string) {
		return string.matches(" *\\{.*\\} *");
	}
	
	private static boolean isItem(String string) {
		return !isObject(string) && !isArray(string);
	}
	
	private static String[] toTokens(String input) {
		StringBuilder sb = new StringBuilder(input);
		
		int level = 0;
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			
			if ((c == ',' || c == ':') && level == 1) {
				sb.replace(i, i + 1, String.valueOf((char) 0));
			} else if (c == '{' || c == '[') {
				level++;
			} else if (c == '}' || c == ']') {
				level--;
			}
		}
		
		return sb.toString().substring(1, sb.length() - 1).split(String.valueOf((char) 0));
	}
	
	private static class Test {
		public static void main(String[] args) {
			System.out.println(sumNumbers("{\"a\":2,\"b\":4}"));
		}
	}
	
	private static class TestPart2 {
		public static void main(String[] args) {
			System.out.println(toTokens("{\"c\":\"red\",\"b\":2}"));
			System.out.println("\"red\"".matches("red"));
			System.out.println(sumNumbersPart2("[1,2,3]"));
			System.out.println(sumNumbersPart2("[1,{\"c\":\"red\",\"b\":2},3]"));
			System.out.println(sumNumbersPart2("{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}"));
			System.out.println(sumNumbersPart2("[1,\"red\",5]"));
		}
	}
}

class RunDay12_Part1 {
	public static void main(String[] args) {
		System.out.println(JSAbacusFramework.sumNumbers(JSAbacusFramework.input));
	}
}

class RunDay12_Part2 {
	public static void main(String[] args) {
		System.out.println(JSAbacusFramework.sumNumbersPart2(JSAbacusFramework.input));
	}
}