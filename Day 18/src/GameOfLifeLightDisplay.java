import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Andre on 12/24/2016.
 */
public class GameOfLifeLightDisplay {
	public static final List<String> input = Input.readAllLines("Day 18/input.txt");
	
	private final int rows, columns;
	private final boolean cornersOn;
	
	private boolean[][] lightDisplay;
	
	//<editor-fold desc="Constructors and creators">
	private GameOfLifeLightDisplay(boolean[][] lightDisplay, int rows, int columns, boolean cornersOn) {
		this.lightDisplay = lightDisplay;
		this.rows = rows;
		this.columns = columns;
		this.cornersOn = cornersOn;
	}
	
	public static GameOfLifeLightDisplay create(List<String> input) {
		return create(input, false);
	}
	
	public static GameOfLifeLightDisplay create(List<String> input, boolean cornersOn) {
		int rows = input.size();
		int columns = input.get(0).length();
		
		boolean[][] lightDisplay = new boolean[rows + 2][columns + 2];
		
		for (int r = 1; r <= 100; r++) {
			for (int c = 1; c <= 100; c++) {
				if (input.get(r - 1).charAt(c - 1) == '#')
					lightDisplay[r][c] = true;
			}
		}
		
		return new GameOfLifeLightDisplay(lightDisplay, rows, columns, cornersOn);
	}
	//</editor-fold>
	
	
	public int countLitLights() {
		return (int) Arrays.stream(lightDisplay).flatMap(booleans ->
				IntStream.range(0, booleans.length).mapToObj(i -> booleans[i])
		).filter(Boolean::booleanValue).count();
	}
	
	public GameOfLifeLightDisplay animate(int steps) {
		for (int stepCount = 0; stepCount < steps; stepCount++) {
			if (cornersOn)
				turnCornersOn();
			
			boolean[][] newLightDisplay = copyOf(lightDisplay);
			
			for (int r = 1; r <= rows; r++) {
				for (int c = 1; c <= columns; c++) {
					int adjacentLit = countAdjacentLit(r, c);
					
					if (lightDisplay[r][c]) {
						if (!(adjacentLit == 3 || adjacentLit == 2)) {
							newLightDisplay[r][c] = false;
						}
					} else {
						if (adjacentLit == 3) {
							newLightDisplay[r][c] = true;
						}
					}
				}
			}
			
			lightDisplay = newLightDisplay;
		}
		if (cornersOn)
			turnCornersOn();
		
		return this;
	}
	
	private void turnCornersOn() {
		lightDisplay[1][columns] = true;
		lightDisplay[1][1] = true;
		lightDisplay[rows][columns] = true;
		lightDisplay[rows][1] = true;
	}
	
	private int countAdjacentLit(int r, int c) {
		return (int) Stream.of(
				lightDisplay[r - 1][c - 1],
				lightDisplay[r - 1][c],
				lightDisplay[r - 1][c + 1],
				lightDisplay[r][c - 1],
				lightDisplay[r][c + 1],
				lightDisplay[r + 1][c - 1],
				lightDisplay[r + 1][c],
				lightDisplay[r + 1][c + 1]
		).filter(Boolean::booleanValue).count();
	}
	
	private static boolean[][] copyOf(boolean[][] in) {
		boolean[][] out = new boolean[in.length][];
		for (int i = 0; i < in.length; i++) {
			out[i] = Arrays.copyOf(in[i], in.length);
		}
		return out;
	}
}

class RunDay18_Part1 {
	public static void main(String[] args) {
		System.out.println(GameOfLifeLightDisplay.create(GameOfLifeLightDisplay.input).animate(100).countLitLights());
	}
}

class RunDay18_Part2 {
	public static void main(String[] args) {
		System.out.println(GameOfLifeLightDisplay.create(GameOfLifeLightDisplay.input, true).animate(100).countLitLights());
	}
}