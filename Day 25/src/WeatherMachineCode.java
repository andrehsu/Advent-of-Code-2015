import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Andre on 1/2/2017.
 */
public class WeatherMachineCode {
	public static final int input_row = 2947,
			input_column = 3029;
	
	public static long codeAt(final int destinationRow,
	                          final int destinationColumn) {
		int row = 1, column = 1;
		long value = 20151125;
		
		while (!(destinationRow == row && destinationColumn == column)) {
			row--;
			column++;
			if (row < 1) {
				row = column;
				column = 1;
			}
			value = getNextNumber(value);
		}
		
		return value;
	}
	
	private static long getNextNumber(long input) {
		return (input * 252533) % 33554393;
	}
	
	private static class Test {
		public static void main(String[] args) {
			// print header
			System.out.printf("    |");
			for (int i = 1; i <= 19; i++) {
				System.out.printf("   %2d     ", i);
			}
			System.out.println();
			
			// print header and body divider
			System.out.printf("----+");
			for (int i = 0; i < 19; i++) {
				System.out.printf("---------+");
			}
			System.out.println();
			
			// print body
			for (int i = 1; i <= 99; i++) {
				System.out.printf(" %2d |", i);
				for (int j = 1; j <= 19; j++) {
					System.out.printf("%9d ", codeAt(i, j));
				}
				System.out.println();
			}
		}
	}
}

class RunDay25_Part1 {
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		System.out.println(WeatherMachineCode.codeAt(WeatherMachineCode.input_row, WeatherMachineCode.input_column));
		System.out.println(System.currentTimeMillis() - startTime + " ms");
	}
}