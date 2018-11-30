import java.util.*;

/**
 * Created by Andre on 12/24/2016.
 */
public class EggnogContainersConfig implements Runnable {
	public static final List<String> input = Input.readAllLines("Day 17/input.txt");
	
	private final List<Integer> containers;
	private final LinkedList<LinkedList<Integer>> combinations = new LinkedList<>();
	private final int eggnogAmount;
	
	public EggnogContainersConfig(List<String> containers, int eggnogAmount) {
		List<Integer> containersTemp = new ArrayList<>(containers.size() + 1);
		for (String s : containers) {
			containersTemp.add(Integer.parseInt(s));
		}
		
		this.containers = Collections.unmodifiableList(containersTemp);
		this.eggnogAmount = eggnogAmount;
	}
	
	public int numOfCombinations() {
		return combinations.size();
	}
	
	public int numOfCombinationsWithMinimumContainers() {
		int minimumContainers = Integer.MAX_VALUE;
		for (LinkedList<Integer> combination : combinations) {
			if (combination.size() < minimumContainers) {
				minimumContainers = combination.size();
			}
		}
		
		int count = 0;
		for (LinkedList<Integer> combination : combinations) {
			if (combination.size() == minimumContainers) {
				count++;
			}
		}
		
		return count;
	}
	
	@Override
	public void run() {
		node(eggnogAmount, new LinkedList<>(containers), new LinkedList<>());
	}
	
	private void node(int remainingEggnog, LinkedList<Integer> remainingContainers, LinkedList<Integer> usedContainers) {
		int container = remainingContainers.remove();
		
		if (remainingContainers.size() == 0) {
			if (remainingEggnog == container) {
				usedContainers.add(container);
				combinations.add(usedContainers);
			} else if (remainingEggnog == 0)
				combinations.add(usedContainers);
			return;
		}
		
		node(remainingEggnog, new LinkedList<>(remainingContainers), new LinkedList<>(usedContainers));
		if (remainingEggnog >= container) {
			remainingEggnog -= container;
			usedContainers.add(container);
			node(remainingEggnog, new LinkedList<>(remainingContainers), new LinkedList<>(usedContainers));
		}
	}
	
	private static class Test {
		public static final List<String> testInput = Input.readAllLines("Day 17/test input.txt");
		
		public static void main(String[] args) {
			EggnogContainersConfig eggnogContainers = new EggnogContainersConfig(testInput, 25);
			eggnogContainers.run();
			System.out.println(eggnogContainers.numOfCombinations());
		}
	}
}

class RunDay17 {
	public static void main(String[] args) {
		EggnogContainersConfig eggnogContainers = new EggnogContainersConfig(EggnogContainersConfig.input, 150);
		eggnogContainers.run();
		System.out.println(eggnogContainers.numOfCombinations());
		System.out.println(eggnogContainers.numOfCombinationsWithMinimumContainers());
	}
}