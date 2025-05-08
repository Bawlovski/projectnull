package classes.planets;

import classes.Planet;

/**
 * GlitchPlanet class represents an unstable planet type with unpredictable combat abilities.
 * This planet has a chance to reduce incoming damage and occasionally fire double missiles.
 */
public class GlitchPlanet extends Planet {
    /**
     * Constructor for GlitchPlanet.
     * Initializes the planet with offensive-focused stats:
     * - Health: 150 (moderate)
     * - Missiles: 40 (high)
     * - Defense: 10 (low)
     * - Attack: 30 (high)
     */
    public GlitchPlanet() {
        super("Glitch", "Electric", 150, 40, 10, 30);
    }

    /**
     * Override of takeDamage method to implement the Glitch Planet's unique defensive ability.
     * Has a 15% chance to glitch and reduce incoming damage by 50%.
     * If the glitch doesn't occur, takes normal damage.
     * 
     * @param damage The amount of damage to be taken
     */
    @Override
    public void takeDamage(int damage) {
        // Glitch planets have a chance to glitch and reduce damage
        if (Math.random() < 0.15) {
            damage = (int)(damage * 0.5); // 50% damage reduction
        }
        super.takeDamage(damage);
    }

    /**
     * Override of useMissile method to implement the Glitch Planet's unique offensive ability.
     * Has a 10% chance to fire two missiles for the price of one.
     * If the glitch doesn't occur, uses missiles normally.
     * 
     * @return true if missiles were used successfully, false otherwise
     */
    @Override
    public boolean useMissile() {
        // Glitch planets have a chance to fire two missiles for the price of one
        if (Math.random() < 0.1) {
            if (getMissiles() >= 2) {
                setMissiles(getMissiles() - 2);
                return true;
            }
        }
        return super.useMissile();
    }
} 