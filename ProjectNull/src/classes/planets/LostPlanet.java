package classes.planets;

import classes.Planet;

public class LostPlanet extends Planet {
    public LostPlanet() {
        super("Lost", "Mystery", 180, 35, 12, 28);
    }

    @Override
    public void takeDamage(int damage) {
        // Lost planets have a chance to find ancient shields
        if (Math.random() < 0.1) {
            setHealth(getHealth() + 20); // Instant healing
            return;
        }
        super.takeDamage(damage);
    }

    @Override
    public void heal() {
        // Lost planets heal more effectively
        int healing = (int)(getMaxHealth() * 0.15); // 15% healing instead of 10%
        setHealth(getHealth() + healing);
    }
} 