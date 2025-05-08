package classes.planets;

import classes.Planet;

/**
 * LostPlanet class represents a mysterious planet type with unique defensive and healing abilities.
 * This planet has a chance to find ancient shields during combat and has enhanced healing capabilities.
 */
public class LostPlanet extends Planet {
    /**
     * Constructor for LostPlanet.
     * Initializes the planet with balanced stats:
     * - Health: 180 (moderate)
     * - Missiles: 35 (good)
     * - Defense: 12 (moderate)
     * - Attack: 28 (good)
     */
    public LostPlanet() {
        super("Lost", "Mystery", 180, 35, 12, 28);
    }

    /**
     * Override of takeDamage method to implement the Lost Planet's unique defensive ability.
     * Has a 10% chance to find ancient shields, which provides instant healing of 20 HP.
     * If the ancient shield is not found, takes normal damage.
     * 
     * @param damage The amount of damage to be taken
     */
    @Override
    public void takeDamage(int damage) {
        // Lost planets have a chance to find ancient shields
        if (Math.random() < 0.1) {
            setHealth(getHealth() + 20); // Instant healing
            return;
        }
        super.takeDamage(damage);
    }

    /**
     * Override of heal method to implement enhanced healing capabilities.
     * Lost planets heal 15% of their max health instead of the standard 10%.
     * This makes them more resilient in long battles.
     */
    @Override
    public void heal() {
        // Lost planets heal more effectively
        int healing = (int)(getMaxHealth() * 0.15); // 15% healing instead of 10%
        setHealth(getHealth() + healing);
    }
} 