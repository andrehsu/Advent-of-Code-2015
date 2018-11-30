import java.util.*;

/**
 * Created by Andre on 12/30/2016.
 */
public class StateOfTheArtComputer {
	public static final List<String> input = Input.readAllLines("Day 23/input.txt");
	
	public static Map<String, Integer> interpret(final List<String> code) {
		return interpret(code, 0, 0);
	}
	
	public static Map<String, Integer> interpret(final List<String> code, int initialA, int initialB) {
		// Improve get operation speed
		final List<List<String>> tokenCode = new ArrayList<>();
		for (String s : code) {
			tokenCode.add(Arrays.asList(s.trim().split(",? +")));
		}
		
		// Register registers
		final Map<String, Integer> registers = new HashMap<>();
		registers.put("a", initialA);
		registers.put("b", initialB);
		
		for (int i = 0; i < tokenCode.size(); i++) {
			List<String> line = tokenCode.get(i);
			switch (line.get(0)) {
				case "hlf":
					registers.compute(line.get(1), (s, integer) -> integer / 2);
					break;
				case "tpl":
					registers.compute(line.get(1), (s, integer) -> integer * 3);
					break;
				case "inc":
					registers.compute(line.get(1), (s, integer) -> integer + 1);
					break;
				case "jmp":
					i += Integer.parseInt(line.get(1)) - 1;
					break;
				case "jie":
					if (registers.get(line.get(1)) % 2 == 0)
						i += Integer.parseInt(line.get(2)) - 1;
					break;
				case "jio":
					if (registers.get(line.get(1)) == 1)
						i += Integer.parseInt(line.get(2)) - 1;
					break;
				default:
					throw new RuntimeException(String.format("Unexpected instruction found (%s)", tokenCode.get(0)));
			}
		}
		
		return registers;
	}
	
	private static class Test {
		public static final List<String> testInput = Input.readAllLines("Day 23/test input.txt");
		
		public static void main(String[] args) {
			System.out.println(interpret(testInput).get("a"));
		}
	}
}

class RunDay23_Part1 {
	public static void main(String[] args) {
		System.out.println(StateOfTheArtComputer.interpret(StateOfTheArtComputer.input).get("b"));
	}
}

class RunDay23_Part2 {
	public static void main(String[] args) {
		System.out.println(StateOfTheArtComputer.interpret(StateOfTheArtComputer.input, 1, 0).get("b"));
	}
}