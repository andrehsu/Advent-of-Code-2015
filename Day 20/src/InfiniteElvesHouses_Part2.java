import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andre on 12/26/2016.
 */
public class InfiniteElvesHouses_Part2 {
	public static final int input = 36_000_000;
	
	public static int findHouseWithMinimum(final int minimumPresents) {
		int[] houses = new int[1_000_000];
		
		for (int elf = 1; elf < houses.length; elf++) {
			for (int i_house = elf, count = 0; i_house < houses.length && count < 50; i_house += elf, count++) {
				houses[i_house] += elf * 11;
			}
		}
		
		for (int i = 0; i < houses.length; i++) {
			if (houses[i] >= minimumPresents) {
				return i;
			}
		}
		
		return -1;
	}
	
	private static class Test {
		public static void main(String[] args) {
			System.out.println(findHouseWithMinimum(60));
		}
	}
}

class RunDay20_Part2 {
	public static void main(String[] args) {
		System.out.println(InfiniteElvesHouses_Part2.findHouseWithMinimum(InfiniteElvesHouses_Part2.input));
	}
}