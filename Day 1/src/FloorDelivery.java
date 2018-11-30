/**
 * Created by Andre on 1/24/2017.
 */
public class FloorDelivery {
	public static final String input = Input.readFirstLine("Day 1/input.txt");
	
	public static int endFloor(String input) {
		int floor = 0;
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c == '(')
				floor++;
			else
				floor--;
		}
		return floor;
	}
	
	public static int whenEnterBasement(String input) {
		int floor = 0;
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c == '(')
				floor++;
			else
				floor--;
			if (floor == -1)
				return i + 1;
		}
		
		return -1;
	}
}

class RunDay1Part1 {
	public static void main(String... args) {
		System.out.println(FloorDelivery.endFloor(FloorDelivery.input));
	}
}

class RunDay1Part2{
	public static void main(String... args){
		System.out.println(FloorDelivery.whenEnterBasement(FloorDelivery.input));
	}
}