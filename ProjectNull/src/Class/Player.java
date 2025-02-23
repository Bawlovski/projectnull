package Class;

public  class Player {
	private String name;
	private Planet planet;
	private boolean protection = false;
	
	
	public Player(String name) {
		super();
		this.name = name;
	}

	public void setPlanet(Planet p) {
		this.planet = p;
	}
	
	public boolean attack(Player p, int missiles){
		this.protection = false;
		
		isAlive(p);
		planet.reduceMissiles(missiles);
		if(p.protection == true) {
			p.planet.setHealth(p.planet.getHealth() - missiles/2);
			p.protection = false;
			return false;
		}
		
		
		return true;
	}
	public void deffend() {
		this.protection = true;
	}

	
	public boolean isAlive(Player p) {
		if(p.planet.getHealth() <= 0) {
			return false;
		}
		return true;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Planet getPlanet() {
		return this.planet;
	}
}
