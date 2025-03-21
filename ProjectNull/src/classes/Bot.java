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

    public void makeMove(List<Player> allPlayers) {
        // Choose a random action (0: Attack, 1: Defend, 2: Heal, 3: Regenerate Missiles)
        int action = random.nextInt(4);

        switch (action) {
            case 0: // Attack
                if (botPlayer.getPlanet().getMissiles() > 0) {
                    // Choose a random target that is not the bot itself
                    Player target;
                    do {
                        target = allPlayers.get(random.nextInt(allPlayers.size()));
                    } while (target == botPlayer);
                    
                    botPlayer.attack(target);
                    System.out.println(botPlayer.getName() + " attacks " + target.getName());
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