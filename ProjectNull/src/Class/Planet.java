package Class;

public abstract class Planet {
	private String name;
	private String type;
	private int health;
	private int missiles;
	private int maxMissiles;
	
	
	



	public Planet(String name, String type, int health, int missiles,int maxMissiles) {
		this.name = name;
		this.type = type;
		this.health = health;
		this.missiles = missiles;
		this.maxMissiles = maxMissiles;
	}

	public void applyEffects() {
		
	}
	
	public boolean reduceMissiles(int x) {
		if(missiles - x == 0) {
			return false;
		}
		missiles -= x;
		return true;
	}
	
	public boolean regenerateMissiles() {
		return false;
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public String getName() {
		return this.name;
	}
}
