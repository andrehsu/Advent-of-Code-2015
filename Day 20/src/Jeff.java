import java.util.function.Function;

/**
 * Created by Andre on 12/27/2016.
 */
public class Jeff {
	public static final int input = 36_000_000;
	public static final Function<Integer, Integer> divisorsSum = input -> {
		int sum = 0;
		
		for (int i = 1; i <= (int) Math.sqrt(input); i++) {
			if ((input % i == 0) && (i <= 50)) {
				sum += (i + input / i) * 11;
			}
		}
		
		return sum;
	};
	
	public static int findHouseWithMinimum(final int minimumPresents) {
		
		
		for (int houseNumber = 0; houseNumber < Integer.MAX_VALUE; houseNumber++) {
			if (divisorsSum.apply(houseNumber) >= minimumPresents) {
				return houseNumber;
			}
		}
		
		return -1;
	}
}


class RunJeff {
	public static void main(String[] args) {
		System.out.println(Jeff.findHouseWithMinimum(InfiniteElvesHouses_Part1.input));
	}
}