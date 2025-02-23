package Class.TypePlanets;

import Class.Planet;

public class Glitch extends Planet {
	
	private static String name = "Glitch";
	private static String type = "Corrupt";
	private static int health = 150;
	private static int missiles = 60;
	private static int maxMissiles = 60;
	
	public Glitch() {
		super(name, type, health, missiles,maxMissiles);
	}
	@Override
	public boolean regenerateMissiles() {
		if(missiles + 25 >= maxMissiles) {
			return false;
		}
		missiles += 25;
		return super.regenerateMissiles();
		
		
	}
}
