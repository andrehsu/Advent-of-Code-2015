import java.util.*;
import java.util.Map.Entry;

/**
 * Created by andre on 12/18/2016.
 */
public class ReindeerRace {
	public static final List<String> input = Input.readAllLines("Day 14/input.txt");
	
	private static final boolean FLYING = true, RESTING = false;
	
	private final int winSecond;
	
	// Stats
	private final Set<String> names = new TreeSet<>();
	private final Map<String, Integer> speeds = new TreeMap<>();
	private final Map<String, Integer> flyingPeriods = new TreeMap<>();
	private final Map<String, Integer> restingPeriods = new TreeMap<>();
	
	// Variables
	private final Map<String, Boolean> states = new TreeMap<>();
	private final Map<String, Integer> timeRemainingInStates = new TreeMap<>();
	private final Map<String, Integer> distances = new TreeMap<>();
	private final Map<String, Integer> points = new TreeMap<>();
	
	public List<Entry<String, Integer>> getDistances() {
		List<Entry<String, Integer>> sortedList = new ArrayList<>(distances.entrySet());
		sortedList.sort(Comparator.comparingInt(Entry::getValue));
		return sortedList;
	}
	
	public List<Entry<String, Integer>> getPoints() {
		List<Entry<String, Integer>> sortedList = new ArrayList<>(points.entrySet());
		sortedList.sort(Comparator.comparingInt(Entry::getValue));
		return sortedList;
	}
	
	public ReindeerRace(List<String> input, int winSecond) {
		this.winSecond = winSecond;
		for (String s : input) {
			s = s.replaceAll("\\bcan\\b \\bfly\\b |\\bkm\\/s\\b \\bfor\\b |\\bseconds\\b, | seconds\\.|\\bbut then must rest for\\b ", "");
			String[] tokens = s.split(" ");
			String name = tokens[0];
			int speed = Integer.parseInt(tokens[1]), flyingPeriod = Integer.parseInt(tokens[2]), restingPeriod = Integer.parseInt(tokens[3]);
			
			names.add(name);
			speeds.put(name, speed);
			flyingPeriods.put(name, flyingPeriod);
			restingPeriods.put(name, restingPeriod);
		}
	}
	
	public void run() {
		// Initialize all states
		for (String name : names) {
			states.put(name, FLYING);
			timeRemainingInStates.put(name, flyingPeriods.get(name));
			distances.put(name, 0);
			points.put(name, 0);
		}
		for (int second = 1; second <= winSecond; second++) {
			for (String name : names) {
				if (timeRemainingInStates.get(name) == 0) {
					states.compute(name, (s, aBoolean) -> !aBoolean);
					if (states.get(name) == FLYING) {
						timeRemainingInStates.put(name, flyingPeriods.get(name));
					} else
						timeRemainingInStates.put(name, restingPeriods.get(name));
				}
				timeRemainingInStates.compute(name, (s, integer) -> integer - 1);
				if (states.get(name) == FLYING) {
					distances.compute(name, (s, integer) -> integer + speeds.get(name));
				}
			}
			
			int leadDistance = Integer.MIN_VALUE;
			for (int value : distances.values()) {
				if (value > leadDistance)
					leadDistance = value;
			}
			for (String name : names) {
				if (distances.get(name) == leadDistance) {
					points.compute(name, (s, integer) -> integer + 1);
				}
			}
		}
	}
	
	private static class Test {
		public static void main(String[] args) {
			List<String> testInput = Input.readAllLines("Day 14/test input.txt");
			ReindeerRace reindeerRace = new ReindeerRace(testInput, 1000);
			reindeerRace.run();
			System.out.println(reindeerRace.getDistances());
			System.out.println(reindeerRace.getPoints());
		}
	}
}

class RunDay14_Part1 {
	private static final int winSecond = 2503;
	
	public static void main(String[] args) {
		ReindeerRace reindeerRace = new ReindeerRace(ReindeerRace.input, winSecond);
		reindeerRace.run();
		System.out.println(reindeerRace.getDistances());
		System.out.println(reindeerRace.getPoints());
	}
}