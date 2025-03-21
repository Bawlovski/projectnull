package classes.planets;

import classes.Planet;

public class GlitchPlanet extends Planet {
    public GlitchPlanet() {
        super("Glitch", "Electric", 150, 40, 10, 30);
    }

    @Override
    public void takeDamage(int damage) {
        // Glitch planets have a chance to glitch and reduce damage
        if (Math.random() < 0.15) {
            damage = (int)(damage * 0.5); // 50% damage reduction
        }
        super.takeDamage(damage);
    }

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