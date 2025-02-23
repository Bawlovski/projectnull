package Class.TypePlanets;

import Class.Planet;

public class Lost extends Planet {
	
	private static String name = "Echo";
	private static String type = "Lost";
	private static int health = 400;
	private static int missiles = 10;
	private static int maxMissiles = 10;
	
	public Lost() {
		super(name, type, health, missiles,maxMissiles);
	}

	@Override
	public boolean regenerateMissiles() {
		if(missiles + 3 >= maxMissiles) {
			return false;
		}
		missiles += 3;
		return super.regenerateMissiles();

	}
	
	
}
