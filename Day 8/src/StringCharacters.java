import java.util.List;

/**
 * Created by andre on 12/18/2016.
 */
public class StringCharacters {
	public static final List<String> input = Input.readAllLines("Day 8/input.txt");
	
	public static int charactersInStringLiterals(String string) {
		return string.length();
	}
	
	public static int charactersInMemory(String string) {
		int length = 0;
		
		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			if (c == '"') {
				continue;
			} else if (c == '\\' && string.charAt(i + 1) == 'x') {
				length++;
				i += 3;
			} else if (c == '\\') {
				length++;
				i += 1;
			} else {
				length++;
			}
			
		}
		
		return length;
	}
	
	public static int charactersInCodeRepresentation(String string) {
		int length = 0;
		
		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			
			if (c == '\\' || c == '"') {
				length += 2;
			} else {
				length++;
			}
		}
		
		return length + 2; // plus two for the double quotes surrounding the string
	}
	
	private static class Test {
		public static void main(String[] args) {
			String test1 = "\"\"", test2 = "\"abc\"", test3 = "\"aaa\\\"aaa\"", test4 = "\"\\x27\"";
			
			System.out.println(charactersInStringLiterals(test1));
			System.out.println(charactersInMemory(test1));
			System.out.println(charactersInCodeRepresentation(test1));
			System.out.println();
			
			System.out.println(charactersInStringLiterals(test2));
			System.out.println(charactersInMemory(test2));
			System.out.println(charactersInCodeRepresentation(test2));
			System.out.println();
			
			System.out.println(charactersInStringLiterals(test3));
			System.out.println(charactersInMemory(test3));
			System.out.println(charactersInCodeRepresentation(test3));
			System.out.println();
			
			System.out.println(charactersInStringLiterals(test4));
			System.out.println(charactersInMemory(test4));
			System.out.println(charactersInCodeRepresentation(test4));
			System.out.println();
		}
	}
}

class RunDay8_Part1 {
	public static void main(String[] args) {
		int sum = 0;
		for (String s : StringCharacters.input) {
			sum += StringCharacters.charactersInStringLiterals(s) - StringCharacters.charactersInMemory(s);
		}
		
		System.out.println(sum);
	}
}

class RunDay8_Part2 {
	public static void main(String[] args) {
		int sum = 0;
		for (String s : StringCharacters.input) {
			sum += StringCharacters.charactersInCodeRepresentation(s) - StringCharacters.charactersInStringLiterals(s);
		}
		
		System.out.println(sum);
	}
}