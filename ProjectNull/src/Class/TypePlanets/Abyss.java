package Class.TypePlanets;

import Class.Planet;

public class Abyss extends Planet {
	
	private static String name = "Abyss";
	private static String type = "Black Hole";
	private static int health = 300;
	private static int missiles = 30;
	private static int maxMissiles = 30;
	
	public Abyss() {
		super(name, type, health, missiles,maxMissiles);
	}
	@Override
	public boolean regenerateMissiles() {
		if(missiles + 10 >= maxMissiles) {
			return false;
		}
		missiles += 10;
		return super.regenerateMissiles();
		
		
	}
}
