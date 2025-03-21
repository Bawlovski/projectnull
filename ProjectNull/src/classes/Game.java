package classes;

import classes.planets.AbyssPlanet;
import classes.planets.GlitchPlanet;
import classes.planets.LostPlanet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private List<Player> players;
    private Scanner scanner;
    private boolean gameRunning;

    public Game() {
        players = new ArrayList<>();
        scanner = new Scanner(System.in);
        gameRunning = false;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void startGame() {
        if (players.size() < 2) {
            System.out.println("Need at least 2 players to start the game!");
            return;
        }

        gameRunning = true;
        int currentPlayerIndex = 0;

        while (gameRunning) {
            Player currentPlayer = players.get(currentPlayerIndex);
            
            if (!currentPlayer.isAlive()) {
                currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                continue;
            }

            System.out.println("\n=== " + currentPlayer.getName() + "'s Turn ===");
            System.out.println(currentPlayer.toString());
            
            // Show other players
            System.out.println("\nOther Players:");
            for (int i = 0; i < players.size(); i++) {
                if (i != currentPlayerIndex) {
                    System.out.println((i + 1) + ". " + players.get(i).toString());
                }
            }

            // Player actions
            System.out.println("\nActions:");
            System.out.println("1. Attack");
            System.out.println("2. Heal");
            System.out.println("3. Regenerate Missiles");
            System.out.println("4. End Turn");
            System.out.println("5. Quit Game");

            int choice = getValidChoice(1, 5);

            switch (choice) {
                case 1:
                    // Attack
                    System.out.println("\nSelect target (1-" + (players.size() - 1) + "):");
                    int targetIndex = getValidChoice(1, players.size() - 1) - 1;
                    if (targetIndex >= currentPlayerIndex) {
                        targetIndex++; // Adjust for skipped current player
                    }
                    Player target = players.get(targetIndex);
                    currentPlayer.attack(target);
                    System.out.println(currentPlayer.getName() + " attacked " + target.getName());
                    break;

                case 2:
                    // Heal
                    currentPlayer.heal();
                    System.out.println(currentPlayer.getName() + " healed their planet");
                    break;

                case 3:
                    // Regenerate Missiles
                    currentPlayer.regenerateMissiles();
                    System.out.println(currentPlayer.getName() + " regenerated missiles");
                    break;

                case 4:
                    // End Turn
                    System.out.println(currentPlayer.getName() + " ended their turn");
                    break;

                case 5:
                    // Quit Game
                    gameRunning = false;
                    System.out.println("Game ended by " + currentPlayer.getName());
                    break;
            }

            // Check for game end conditions
            int alivePlayers = 0;
            Player winner = null;
            for (Player player : players) {
                if (player.isAlive()) {
                    alivePlayers++;
                    winner = player;
                }
            }

            if (alivePlayers <= 1) {
                if (winner != null) {
                    winner.addWin();
                    System.out.println("\n" + winner.getName() + " wins the game!");
                }
                gameRunning = false;
            }

            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }

        // Show final statistics
        System.out.println("\n=== Game Statistics ===");
        for (Player player : players) {
            System.out.println(player.toString());
        }
    }

    private int getValidChoice(int min, int max) {
        while (true) {
            try {
                System.out.print("Enter your choice: ");
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.println("Invalid choice. Please enter a number between " + min + " and " + max);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
    }

    public static void main(String[] args) {
        // Example game setup
        Game game = new Game();
        
        // Create some planets
        AbyssPlanet abyss = new AbyssPlanet();
        GlitchPlanet glitch = new GlitchPlanet();
        LostPlanet lost = new LostPlanet();

        // Create players
        game.addPlayer(new Player("Player 1", abyss));
        game.addPlayer(new Player("Player 2", glitch));
        game.addPlayer(new Player("Player 3", lost));

        // Start the game
        game.startGame();
    }
} 