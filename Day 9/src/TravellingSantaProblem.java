import java.nio.file.Files;
import java.util.*;

/**
 * Created by andre on 12/18/2016.
 */
public class TravellingSantaProblem {
	public static final List<String> input = Input.readAllLines("Day 9/input.txt");
	
	private final Map<String, Map<String, Integer>> adjacencyList;
	private final Map<String, Integer> paths = new TreeMap<>();
	private final int totalLocations;
	
	public TravellingSantaProblem(List<String> distanceList) {
		adjacencyList = new HashMap<>();
		Set<String> locations = new HashSet<>();
		for (String string : distanceList) {
			String[] tokens = string.trim().split("\\s+=\\s+|\\s+to\\s+");
			String location1 = tokens[0], location2 = tokens[1];
			int distance = Integer.parseInt(tokens[2]);
			
			locations.add(location1);
			locations.add(location2);
			
			adjacencyList.compute(location1, (s, stringIntegerMap) -> {
				if (stringIntegerMap == null) {
					stringIntegerMap = new HashMap<>();
					stringIntegerMap.put(location2, distance);
				} else {
					stringIntegerMap.put(location2, distance);
				}
				return stringIntegerMap;
			});
			
			adjacencyList.compute(location2, (s, stringIntegerMap) -> {
				if (stringIntegerMap == null) {
					stringIntegerMap = new HashMap<>();
					stringIntegerMap.put(location1, distance);
				} else {
					stringIntegerMap.put(location1, distance);
				}
				return stringIntegerMap;
			});
		}
		
		totalLocations = locations.size();
	}
	
	private int getDistance(String from, String to) {
		return adjacencyList.get(from).get(to);
	}
	
	private void addSolution(String solution, int distance) {
		paths.put(solution, distance);
	}
	
	private Set<String> getLocations(String from) {
		return new HashSet<>(adjacencyList.get(from).keySet());
	}
	
	private Set<String> getStartingLocations() {
		return new HashSet<>(adjacencyList.keySet());
	}
	
	public Map.Entry<String, Integer> shortestPath() {
		int shortestPathDistance = Integer.MAX_VALUE;
		Map.Entry<String, Integer> shortestEntry = null;
		for (Map.Entry<String, Integer> stringIntegerEntry : paths.entrySet()) {
			if (stringIntegerEntry.getValue() < shortestPathDistance) {
				shortestEntry = stringIntegerEntry;
				shortestPathDistance = stringIntegerEntry.getValue();
			}
		}
		
		return shortestEntry;
	}
	
	public Map.Entry<String,Integer> longestPath(){
		int longestPathDistance = Integer.MIN_VALUE;
		Map.Entry<String, Integer> longestEntry = null;
		for (Map.Entry<String, Integer> entry : paths.entrySet()) {
			if (entry.getValue() > longestPathDistance) {
				longestEntry = entry;
				longestPathDistance = entry.getValue();
			}
		}
		
		return longestEntry;
	}
	
	public void run() {
		for (String startingLocation : getStartingLocations()) {
			Set<String> travelledLocation = new LinkedHashSet<>();
			travelledLocation.add(startingLocation);
			for (String to : getLocations(startingLocation)) {
				node(startingLocation, to, 0, new LinkedHashSet<>(travelledLocation));
			}
		}
	}
	
	private void node(String previous, String current, int distance, Set<String> travelledLocations) {
		if (travelledLocations.contains(current)) {
			return;
		}
		
		travelledLocations.add(current);
		distance += getDistance(previous, current);
		
		if (travelledLocations.size() == totalLocations) {
			StringBuilder solution = new StringBuilder();
			for (String travelledLocation : travelledLocations) {
				solution.append(" -> ").append(travelledLocation);
			}
			addSolution(solution.toString().substring(4), distance);
		}
		
		try {
			for (String to : getLocations(current)) {
				node(current, to, distance, new LinkedHashSet<>(travelledLocations));
			}
		} catch (NullPointerException e) {
			return;
		}
	}
	
	private static class Test {
		public static void main(String[] args) {
			List<String> testInput = Input.readAllLines("Day 9/testInput.txt");
			
			TravellingSantaProblem tsp = new TravellingSantaProblem(testInput);
			tsp.run();
			System.out.println(tsp.shortestPath().getValue());
			
			
		}
	}
}

class RunDay9_Part1 {
	public static void main(String[] args) {
		TravellingSantaProblem tsp = new TravellingSantaProblem(TravellingSantaProblem.input);
		tsp.run();
		System.out.println(tsp.shortestPath().getValue());
	}
}

class RunDay9_Part2{
	public static void main(String[] args){
		TravellingSantaProblem tsp = new TravellingSantaProblem(TravellingSantaProblem.input);
		tsp.run();
		System.out.println(tsp.longestPath().getValue());
	}
}