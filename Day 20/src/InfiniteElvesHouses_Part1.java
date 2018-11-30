import java.util.function.Function;

/**
 * Created by Andre on 12/26/2016.
 */
public class InfiniteElvesHouses_Part1 {
	public static final int input = 36_000_000;
	public static final Function<Integer, Integer> divisorsSum = input -> {
		int sum = 0;
		
		for (int i = 1; i <= (int) Math.sqrt(input); i++) {
			if (input % i == 0) {
				sum += i + input / i;
			}
		}
		
		return sum;
	};
	
	public static int findHouseWithMinimum(final int minimumPresents) {
		for (int houseNumber = 0; houseNumber < Integer.MAX_VALUE; houseNumber++) {
			if (divisorsSum.apply(houseNumber) >= minimumPresents / 10) {
				return houseNumber;
			}
		}
		
		return -1;
	}
}

class RunDay20_Part1 {
	public static void main(String[] args) {
		System.out.println(InfiniteElvesHouses_Part1.findHouseWithMinimum(InfiniteElvesHouses_Part1.input));
	}
}