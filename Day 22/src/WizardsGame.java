import java.util.*;

/**
 * Created by Andre on 12/28/2016.
 */
public class WizardsGame {
	private static class GameStateNode {
		private enum Spell {
			MAGIC_MISSILE(53, 1),
			DRAIN(73, 1),
			SHIELD(113, 6),
			POISON(173, 6),
			RECHARGE(229, 5);
			
			private final int cost, duration;
			
			Spell(int cost, int duration) {
				this.cost = cost;
				this.duration = duration;
			}
			
			int getCost() {
				return cost;
			}
			
			int getDuration() {
				return duration;
			}
			
			static Set<Spell> hashSet() {
				return new HashSet<>(Arrays.asList(values()));
			}
		}
		
		private int bossHitPoints, playerHitPoints,
				bossOriginalDamage,
				manaUsed, playerMana;
		private int armor;
		private Map<Spell, Integer> activeEffects;
		private boolean gameEnd = false, playerWin = false;
		private Spell spellToCast;
		private boolean ran = false;
		private Set<GameStateNode> nextNodes = new HashSet<>();
		private final boolean hard;
		
		static Set<GameStateNode> firstNodes(int bossHitPoints, int playerHitPoints, int bossOriginalDamage, int playerMana, boolean hard) {
			Set<GameStateNode> firstNodes = new HashSet<>();
			for (Spell spell : Spell.hashSet()) {
				firstNodes.add(
						new GameStateNode(
								bossHitPoints,
								playerHitPoints,
								bossOriginalDamage,
								0,
								playerMana,
								new HashMap<>(),
								spell,
								hard
						)
				);
			}
			return firstNodes;
		}
		
		private GameStateNode(int bossHitPoints, int playerHitPoints, int bossOriginalDamage, int manaUsed, int playerMana, Map<Spell, Integer> activeEffects, Spell spellToCast, boolean hard) {
			this.bossHitPoints = bossHitPoints;
			this.playerHitPoints = playerHitPoints;
			this.bossOriginalDamage = bossOriginalDamage;
			this.playerMana = playerMana;
			this.manaUsed = manaUsed;
			this.activeEffects = activeEffects;
			this.spellToCast = spellToCast;
			this.hard = hard;
		}
		
		private void doDamageToBoss(int amount) {
			bossHitPoints -= amount;
		}
		
		private void rechargeMana(int amount) {
			playerMana += amount;
		}
		
		private void setArmor(int amount) {
			armor = amount;
		}
		
		private void heal(int amount) {
			playerHitPoints += amount;
		}
		
		private void useMana(int amount) {
			manaUsed += amount;
			playerMana -= amount;
		}
		
		private void playerCastSpell() {
			if (activeEffects.containsKey(spellToCast)) {
				gameEnd = true;
				return;
			}
			useMana(spellToCast.cost);
			switch (spellToCast) {
				case MAGIC_MISSILE:
					doDamageToBoss(4);
					break;
				case DRAIN:
					doDamageToBoss(2);
					heal(2);
					break;
				case SHIELD:
				case POISON:
				case RECHARGE:
					activeEffects.put(spellToCast, spellToCast.duration);
					break;
			}
			
			gameEnd();
		}
		
		private void bossTurn() {
			int bossDamage = bossOriginalDamage - armor;
			if (bossDamage < 1) bossDamage = 1;
			playerHitPoints -= bossDamage;
			
			gameEnd();
		}
		
		private void doEffects() {
			setArmor(0);
			for (Spell spell : activeEffects.keySet()) {
				switch (spell) {
					case SHIELD:
						setArmor(7);
						break;
					case POISON:
						doDamageToBoss(3);
						break;
					case RECHARGE:
						rechargeMana(101);
						break;
				}
			}
			
			(new HashMap<>(activeEffects)).forEach((spell, integer) -> {
				if (integer == 1) {
					activeEffects.remove(spell);
				} else {
					activeEffects.put(spell, integer - 1);
				}
			});
			
			gameEnd();
		}
		
		private void hardModeDrain() {
			playerHitPoints--;
			gameEnd();
		}
		
		private void generateNodes() {
			for (Spell spell : Spell.values()) {
				nextNodes.add(
						new GameStateNode(
								bossHitPoints,
								playerHitPoints,
								bossOriginalDamage,
								manaUsed,
								playerMana,
								new HashMap<>(activeEffects),
								spell,
								hard
						)
				);
			}
		}
		
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			
			GameStateNode that = (GameStateNode) o;
			
			if (bossHitPoints != that.bossHitPoints) return false;
			if (playerHitPoints != that.playerHitPoints) return false;
			if (bossOriginalDamage != that.bossOriginalDamage) return false;
			if (manaUsed != that.manaUsed) return false;
			if (playerMana != that.playerMana) return false;
			if (armor != that.armor) return false;
			if (gameEnd != that.gameEnd) return false;
			if (playerWin != that.playerWin) return false;
			if (ran != that.ran) return false;
			if (hard != that.hard) return false;
			if (activeEffects != null ? !activeEffects.equals(that.activeEffects) : that.activeEffects != null)
				return false;
			if (spellToCast != that.spellToCast) return false;
			return nextNodes != null ? nextNodes.equals(that.nextNodes) : that.nextNodes == null;
		}
		
		@Override
		public int hashCode() {
			int result = bossHitPoints;
			result = 31 * result + playerHitPoints;
			result = 31 * result + bossOriginalDamage;
			result = 31 * result + manaUsed;
			result = 31 * result + playerMana;
			result = 31 * result + armor;
			result = 31 * result + (activeEffects != null ? activeEffects.hashCode() : 0);
			result = 31 * result + (gameEnd ? 1 : 0);
			result = 31 * result + (playerWin ? 1 : 0);
			result = 31 * result + (spellToCast != null ? spellToCast.hashCode() : 0);
			result = 31 * result + (ran ? 1 : 0);
			result = 31 * result + (nextNodes != null ? nextNodes.hashCode() : 0);
			result = 31 * result + (hard ? 1 : 0);
			return result;
		}
		
		private void gameEnd() {
			if (!gameEnd) {
				if (playerHitPoints <= 0 || playerMana <= 0) {
					gameEnd = true;
					playerWin = false;
				} else if (bossHitPoints <= 0) {
					gameEnd = true;
					playerWin = true;
				}
			}
		}
		
		void run() {
			if (!ran) {
				ran = true;
				
				// Player turn
				if (hard) hardModeDrain();
				doEffects();
				playerCastSpell();
				
				
				// Boss turn
				doEffects();
				bossTurn();
				
				generateNodes();
			}
		}
		
		Boolean isEnd() {
			if (!ran)
				return null;
			else
				return gameEnd;
		}
		
		Boolean playerWon() {
			if (!ran)
				return null;
			else
				return playerWin;
		}
		
		Integer getManaUsed() {
			return manaUsed;
		}
		
		Set<GameStateNode> getNodes() {
			if (!ran) {
				return null;
			}
			return nextNodes;
		}
		
	}
	
	private final int playerHitPoints, playerMana,
			bossHitPoints, bossOriginalDamage;
	private final boolean hard;
	
	private int leastMana = -1;
	
	public int getLeastMana() {
		return leastMana;
	}
	
	public WizardsGame(int playerHitPoints, int playerMana, int bossHitPoints, int bossOriginalDamage) {
		this(playerHitPoints, playerMana, bossHitPoints, bossOriginalDamage, false);
	}
	
	public WizardsGame(int playerHitPoints, int playerMana, int bossHitPoints, int bossOriginalDamage, boolean hard) {
		this.playerHitPoints = playerHitPoints;
		this.playerMana = playerMana;
		this.bossHitPoints = bossHitPoints;
		this.bossOriginalDamage = bossOriginalDamage;
		this.hard = hard;
	}
	
	
	public void run() {
		Set<GameStateNode> nodes = GameStateNode.firstNodes(bossHitPoints, playerHitPoints, bossOriginalDamage, playerMana, hard);
		while (nodes.size() > 0) {
			Set<GameStateNode> nextNodes = new HashSet<>();
			for (GameStateNode node : nodes) {
				node.run();
				if (leastMana != -1 && node.getManaUsed() > leastMana)
					continue;
				
				if (!node.isEnd()) {
					nextNodes.addAll(node.getNodes());
				} else if (leastMana == -1 || node.getManaUsed() < leastMana) {
					if (node.playerWon())
						leastMana = node.getManaUsed();
				}
			}
			nodes = nextNodes;
		}
	}
	
	private static class Test {
		public static void main(String[] args) {
			WizardsGame wizardsGame = new WizardsGame(10, 250, 13, 8);
			wizardsGame.run();
			System.out.println(wizardsGame.getLeastMana());
		}
	}
}

class RunDay22_Part1 {
	public static void main(String[] args) {
		WizardsGame wizardsGame = new WizardsGame(50, 500, 51, 9);
		wizardsGame.run();
		System.out.println(wizardsGame.getLeastMana());
	}
}

class RunDay22_Part2 {
	public static void main(String[] args) {
		WizardsGame wizardsGame = new WizardsGame(50, 500, 51, 9, true);
		wizardsGame.run();
		System.out.println(wizardsGame.getLeastMana());
	}
}