import java.math.BigInteger;
import java.util.*;

/**
 * Created by Andre on 12/30/2016.
 */
public class PresentOnSleigh {
	public static final List<String> input = Input.readAllLines("Day 24/input.txt");
	
	private final Set<Integer> presents;
	private final int requiredSize,
			parts;
	
	private BigInteger quantumEntanglement = null;
	
	public BigInteger getQuantumEntanglement() {
		return quantumEntanglement == null ? BigInteger.valueOf(-1) : quantumEntanglement;
	}
	
	public PresentOnSleigh(List<String> input) {
		this(input, 3);
	}
	
	public PresentOnSleigh(List<String> input, int parts) {
		Set<Integer> presents_temp = new HashSet<>();
		input.forEach(s -> presents_temp.add(Integer.parseInt(s)));
		presents = Collections.unmodifiableSet(presents_temp);
		requiredSize = sum(presents) / parts;
		this.parts = parts;
	}
	
	public void run() {
		for (int i = 1; i < presents.size() - (parts - 1); i++) {
			//System.out.println(i); // To check that the algorithm is moving/ working
			Set<Set<Integer>> validBag1AtSize = new HashSet<>();
			
			List<Set<Integer>> bag1Combinations = combinations(presents, i);
			for (Set<Integer> bag1 : bag1Combinations) {
				if (sum(bag1) == requiredSize) {
					validBag1AtSize.add(bag1);
				}
			}
			
			if (validBag1AtSize.size() > 0) {
				for (Set<Integer> integers : validBag1AtSize) {
					if (quantumEntanglement == null || multiply(integers).compareTo(quantumEntanglement) < 0) {
						quantumEntanglement = multiply(integers);
					}
				}
				break;
			}
		}
	}
	
	private static Set<List<Integer>> permutations(Set<Integer> integers) {
		Set<List<Integer>> output = new HashSet<>();
		permutations_node(output, new LinkedList<>(), integers);
		return output;
	}
	
	private static void permutations_node(Set<List<Integer>> output, LinkedList<Integer> soFar, Set<Integer> remaining) {
		if (remaining.size() == 0) {
			output.add(soFar);
		} else {
			for (Integer integer : remaining) {
				LinkedList<Integer> newSoFar = new LinkedList<>(soFar);
				Set<Integer> newRemaining = new HashSet<>(remaining);
				newSoFar.add(integer);
				newRemaining.remove(integer);
				permutations_node(output, newSoFar, newRemaining);
			}
		}
	}
	
	private static BigInteger multiply(Collection<Integer> bag) {
		if (bag.size() == 0) return BigInteger.ZERO;
		
		BigInteger product = BigInteger.ONE;
		for (int integer : bag) {
			product = product.multiply(BigInteger.valueOf(integer));
		}
		return product;
	}
	
	private static int sum(Collection<Integer> collection) {
		int sum = 0;
		for (Integer integer : collection) {
			sum += integer;
		}
		return sum;
	}
	
	private static List<Set<Integer>> combinations(Set<Integer> set, final int size) {
		List<Set<Integer>> output = new LinkedList<>();
		combinations_node(output, new ArrayList<>(set), new LinkedList<>(), size);
		return output;
	}
	
	private static void combinations_node(List<Set<Integer>> output,
	                                      List<Integer> numbers,
	                                      List<Integer> numbersToUse,
	                                      final int size) {
		if (size - numbersToUse.size() == 0) {
			Set<Integer> toAdd = new HashSet<>();
			for (Integer integer : numbersToUse) {
				toAdd.add(numbers.get(integer));
			}
			output.add(toAdd);
		} else {
			for (int i = numbersToUse.size() == 0 ? 0 : numbersToUse.get(numbersToUse.size() - 1) + 1;
			     i < numbers.size() - size + numbersToUse.size() + 1;
			     i++) {
				List<Integer> newNumbersToUse = new LinkedList<>(numbersToUse);
				newNumbersToUse.add(i);
				combinations_node(output, numbers, newNumbersToUse, size);
			}
		}
	}
	
	private static class Test {
		private static final List<String> testInput = Arrays.asList("1", "2", "3", "4", "5", "7", "8", "9", "10", "11");
		
		public static void main(String[] args) {
			PresentOnSleigh presentOnSleigh = new PresentOnSleigh(testInput);
			presentOnSleigh.run();
			System.out.println(presentOnSleigh.getQuantumEntanglement());
			
			PresentOnSleigh presentOnSleigh2 = new PresentOnSleigh(testInput,4);
			presentOnSleigh2.run();
			System.out.println(presentOnSleigh2.getQuantumEntanglement());
		}
	}
	
	private static class Test_Combination {
		public static void main(String[] args) {
			Set<Integer> set = new HashSet<>();
			for (int i = 0; i < 23; i++) {
				set.add(i + 1);
			}
			System.out.println(combinations(set, 5).size());
		}
	}
}

class RunDay24_Part1 {
	public static void main(String[] args) {
		PresentOnSleigh presentOnSleigh = new PresentOnSleigh(PresentOnSleigh.input);
		presentOnSleigh.run();
		System.out.println(presentOnSleigh.getQuantumEntanglement());
	}
}

class RunDay24_Part2 {
	public static void main(String[] args) {
		PresentOnSleigh presentOnSleigh = new PresentOnSleigh(PresentOnSleigh.input, 4);
		presentOnSleigh.run();
		System.out.println(presentOnSleigh.getQuantumEntanglement());
	}
}