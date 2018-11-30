import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;

/**
 * Created by Andre on 12/6/2016.
 */
class LightDisplay {
	public static final List<String> input = Input.readAllLines("Day 6/input.txt");
	
	public static int countLightsAfter(List<String> input) {
		boolean[][] lights = new boolean[1000][1000];
		
		for (String instruction : input) {
			String[] tokens = instruction.replace("turn ", "").split(",| ");
			Point start = new Point(parseInt(tokens[1]), parseInt(tokens[2])),
					end = new Point(parseInt(tokens[4]), parseInt(tokens[5]));
			int type = 0;
			if (instruction.contains("on"))
				type = 1;
			else if (instruction.contains("off"))
				type = -1;
			
			for (int r = start.x; r <= end.x; r++) {
				for (int c = start.y; c <= end.y; c++) {
					switch (type) {
						case 0:
							lights[r][c] = !lights[r][c];
							break;
						case 1:
							lights[r][c] = true;
							break;
						case -1:
							lights[r][c] = false;
							break;
					}
				}
			}
		}
		return (int) Arrays.stream(lights).flatMap(booleans -> IntStream.range(0, booleans.length).mapToObj(i -> booleans[i])).filter(Boolean::booleanValue).count();
	}
}

class RunDay6_Part1 {
	public static void main(String[] args) {
		System.out.println(LightDisplay.countLightsAfter(LightDisplay.input));
	}
}