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

    public void attack(Player target) {
        if (!planet.useMissile()) {
            return;
        }
        int damage = planet.getAttack();
        target.getPlanet().takeDamage(damage);
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