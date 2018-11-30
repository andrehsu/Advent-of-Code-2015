import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.javatuples.Pair;
import org.javatuples.Unit;
import property.Property;

import java.util.*;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.sum;
import static property.Property.*;

/**
 * Created by andre on 12/18/2016.
 */
public class BestCookie {
	public static final List<String> input = Input.readAllLines("Day 15/input.txt");
	
	private final Table<String, Property, Integer> ingredientTable;
	
	private Map<String, Integer> bestCookie = null;
	private int bestGrade = 0;
	private Map<String, Integer> bestCookieC = null;
	private int bestGradeC = 0;
	
	public Pair<Pair<Map<String, Integer>, Integer>, Pair<Map<String, Integer>, Integer>> get() {
		return Pair.with(Pair.with(bestCookie, bestGrade), Pair.with(bestCookieC, bestGradeC));
	}
	
	//<editor-fold desc="Constructor and creator">
	public BestCookie(Table<String, Property, Integer> ingredientTable) {
		this.ingredientTable = ingredientTable;
	}
	
	public static BestCookie create(List<String> input) {
		Table<String, Property, Integer> ingredientTable = HashBasedTable.create();
		for (String ingredient : input) {
			ingredient = ingredient.replaceAll(":|,|capacity|durability|flavor|texture|calories", "");
			String[] tokens = ingredient.split(" +");
			
			String name = tokens[0];
			
			ingredientTable.put(name, CAPACITY, parseInt(tokens[1]));
			ingredientTable.put(name, DURABILITY, parseInt(tokens[2]));
			ingredientTable.put(name, FLAVOR, parseInt(tokens[3]));
			ingredientTable.put(name, TEXTURE, parseInt(tokens[4]));
			ingredientTable.put(name, CALORIES, parseInt(tokens[5]));
		}
		return new BestCookie(ingredientTable);
	}
	//</editor-fold>
	
	public BestCookie run() {
		node(100, new HashMap<>(), new HashSet<>(ingredientTable.rowKeySet()));
		return this;
	}
	
	private void node(int remTeaspoons, Map<String, Integer> cookie, Set<String> remIngredients) {
		if (remIngredients.size() == 1) {
			cookie.put(nextOf(remIngredients), remTeaspoons);
			Pair<Integer, Boolean> grade = gradeCookie(cookie);
			if (grade.getValue0() > bestGrade) {
				bestGrade = grade.getValue0();
				bestCookie = cookie;
			}
			if (grade.getValue1() && grade.getValue0() > bestGradeC) {
				bestGradeC = grade.getValue0();
				bestCookieC = cookie;
			}
			return;
		}
		
		String ingredient = nextOf(remIngredients);
		Set<String> nextRemIngredients = new HashSet<>(remIngredients);
		nextRemIngredients.remove(ingredient);
		for (int amount = 0; amount <= remTeaspoons; amount++) {
			cookie.put(ingredient, amount);
			node(remTeaspoons - amount, new HashMap<>(cookie), new HashSet<>(nextRemIngredients));
		}
	}
	
	private static <E> E nextOf(Collection<E> collection) {
		return collection.iterator().next();
	}
	
	private Pair<Integer, Boolean> gradeCookie(Map<String, Integer> ingredientsUsed) {
		Map<Property, Integer> sums = new HashMap<>();
		
		
		for (Property property : Property.values()) {
			for (String ingredient : ingredientsUsed.keySet()) {
				sums.merge(property, ingredientTable.get(ingredient, property) * ingredientsUsed.get(ingredient), Integer::sum);
			}
		}
		
		sums.entrySet().stream().filter(entry -> entry.getValue() < 0).forEach(entry -> entry.setValue(0));
		
		Unit<Integer> grade = Unit.with(sums.get(CAPACITY) * sums.get(DURABILITY) * sums.get(FLAVOR) * sums.get(TEXTURE));
		if (sums.get(CALORIES) == 500)
			return grade.add(true);
		else
			return grade.add(false);
	}
	
	
	private static class Test {
		private static final List<String> testInput = Input.readAllLines("Day 15/test input.txt");
		
		public static void main(String[] args) {
			BestCookie bestCookie = BestCookie.create(testInput);
			
			Map<String, Integer> optimalCookie = new HashMap<>();
			optimalCookie.put("Butterscotch", 44);
			optimalCookie.put("Cinnamon", 56);
			System.out.println(bestCookie.gradeCookie(optimalCookie));
			
			System.out.println(bestCookie.get());
		}
	}
}

class RunDay15_Part1Part2 {
	public static void main(String[] args) {
		System.out.println(BestCookie.create(BestCookie.input).run().get());
	}
}