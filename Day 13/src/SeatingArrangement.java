import sun.reflect.generics.tree.Tree;

import java.util.*;

/**
 * Created by andre on 12/18/2016.
 */
public class SeatingArrangement {
	public static final List<String> input = Input.readAllLines("Day 13/input.txt");
	
	private Set<String> guests = new TreeSet<>();
	private final Map<LinkedList<String>, Integer> seatingArrangements = new HashMap<>();
	private final Map<String, Map<String, Integer>> happinessIndex = new TreeMap<>();
	
	public Map.Entry<LinkedList<String>, Integer> getOptimal() {
		List<Map.Entry<LinkedList<String>, Integer>> sortedList = new ArrayList<>(seatingArrangements.entrySet());
		Collections.sort(sortedList, (o1, o2) -> {
			@SuppressWarnings("unchecked")
			Map.Entry<LinkedList<String>, Integer> entry1 = o1, entry2 = o2;
			return Integer.compare(entry1.getValue(), entry2.getValue());
		});
		
		return sortedList.get(sortedList.size() - 1);
	}
	
	private int getHappiness(String person, String sittingNextTo) {
		try {
			Integer result = happinessIndex.get(person).get(sittingNextTo);
			result += happinessIndex.get(sittingNextTo).get(person);
			return result;
		} catch (NullPointerException e) {
			return 0;
		}
	}
	
	public SeatingArrangement(List<String> input) {
		for (String s : input) {
			s = s.replaceAll("\\bwould\\b\\s|\\bhappiness units by sitting next to\\b |\\.|\\bgain\\b ", "")
					.replaceAll("\\blose\\b ", "-");
			String[] tokens = s.split("\\s+");
			String firstPerson = tokens[0], secondPerson = tokens[2];
			int deltaHappiness = Integer.parseInt(tokens[1]);
			
			happinessIndex.compute(firstPerson, (s1, stringIntegerMap) -> {
				if (stringIntegerMap == null) {
					stringIntegerMap = new TreeMap<String, Integer>();
					stringIntegerMap.put(secondPerson, deltaHappiness);
				} else {
					stringIntegerMap.put(secondPerson, deltaHappiness);
				}
				return stringIntegerMap;
			});
			
			guests.add(firstPerson);
			guests.add(secondPerson);
		}
		guests = Collections.unmodifiableSet(guests);
	}
	
	public SeatingArrangement(List<String> input, boolean hasYou){
		this(input);
		Set<String> newGuestList = new TreeSet<>(guests);
		newGuestList.add("you");
		guests = Collections.unmodifiableSet(newGuestList);
	}
	
	public void run() {
		node(new LinkedList<>(), new TreeSet<>(guests));
	}
	
	private void node(LinkedList<String> currentArrangement, TreeSet<String> remainingGuests) {
		if (remainingGuests.size() == 0) {
			seatingArrangements.put(currentArrangement, calculateArrangementHappiness(currentArrangement));
			return;
		}
		
		for (String guest : new HashSet<>(remainingGuests)) {
			LinkedList<String> nextNodeCurrentArrangement = new LinkedList<>(currentArrangement);
			TreeSet<String> nextNodeRemainingGuests= new TreeSet<>(remainingGuests);
			nextNodeCurrentArrangement.add(guest);
			nextNodeRemainingGuests.remove(guest);
			
			node(new LinkedList<>(nextNodeCurrentArrangement), new TreeSet<>(nextNodeRemainingGuests));
		}
	}
	
	private int calculateArrangementHappiness(LinkedList<String> arrangement) {
		int sumHappiness = 0;
		
		List<String> arrayRepresentation = new ArrayList<>(arrangement);
		for (int i = 0; i < arrangement.size() - 1; i++) {
			sumHappiness += getHappiness(arrayRepresentation.get(i), arrayRepresentation.get(i + 1));
		}
		sumHappiness += getHappiness(arrayRepresentation.get(0), arrayRepresentation.get(arrayRepresentation.size() - 1));
		
		return sumHappiness;
	}
	
	private static class Test {
		public static void main(String[] args) {
			List<String> testInput = Input.readAllLines("Day 13/test input.txt");
			
			SeatingArrangement arranger = new SeatingArrangement(testInput);
			arranger.run();
			System.out.println(arranger.getOptimal().getValue());
		}
	}
}

class RunDay13_Part1 {
	public static void main(String[] args) {
		SeatingArrangement arranger = new SeatingArrangement(SeatingArrangement.input);
		arranger.run();
		System.out.println(arranger.getOptimal().getValue());
	}
}

class RunDay13_Part2{
	public static void main(String[] args){
		SeatingArrangement arranger = new SeatingArrangement(SeatingArrangement.input,true);
		arranger.run();
		System.out.println(arranger.getOptimal().getValue());
	}
}