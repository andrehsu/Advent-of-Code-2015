import java.util.*;

/**
 * Created by Andre on 12/6/2016.
 */
public class BitwiseLogicBoard {
	public static final List<String> input = Input.readAllLines("Day 7/input.txt");
	
	private static final String AND = "AND", LSHIFT = "LSHIFT", NOT = "NOT", OR = "OR", RSHIFT = "RSHIFT";
	
	private final Map<String, Integer> wireValues = new HashMap<>();
	private final Map<String, String> connections = new HashMap<>();
	
	public BitwiseLogicBoard(List<String> instructions) {
		for (String instruction : instructions) {
			String[] tokens = splitTrim(instruction, "->");
			
			String key = tokens[1];
			
			if (isInteger(tokens[0]))
				wireValues.put(key, Integer.parseInt(tokens[0]));
			else
				wireValues.put(key, null);
			
			connections.put(key, tokens[0]);
		}
	}
	
	public void overrideValue(String key, int value){
		wireValues.put(key,value);
	}
	
	public int getSignalValue(String identifier) {
		if (wireValues.get(identifier) != null) {
			return wireValues.get(identifier);
		} else if (isInteger(identifier)) {
			return Integer.parseInt(identifier);
		}
		
		String connectionTo = connections.get(identifier);
		if (connectionTo.contains(AND)) {
			String[] tokens = splitTrim(connectionTo, AND);
			wireValues.put(identifier, getSignalValue(tokens[0]) & getSignalValue(tokens[1]));
		} else if (connectionTo.contains(OR)) {
			String[] tokens = splitTrim(connectionTo, OR);
			wireValues.put(identifier, getSignalValue(tokens[0]) | getSignalValue(tokens[1]));
		} else if (connectionTo.contains(LSHIFT)) {
			String[] tokens = splitTrim(connectionTo, LSHIFT);
			wireValues.put(identifier, getSignalValue(tokens[0]) << getSignalValue(tokens[1]));
		} else if (connectionTo.contains(RSHIFT)) {
			String[] tokens = splitTrim(connectionTo, RSHIFT);
			wireValues.put(identifier, getSignalValue(tokens[0]) >> getSignalValue(tokens[1]));
		} else if (connectionTo.contains(NOT)) {
			String[] tokens = splitTrim(connectionTo, NOT);
			wireValues.put(identifier, ~getSignalValue(tokens[1]));
		} else {
			return getSignalValue(connectionTo);
		}
		
		return wireValues.get(identifier);
	}
	
	private static String[] splitTrim(String string, String regex) {
		String[] output = string.split(regex);
		for (int i = 0; i < output.length; i++) {
			output[i] = output[i].trim();
		}
		
		return output;
	}
	
	private static boolean isInteger(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	private static class Test {
		public static void main(String[] args) {
			String[] tokens = splitTrim("NOT gs", NOT);
			System.out.println(Arrays.toString(tokens));
		}
	}
}

class RunDay7_Part1 {
	public static void main(String[] args) {
		BitwiseLogicBoard board = new BitwiseLogicBoard(BitwiseLogicBoard.input);
		System.out.println(board.getSignalValue("a"));
	}
}

class RunDay7_Part2{
	public static void main(String[] args){
		BitwiseLogicBoard board = new BitwiseLogicBoard(BitwiseLogicBoard.input);
		board.overrideValue("b", 956);
		System.out.println(board.getSignalValue("a"));
	}
}