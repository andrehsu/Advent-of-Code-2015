import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by Andre on 12/6/2016.
 */
public class WrappingPresents {
	public static final List<String> input = Input.readAllLines("Day 2/input.txt");
	
	public static int paperNeeded(List<String> input) {
		int paperNeeded = 0;
		for (String dimension : input) {
			int[] lengths = Arrays.stream(dimension.split("x")).mapToInt(Integer::parseInt).toArray();
			
			int area1 = lengths[0] * lengths[1],
					area2 = lengths[1] * lengths[2],
					area3 = lengths[0] * lengths[2],
					smallestArea = Stream.of(area1, area2, area3).min(Integer::compareTo).orElse(-1);
			
			paperNeeded += 2 * (area1 + area2 + area3) + smallestArea;
		}
		
		return paperNeeded;
	}
	
	public static int ribbonNeeded(List<String> input) {
		int ribbonNeeded = 0;
		for (String dimension : input) {
			int[] lengths = Arrays.stream(dimension.split("x")).mapToInt(Integer::parseInt).toArray();
			
			int peri1 = (lengths[0] + lengths[1])*2,
					peri2 = (lengths[1] + lengths[2])*2,
					peri3 = (lengths[0] + lengths[2])*2;
			
			ribbonNeeded += (lengths[0] * lengths[1] * lengths[2]) + Stream.of(peri1, peri2, peri3).min(Integer::compareTo).orElse(-1);
		}
		
		return ribbonNeeded;
	}
}

class RunDay2_Part1 {
	public static void main(String[] args) {
		System.out.println(WrappingPresents.paperNeeded(WrappingPresents.input));
	}
}

class RunDay2_Part2 {
	public static void main(String[] args) {
		System.out.println(WrappingPresents.ribbonNeeded(WrappingPresents.input));
	}
}