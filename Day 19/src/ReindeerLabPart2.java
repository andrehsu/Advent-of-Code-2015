import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.istack.internal.NotNull;
import com.sun.org.apache.xpath.internal.operations.Mult;

import java.util.*;

/**
 * Created by Andre on 12/25/2016.
 */
public class ReindeerLabPart2 {
	
	public static final List<String> input = Input.readAllLines("Day 19/input.txt");
	private final String destinationMolecule;
	private final Multimap<String, String> replacementMap;
	
	private int steps = -1;
	
	public int getSteps() {
		return steps;
	}
	
	public ReindeerLabPart2(List<String> input) {
		input = new ArrayList<>(input);
		destinationMolecule = input.remove(input.size() - 2);
		replacementMap = generateReplacementMap(input);
	}
	
	public void run() {
		steps = 0;
		String molecule = destinationMolecule;
		while (!molecule.equals("e")) {
			steps++;
			Set<String> possibleMolecules = new HashSet<>();
			for (Map.Entry<String, String> entry : replacementMap.entries()) {
				int index = -1;
				while ((index = molecule.indexOf(entry.getKey(), index+1)) != -1) {
					possibleMolecules.add(new StringBuilder(molecule).replace(index, index + entry.getKey().length(), entry.getValue()).toString());
				}
			}
			molecule = possibleMolecules.stream().min(Comparator.comparingInt(String::length)).orElseThrow(RuntimeException::new);
		}
	}
	
	private static Multimap<String, String> generateReplacementMap(List<String> input) {
		Multimap<String, String> output = HashMultimap.create();
		for (String line : input) {
			String[] tokens = line.split(" => ");
			output.put(tokens[1], tokens[0]);
		}
		
		return output;
	}
}

class RunDay19_Part2 {
	public static void main(String[] args) {
		ReindeerLabPart2 reindeerLab = new ReindeerLabPart2(ReindeerLabPart2.input);
		reindeerLab.run();
		System.out.println(reindeerLab.getSteps());
	}
}