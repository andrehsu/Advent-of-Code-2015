import javax.management.relation.Role;
import java.util.*;

/**
 * Created by Andre on 12/28/2016.
 */
public class RolePlayingGame {
	public class Input {
		public static final int bossHitPoint = 103, bossDamage = 9, bossArmor = 2;
		public static final int playerHitPoint = 100;
	}
	
	private final int playerHitPoint;
	private final int bossHitPoint, bossDamage, bossArmor;
	
	private int leastGoldWithWin = -1, mostGoldWithLost = -1;
	
	public int getLeastGoldWithWin() {
		return leastGoldWithWin;
	}
	
	public int getMostGoldWithLost() {
		return mostGoldWithLost;
	}
	
	private static class CharacterSetup {
		private final int damage, armor, cost;
		private final Set<Item> items;
		
		CharacterSetup(Set<Item> items) {
			int cost = 0, damage = 0, armor = 0;
			for (Item item : items) {
				cost += item.cost;
				damage += item.damage;
				armor += item.armor;
			}
			this.cost = cost;
			this.damage = damage;
			this.armor = armor;
			
			this.items = items;
		}
		
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			
			CharacterSetup setup = (CharacterSetup) o;
			
			if (damage != setup.damage) return false;
			if (armor != setup.armor) return false;
			return cost == setup.cost;
		}
		
		@Override
		public int hashCode() {
			int result = damage;
			result = 31 * result + armor;
			result = 31 * result + cost;
			return result;
		}
	}
	
	private enum Item {
		
		// Weapon
		DAGGER(Type.WEAPON, 8, 4, 0),
		SHORTSWORD(Type.WEAPON, 10, 5, 0),
		WARHAMMER(Type.WEAPON, 25, 6, 0),
		LONGSWORD(Type.WEAPON, 40, 7, 0),
		GREATAXE(Type.WEAPON, 74, 8, 0),
		
		// Armor
		NONE(Type.ARMOR, 0, 0, 0),
		LEATHER(Type.ARMOR, 13, 0, 1),
		CHAINMAIL(Type.ARMOR, 31, 0, 2),
		SPLINTMAIL(Type.ARMOR, 53, 0, 3),
		BANDEDMAIL(Type.ARMOR, 75, 0, 4),
		PLATEMAIL(Type.ARMOR, 102, 0, 5),
		
		// Rings
		DAMAGE_1(Type.RING, 25, 1, 0),
		DAMAGE_2(Type.RING, 50, 2, 0),
		DAMAGE_3(Type.RING, 100, 3, 0),
		DEFENSE_1(Type.RING, 20, 0, 1),
		DEFENSE_2(Type.RING, 40, 0, 2),
		DEFENSE_3(Type.RING, 80, 0, 3);
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(super.toString().toLowerCase());
			return sb.append(" (").append(cost).append(") ").toString();
		}
		
		private enum Type {
			WEAPON, ARMOR, RING;
		}
		
		private final Type type;
		private final int cost, damage, armor;
		
		private Item(Type type, int cost, int damage, int armor) {
			this.type = type;
			this.cost = cost;
			this.damage = damage;
			this.armor = armor;
		}
		
		
	}
	
	public RolePlayingGame(int playerHitPoint, int bossHitPoint, int bossDamage, int bossArmor) {
		this.playerHitPoint = playerHitPoint;
		this.bossHitPoint = bossHitPoint;
		this.bossDamage = bossDamage;
		this.bossArmor = bossArmor;
	}
	
	public void run() {
		Set<CharacterSetup> setups = setups();
		leastGoldWithWin = findLeastGoldWithWin(setups);
		mostGoldWithLost = findMostGoldWithLose(setups);
	}
	
	private Set<CharacterSetup> setups() {
		Set<CharacterSetup> setups = new HashSet<>();
		
		Set<Item> weapons = new HashSet<>(), armors = new HashSet<>(), rings = new HashSet<>();
		for (Item item : Item.values()) {
			switch (item.type) {
				case WEAPON:
					weapons.add(item);
					break;
				case ARMOR:
					armors.add(item);
					break;
				case RING:
					rings.add(item);
					break;
			}
		}
		
		for (Item weapon : weapons) {
			for (Item armor : armors) {
				Set<Item> items_pickZeroRings = new HashSet<>();
				items_pickZeroRings.add(weapon);
				items_pickZeroRings.add(armor);
				
				setups.add(new CharacterSetup(items_pickZeroRings));
				
				for (Item firstRing : rings) {
					Set<Item> items_pickOneRing = new HashSet<>(items_pickZeroRings);
					items_pickOneRing.add(firstRing);
					
					setups.add(new CharacterSetup(items_pickOneRing));
					
					Set<Item> fiveRings = new HashSet<>(rings);
					fiveRings.remove(firstRing);
					for (Item secondRing : fiveRings) {
						Set<Item> items_pickTwoRings = new HashSet<>(items_pickOneRing);
						items_pickTwoRings.add(secondRing);
						
						setups.add(new CharacterSetup(items_pickTwoRings));
					}
				}
			}
		}
		
		return setups;
	}
	
	private int findLeastGoldWithWin(Set<CharacterSetup> setups) {
		List<CharacterSetup> sortedSetups = new LinkedList<>(setups);
		sortedSetups.sort(Comparator.comparingInt(o -> o.cost));
		
		for (CharacterSetup setup : sortedSetups) {
			if (canWinBattle(setup)) {
				System.out.println(setup.items);
				return setup.cost;
			}
		}
		
		return -1;
	}
	
	private int findMostGoldWithLose(Set<CharacterSetup> setups) {
		List<CharacterSetup> sortedSetups = new LinkedList<>(setups);
		sortedSetups.sort((o1, o2) -> Integer.compare(o2.cost, o1.cost));
		
		for (CharacterSetup setup : sortedSetups) {
			if (!canWinBattle(setup)) {
				System.out.println(setup.items);
				return setup.cost;
			}
		}
		
		return -1;
	}
	
	private boolean canWinBattle(CharacterSetup setup) {
		int bossHitPoint = this.bossHitPoint, hitPoint = this.playerHitPoint;
		int playerDamage = setup.damage - bossArmor;
		if (playerDamage < 1) {
			playerDamage = 1;
		}
		
		int bossDamage = this.bossDamage - setup.armor;
		if (bossDamage < 1) {
			bossDamage = 1;
		}
		
		while (true) {
			bossHitPoint -= playerDamage;
			if (bossHitPoint <= 0)
				break;
			hitPoint -= bossDamage;
			if (hitPoint <= 0)
				break;
		}
		
		return hitPoint > bossHitPoint;
	}
}

class RunDay21 {
	public static void main(String[] args) {
		RolePlayingGame game = new RolePlayingGame(RolePlayingGame.Input.playerHitPoint, RolePlayingGame.Input.bossHitPoint, RolePlayingGame.Input.bossDamage, RolePlayingGame.Input.bossArmor);
		game.run();
		System.out.println(game.getLeastGoldWithWin());
		System.out.println(game.getMostGoldWithLost());
	}
}