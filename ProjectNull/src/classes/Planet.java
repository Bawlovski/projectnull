package classes;

public abstract class Planet {
    private String name;
    private String type;
    private int health;
    private int maxHealth;
    private int missiles;
    private int maxMissiles;
    private int defense;
    private int attack;

    public Planet(String name, String type, int health, int missiles, int defense, int attack) {
        this.name = name;
        this.type = type;
        this.health = health;
        this.maxHealth = health;
        this.missiles = missiles;
        this.maxMissiles = missiles;
        this.defense = defense;
        this.attack = attack;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(health, maxHealth));
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getMissiles() {
        return missiles;
    }

    public void setMissiles(int missiles) {
        this.missiles = Math.max(0, Math.min(missiles, maxMissiles));
    }

    public int getMaxMissiles() {
        return maxMissiles;
    }

    public int getDefense() {
        return defense;
    }

    public int getAttack() {
        return attack;
    }

    // Battle methods
    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int damage) {
        int actualDamage = Math.max(1, damage - defense);
        setHealth(health - actualDamage);
    }

    public boolean useMissile() {
        if (missiles > 0) {
            missiles--;
            return true;
        }
        return false;
    }

    public void regenerateMissiles() {
        int regeneration = (int)(maxMissiles * 0.2); // Regenerate 20% of max missiles
        setMissiles(missiles + regeneration);
    }

    public void heal() {
        int healing = (int)(maxHealth * 0.1); // Heal 10% of max health
        setHealth(health + healing);
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - HP: %d/%d, Missiles: %d/%d", 
            name, type, health, maxHealth, missiles, maxMissiles);
    }
} 