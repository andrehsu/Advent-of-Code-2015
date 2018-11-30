import java.util.LinkedHashMap;

/**
 * Created by andre on 12/18/2016.
 */
public class LookAndSay {
	public static final String input = "1113122113";
	
	private LookAndSay() {
		
	}
	
	public static String lookAndSay(String input, int times) {
		if (input.length() == 0)
			return input;
		
		StringBuilder sb = new StringBuilder(input);
		for (int counter = 0; counter < times; counter++) {
			StringBuilder output = new StringBuilder();
			char currentChar = (char) 0;
			int count = 0;
			for (int i = 0; i < sb.length(); i++) {
				char c = sb.charAt(i);
				if (c != currentChar) {
					if (currentChar != (char) 0) output.append(count).append(currentChar);
					currentChar = c;
					count = 1;
				} else
					count++;
			}
			output.append(count).append(currentChar);
			sb = output;
		}
		
		return sb.toString();
	}
	
	private static class Test {
		public static void main(String[] args) {
			System.out.println(lookAndSay("1", 1));
			System.out.println(lookAndSay("1",3));
			System.out.println(lookAndSay(input,60));
		}
	}
}

class RunDay10_Part1 {
	public static void main(String[] args) {
		System.out.println(LookAndSay.lookAndSay(LookAndSay.input,40).length());
	}
}

class RunDay10_Part2{
	public static void main(String[] args){
		System.out.println(LookAndSay.lookAndSay(LookAndSay.input,50).length());
	}
}