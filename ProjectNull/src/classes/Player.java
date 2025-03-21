package classes;

public class Player {
    private String name;
    private Planet planet;
    private int wins;
    private int losses;

    public Player(String name, Planet planet) {
        this.name = name;
        this.planet = planet;
        this.wins = 0;
        this.losses = 0;
    }

    public String getName() {
        return name;
    }

    public Planet getPlanet() {
        return planet;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public void addWin() {
        wins++;
    }

    public void addLoss() {
        losses++;
    }

    public boolean isAlive() {
        return planet.isAlive();
    }

    public void attack(Player target, int missiles) {
        if (missiles <= 0 || missiles > planet.getMissiles() || !isAlive() || !target.isAlive()) {
            return;
        }
        
        // Consume missiles first
        for (int i = 0; i < missiles; i++) {
            if (!planet.useMissile()) {
                return; // Stop if we can't use more missiles
            }
        }
        
        // Calculate and apply damage with diminishing returns
        int baseDamage = planet.getAttack();
        int totalDamage = 0;
        for (int i = 0; i < missiles; i++) {
            // Each subsequent missile does less damage (80% of previous)
            totalDamage += (int)(baseDamage * Math.pow(0.8, i));
        }
        target.getPlanet().takeDamage(totalDamage);
    }

    public void heal() {
        planet.heal();
    }

    public void regenerateMissiles() {
        planet.regenerateMissiles();
    }

    public void defend() {
        // Implement defense logic here
        // For example, temporarily increase defense or reduce incoming damage
        System.out.println(name + " is defending");
    }

    @Override
    public String toString() {
        return String.format("%s - %s (Wins: %d, Losses: %d)", 
            name, planet.toString(), wins, losses);
    }
} 