package classes.planets;

import classes.Planet;

public class AbyssPlanet extends Planet {
    public AbyssPlanet() {
        super("Abyss", "Dark", 200, 30, 15, 25);
    }

    @Override
    public void takeDamage(int damage) {
        // Abyss planets have a 20% chance to dodge attacks
        if (Math.random() < 0.2) {
            return;
        }
        super.takeDamage(damage);
    }

    @Override
    public void regenerateMissiles() {
        // Abyss planets regenerate more missiles at night (30% instead of 20%)
        int regeneration = (int)(getMaxMissiles() * 0.3);
        setMissiles(getMissiles() + regeneration);
    }
} 