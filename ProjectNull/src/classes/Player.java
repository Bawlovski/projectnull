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
            System.out.println("[ATTACK FAILED] Invalid attack parameters");
            return;
        }
        
        System.out.println("=====================");
        System.out.println("[PLAYER ATTACK] " + name + " attacks " + target.getName() + " with " + missiles + " missiles");
        
        // Consume missiles first
        for (int i = 0; i < missiles; i++) {
            if (!planet.useMissile()) {
                System.out.println("[ATTACK FAILED] Not enough missiles");
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
        
        System.out.println("[DAMAGE] Total damage: " + totalDamage);
        
        int targetInitialHealth = target.getPlanet().getHealth();
        target.getPlanet().takeDamage(totalDamage);
        int damageDealt = targetInitialHealth - target.getPlanet().getHealth();
        
        System.out.println("[RESULT] " + target.getName() + " took " + damageDealt + " damage");
        System.out.println("[HEALTH] " + target.getName() + " health: " + target.getPlanet().getHealth() + "/" + target.getPlanet().getMaxHealth());
        
        if (!target.isAlive()) {
            System.out.println("[DESTROYED] " + target.getName() + " has been destroyed!");
        }
        System.out.println("=====================");
    }

    public void heal() {
        System.out.println("=====================");
        System.out.println("[PLAYER HEAL] " + name + " is healing");
        
        int initialHealth = planet.getHealth();
        planet.heal();
        int healedAmount = planet.getHealth() - initialHealth;
        
        System.out.println("[HEALED] " + name + " recovered " + healedAmount + " health");
        System.out.println("[HEALTH] " + name + " health: " + planet.getHealth() + "/" + planet.getMaxHealth());
        System.out.println("=====================");
    }

    public void regenerateMissiles() {
        System.out.println("=====================");
        System.out.println("[PLAYER REGEN] " + name + " is regenerating missiles");
        
        int initialMissiles = planet.getMissiles();
        planet.regenerateMissiles();
        int regeneratedAmount = planet.getMissiles() - initialMissiles;
        
        System.out.println("[REGENERATED] " + name + " regenerated " + regeneratedAmount + " missiles");
        System.out.println("[MISSILES] " + name + " missiles: " + planet.getMissiles() + "/" + planet.getMaxMissiles());
        System.out.println("=====================");
    }

    public void defend() {
        // Implement defense logic here
        // For example, temporarily increase defense or reduce incoming damage
        System.out.println("=====================");
        System.out.println("[PLAYER DEFEND] " + name + " is defending");
        System.out.println("=====================");
    }

    @Override
    public String toString() {
        return String.format("%s - %s (Wins: %d, Losses: %d)", 
            name, planet.toString(), wins, losses);
    }
} 