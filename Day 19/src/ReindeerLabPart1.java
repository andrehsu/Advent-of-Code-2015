import java.util.*;

/**
 * Created by Andre on 12/25/2016.
 */
public class ReindeerLabPart1 {
	public static final List<String> input = Input.readAllLines("Day 19/input.txt");
	
	public static Set<String> initialConfiguration(final List<String> input) {
		return possibleConfigs(generateReplacementMap(input.subList(0, input.size() - 1)), input.get(input.size() - 1));
	}
	
	public static Set<String> possibleConfigs(final Map<String, Set<String>> moleculeReplacementMap, final String inputMolecule) {
		Set<String> output = new HashSet<>();
		
		for (Map.Entry<String, Set<String>> entry : moleculeReplacementMap.entrySet()) {
			String replaceMoleculeFrom = entry.getKey();
			Set<String> replaceMoleculeTo = entry.getValue();
			for (String replacement : replaceMoleculeTo) {
				int lastIndex = -1;
				while (true) {
					lastIndex = inputMolecule.indexOf(replaceMoleculeFrom, lastIndex + 1);
					StringBuilder sb = new StringBuilder(inputMolecule);
					if (lastIndex != -1) {
						output.add(sb.replace(lastIndex, lastIndex + replaceMoleculeFrom.length(), replacement).toString());
					} else
						break;
				}
			}
		}
		
		return output;
	}
	
	private static Map<String, Set<String>> generateReplacementMap(List<String> input) {
		Map<String, Set<String>> output = new HashMap<>();
		for (String line : input) {
			if (line.isEmpty()) continue;
			String[] tokens = line.split(" => ");
			output.compute(tokens[0], (s, strings) -> {
				if (strings == null) {
					strings = new HashSet<String>();
					strings.add(tokens[1]);
				} else
					strings.add(tokens[1]);
				
				return strings;
			});
		}
		
		return output;
	}
	
	
	private static class Test {
		public static final List<String> testInput = Input.readAllLines("Day 19/part 1 test input 1.txt"), testInput1 = Input.readAllLines("Day 19/part 1 test input 2.txt");
		
		public static void main(String[] args) {
			System.out.println(initialConfiguration(testInput));
			System.out.println(initialConfiguration(testInput1));
		}
	}
}

class RunDay19_Part1 {
	public static void main(String[] args) {
		System.out.println(ReindeerLabPart1.initialConfiguration(ReindeerLabPart2.input).size());
	}
}