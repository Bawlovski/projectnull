import classes.Player;
import classes.Planet;
import classes.Game;
import classes.planets.AbyssPlanet;
import classes.planets.GlitchPlanet;
import classes.planets.LostPlanet;
import swing.BattleScreen;
import swing.LoadGameDialog;
import database.DatabaseManager;
import database.DatabaseManager.GameSaveData;
import database.DatabaseManager.PlayerSaveData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class main {
    public static void main(String[] args) {
        // Start the game on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // Display option to start new game or load game
            String[] options = {"New Game", "Load Game", "Exit"};
            int choice = JOptionPane.showOptionDialog(
                null,
                "Welcome to Space Battle!",
                "Space Battle",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );
            
            switch (choice) {
                case 0: // New Game
                    String playerName = JOptionPane.showInputDialog(
                        null,
                        "Enter your name:",
                        "New Game",
                        JOptionPane.QUESTION_MESSAGE
                    );
                    if (playerName != null && !playerName.trim().isEmpty()) {
                        startNewGame(playerName.trim());
                    }
                    break;
                    
                case 1: // Load Game
                    loadGame();
                    break;
                    
                default: // Exit or closed
                    System.exit(0);
            }
        });
    }
    
    public static void startNewGame(String playerName) {
        // Create player and planets
        Player player = new Player(playerName, new AbyssPlanet());
        Player bot1 = new Player("Bot 1", new GlitchPlanet());
        Player bot2 = new Player("Bot 2", new LostPlanet());
        Player bot3 = new Player("Bot 3", new AbyssPlanet());
        
        // Start battle screen with all players
        List<Player> players = Arrays.asList(player, bot1, bot2, bot3);
        BattleScreen battleScreen = new BattleScreen(players);
        battleScreen.setVisible(true);
    }
    
    public static void loadGame() {
        DatabaseManager dbManager = new DatabaseManager();
        LoadGameDialog loadDialog = new LoadGameDialog(null, dbManager);
        loadDialog.setVisible(true);
        
        if (loadDialog.isConfirmed()) {
            int gameId = loadDialog.getSelectedGameId();
            GameSaveData saveData = dbManager.loadGame(gameId);
            
            if (saveData != null) {
                // Recreate players from save data
                List<Player> players = new ArrayList<>();
                
                for (PlayerSaveData playerData : saveData.getPlayers()) {
                    // Create appropriate planet based on type
                    Planet planet;
                    switch (playerData.getPlanetType()) {
                        case "Dark":
                            planet = new AbyssPlanet();
                            break;
                        case "Glitch":
                            planet = new GlitchPlanet();
                            break;
                        case "Lost":
                            planet = new LostPlanet();
                            break;
                        default:
                            planet = new AbyssPlanet();
                    }
                    
                    // Set planet state
                    planet.setHealth(playerData.getHealth());
                    planet.setMissiles(playerData.getMissiles());
                    
                    // Create player with restored state
                    Player player = new Player(playerData.getName(), planet);
                    players.add(player);
                }
                
                // Start battle screen with loaded players
                BattleScreen battleScreen = new BattleScreen(players);
                battleScreen.setCurrentTurn(saveData.getCurrentTurn());
                battleScreen.setVisible(true);
            }
        }
    }
} 