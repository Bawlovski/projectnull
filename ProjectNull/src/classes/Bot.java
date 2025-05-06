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
            System.out.println("[BOT] " + botPlayer.getName() + " is dead and cannot make a move.");
            return;
        }

        // Choose a random action (0: Attack, 1: Defend, 2: Heal, 3: Regenerate Missiles)
        int action = random.nextInt(4);

        System.out.println("=====================");
        System.out.println("[BOT TURN] " + botPlayer.getName() + " is making a move...");

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
                        System.out.println("[BOT ATTACK] " + botPlayer.getName() + " attacks " + target.getName() + " with " + missiles + " missiles");
                        System.out.println("[TARGET] " + target.getName() + " health: " + target.getPlanet().getHealth() + "/" + target.getPlanet().getMaxHealth());
                    } else {
                        System.out.println("[BOT] " + botPlayer.getName() + " has no valid targets to attack.");
                    }
                } else {
                    System.out.println("[BOT] " + botPlayer.getName() + " has no missiles to attack.");
                }
                break;

            case 1: // Defend
                botPlayer.defend();
                System.out.println("[BOT DEFEND] " + botPlayer.getName() + " defends");
                break;

            case 2: // Heal
                if (botPlayer.getPlanet().getHealth() < botPlayer.getPlanet().getMaxHealth()) {
                    botPlayer.heal();
                    System.out.println("[BOT HEAL] " + botPlayer.getName() + " heals");
                    System.out.println("[HEALTH] " + botPlayer.getName() + " health: " + botPlayer.getPlanet().getHealth() + "/" + botPlayer.getPlanet().getMaxHealth());
                } else {
                    System.out.println("[BOT] " + botPlayer.getName() + " is already at full health.");
                }
                break;

            case 3: // Regenerate Missiles
                if (botPlayer.getPlanet().getMissiles() < botPlayer.getPlanet().getMaxMissiles()) {
                    botPlayer.regenerateMissiles();
                    System.out.println("[BOT REGEN] " + botPlayer.getName() + " regenerates missiles");
                    System.out.println("[MISSILES] " + botPlayer.getName() + " missiles: " + botPlayer.getPlanet().getMissiles() + "/" + botPlayer.getPlanet().getMaxMissiles());
                } else {
                    System.out.println("[BOT] " + botPlayer.getName() + " already has maximum missiles.");
                }
                break;
        }
        System.out.println("=====================");
    }
} 