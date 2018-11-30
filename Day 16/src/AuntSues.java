import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Andre on 1/24/2017.
 */
public class AuntSues {
	public static final List<String> input = Input.readAllLines("Day 16/input.txt");
	
	public static final String[] regexRules = {
			regexOf("children", 3),
			regexOf("cats", 7),
			regexOf("samoyeds", 2),
			regexOf("pomeranians", 3),
			regexOf("akitas", 0),
			regexOf("vizslas", 0),
			regexOf("goldfish", 5),
			regexOf("trees", 3),
			regexOf("cars", 2),
			regexOf("perfumes", 1)
	};
	public static final String[] regexRulesPart2 = {
			regexOf("children", 3),
			regexOf("cats", 8, 9, 10),
			regexOf("samoyeds", 2),
			regexOf("pomeranians", 0, 1, 2),
			regexOf("akitas", 0),
			regexOf("vizslas", 0),
			regexOf("goldfish", 0, 1, 2, 3, 4),
			regexOf("trees", 4, 5, 6, 7, 8, 9, 10),
			regexOf("cars", 2),
			regexOf("perfumes", 1)
	};
	
	public static String filterSues(List<String> input, String[] regexRules) {
		input = new ArrayList<>(input);
		
		lines:
		for (Iterator<String> iterator = input.iterator(); iterator.hasNext(); ) {
			String line = iterator.next();
			for (String regexRule : regexRules) {
				if (line.matches(regexRule)) {
					iterator.remove();
					continue lines;
				}
			}
		}
		
		if (input.size() > 1) {
			System.out.printf("%d left, solution not optimal, they are:%n%s", input.size(), input);
			return "";
		} else if (input.size() == 0) {
			System.out.println("Zero left, no solution");
			return "";
		} else
			return input.remove(0);
	}
	
	private static String regexOf(String item, Integer... validValues) {
		Set<Integer> validValuesSet = Arrays.stream(validValues).collect(Collectors.toSet());
		
		// add invalidValues to string
		String regex_invalidValues = IntStream.rangeClosed(0, 10).filter(value -> !validValuesSet.contains(value)).mapToObj(value -> value + "|").reduce(String::concat).orElseThrow(RuntimeException::new);
		// remove last OR (|)
		regex_invalidValues = regex_invalidValues.substring(0, regex_invalidValues.length() - 1);
		
		return String.format(".*%s: \\b(%s)\\b.*", item, regex_invalidValues);
	}
	
	private static final class Test {
		public static void main(String... args) {
			System.out.println(regexOf("children", 3));
			System.out.println("Sue 1: children: 1, cars: 8, vizslas: 7".matches(regexOf("children", 3)));
		}
	}
}

class RunDay16Part1 {
	public static void main(String... args) {
		System.out.println(AuntSues.filterSues(AuntSues.input, AuntSues.regexRules));
	}
}

class RunDay16Part2 {
	public static void main(String... args) {
		System.out.println(AuntSues.filterSues(AuntSues.input, AuntSues.regexRulesPart2));
	}
}