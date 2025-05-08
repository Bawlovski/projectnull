package classes.planets;

import classes.Planet;

/**
 * AbyssPlanet class represents a dark and mysterious planet type with high defensive capabilities.
 * This planet has a chance to dodge attacks and enhanced missile regeneration at night.
 */
public class AbyssPlanet extends Planet {
    /**
     * Constructor for AbyssPlanet.
     * Initializes the planet with defensive-focused stats:
     * - Health: 200 (high)
     * - Missiles: 30 (moderate)
     * - Defense: 15 (high)
     * - Attack: 25 (moderate)
     */
    public AbyssPlanet() {
        super("Abyss", "Dark", 200, 30, 15, 25);
    }

    /**
     * Override of takeDamage method to implement the Abyss Planet's unique defensive ability.
     * Has a 20% chance to completely dodge incoming attacks.
     * If the dodge fails, takes normal damage.
     * 
     * @param damage The amount of damage to be taken
     */
    @Override
    public void takeDamage(int damage) {
        // Abyss planets have a 20% chance to dodge attacks
        if (Math.random() < 0.2) {
            return;
        }
        super.takeDamage(damage);
    }

    /**
     * Override of regenerateMissiles method to implement enhanced regeneration capabilities.
     * Abyss planets regenerate 30% of their max missiles instead of the standard 20%.
     * This makes them more effective in sustained combat.
     */
    @Override
    public void regenerateMissiles() {
        // Abyss planets regenerate more missiles at night (30% instead of 20%)
        int regeneration = (int)(getMaxMissiles() * 0.3);
        setMissiles(getMissiles() + regeneration);
    }
} 