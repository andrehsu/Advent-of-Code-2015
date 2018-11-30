import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * Created by Andre on 12/6/2016.
 */
class OtherLightDisplay {
	public static final List<String> input = Input.readAllLines("Day 6/input.txt");
	
	public static int countLightsAfter(List<String> input) {
		int[][] lights = new int[1000][1000];
		
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
							lights[r][c] += 2;
							break;
						case 1:
							lights[r][c]++;
							break;
						case -1:
							lights[r][c]--;
							if (lights[r][c] < 0)
								lights[r][c] = 0;
							break;
					}
				}
			}
		}
		return Arrays.stream(lights).flatMapToInt(Arrays::stream).sum();
	}
}

class RunDay6_Part2 {
	public static void main(String[] args) {
		System.out.println(OtherLightDisplay.countLightsAfter(OtherLightDisplay.input));
	}
}