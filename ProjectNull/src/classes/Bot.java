package classes;

import java.util.Random;
import java.util.List;

public class Bot {
    private Player botPlayer;
    private Random random;

    public Bot(Player player) {
        this.botPlayer = player;
        this.random = new Random();
    }

    public Player getBotPlayer() {
        return botPlayer;
    }

    public void makeMove(List<Player> allPlayers) {
        if (!botPlayer.isAlive()) {
            return;
        }

        // Choose a random action (0: Attack, 1: Defend, 2: Heal, 3: Regenerate Missiles)
        int action = random.nextInt(4);

        switch (action) {
            case 0: // Attack
                if (botPlayer.getPlanet().getMissiles() > 0) {
                    // Find alive targets
                    List<Player> aliveTargets = allPlayers.stream()
                        .filter(p -> p != botPlayer && p.isAlive())
                        .toList();
                    
                    if (!aliveTargets.isEmpty()) {
                        // Choose a random alive target
                        Player target = aliveTargets.get(random.nextInt(aliveTargets.size()));
                        
                        // Choose a random number of missiles (1 to max available)
                        int missiles = random.nextInt(botPlayer.getPlanet().getMissiles()) + 1;
                        botPlayer.attack(target, missiles);
                        System.out.println(botPlayer.getName() + " attacks " + target.getName() + " with " + missiles + " missiles");
                    }
                }
                break;

            case 1: // Defend
                botPlayer.defend();
                System.out.println(botPlayer.getName() + " defends");
                break;

            case 2: // Heal
                if (botPlayer.getPlanet().getHealth() < botPlayer.getPlanet().getMaxHealth()) {
                    botPlayer.heal();
                    System.out.println(botPlayer.getName() + " heals");
                }
                break;

            case 3: // Regenerate Missiles
                if (botPlayer.getPlanet().getMissiles() < botPlayer.getPlanet().getMaxMissiles()) {
                    botPlayer.regenerateMissiles();
                    System.out.println(botPlayer.getName() + " regenerates missiles");
                }
                break;
        }
    }
} 