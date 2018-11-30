


import org.javatuples.Pair;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Andre on 1/29/2017.
 */
public class PresentDelivery {
	public static final String input = Input.readFirstLine("Day 3/input.txt");
	
	public static int uniqueHouses(String input) {
		Set<Pair<Integer, Integer>> unique = new HashSet<>();
		int x, y;
		x = y = 0;
		
		unique.add(Pair.with(x, y));
		for (char c : input.toCharArray()) {
			Pair<Integer, Integer> result = resultOf(c, x, y);
			x = result.getValue0();
			y = result.getValue1();
			unique.add(result);
		}
		return unique.size();
	}
	
	public static int uniqueHousesWithRobot(String input) {
		Set<Pair<Integer, Integer>> unique = new HashSet<>();
		
		int xSanta, ySanta, xRobot, yRobot;
		xSanta = ySanta = xRobot = yRobot = 0;
		
		unique.add(Pair.with(xSanta, ySanta));
		unique.add(Pair.with(xRobot, yRobot));
		for (int i = 0; i < input.length(); i += 2) {
			Pair<Integer, Integer> result = resultOf(input.charAt(i), xSanta, ySanta);
			xSanta = result.getValue0();
			ySanta = result.getValue1();
			unique.add(result);
			result = resultOf(input.charAt(i + 1), xRobot, yRobot);
			xRobot = result.getValue0();
			yRobot = result.getValue1();
			unique.add(result);
		}
		return unique.size();
	}
	
	private static Pair<Integer, Integer> resultOf(char c, int x, int y) {
		switch (c) {
			case '^':
				return Pair.with(x, y + 1);
			case 'v':
				return Pair.with(x, y - 1);
			case '>':
				return Pair.with(x + 1, y);
			case '<':
				return Pair.with(x - 1, y);
			default:
				return Pair.with(null, null);
		}
	}
}

class RunDay3_Part1 {
	public static void main(String[] args) {
		System.out.println(PresentDelivery.uniqueHouses(PresentDelivery.input));
	}
}

class RunDay3_Part2{
	public static void main(String[] args){
		System.out.println(PresentDelivery.uniqueHousesWithRobot(PresentDelivery.input));
	}
}