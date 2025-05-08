package classes;

/**
 * Abstract base class representing a planet in the game.
 * Defines the core attributes and behaviors that all planets share.
 * Each planet type extends this class to implement its unique abilities.
 */
public abstract class Planet {
    private String name;        // Name of the planet
    private String type;        // Type of the planet (e.g., "Dark", "Electric", "Mystery")
    private int health;         // Current health points
    private int maxHealth;      // Maximum health points
    private int missiles;       // Current number of missiles
    private int maxMissiles;    // Maximum number of missiles
    private int defense;        // Defense rating
    private int attack;         // Attack rating

    /**
     * Constructor for Planet.
     * Initializes a planet with the specified attributes.
     * 
     * @param name The name of the planet
     * @param type The type of the planet
     * @param health Initial health points
     * @param missiles Initial number of missiles
     * @param defense Defense rating
     * @param attack Attack rating
     */
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

    /**
     * Sets the planet's health, ensuring it stays within valid bounds (0 to maxHealth).
     * 
     * @param health The new health value
     */
    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(health, maxHealth));
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getMissiles() {
        return missiles;
    }

    /**
     * Sets the planet's missile count, ensuring it stays within valid bounds (0 to maxMissiles).
     * 
     * @param missiles The new missile count
     */
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

    /**
     * Checks if the planet is still alive (health > 0).
     * 
     * @return true if the planet is alive, false otherwise
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * Applies damage to the planet, taking defense into account.
     * Minimum damage is 1, even with high defense.
     * 
     * @param damage The amount of damage to be taken
     */
    public void takeDamage(int damage) {
        int actualDamage = Math.max(1, damage - defense);
        setHealth(health - actualDamage);
    }

    /**
     * Attempts to use a missile.
     * 
     * @return true if a missile was used successfully, false if no missiles are available
     */
    public boolean useMissile() {
        if (missiles > 0) {
            missiles--;
            return true;
        }
        return false;
    }

    /**
     * Regenerates missiles by 20% of the maximum capacity.
     */
    public void regenerateMissiles() {
        int regeneration = (int)(maxMissiles * 0.2); // Regenerate 20% of max missiles
        setMissiles(missiles + regeneration);
    }

    /**
     * Heals the planet by 10% of its maximum health.
     */
    public void heal() {
        int healing = (int)(maxHealth * 0.1); // Heal 10% of max health
        setHealth(health + healing);
    }

    /**
     * Returns a string representation of the planet's current state.
     * 
     * @return String containing planet name, type, health, and missile information
     */
    @Override
    public String toString() {
        return String.format("%s (%s) - HP: %d/%d, Missiles: %d/%d", 
            name, type, health, maxHealth, missiles, maxMissiles);
    }
} 