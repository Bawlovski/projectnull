package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import classes.Player;
import classes.Planet;
import classes.planets.AbyssPlanet;
import classes.planets.GlitchPlanet;
import classes.planets.LostPlanet;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:game_data.db";
    private Connection connection;
    
    public DatabaseManager() {
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            
            // Create the database and tables if they don't exist
            initializeDatabase();
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
    
    private void initializeDatabase() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            // Create saved games table
            String createSavedGamesTable = 
                "CREATE TABLE IF NOT EXISTS saved_games (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "save_name TEXT NOT NULL, " +
                "save_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "current_turn INTEGER NOT NULL)";
            
            // Create players table
            String createPlayersTable = 
                "CREATE TABLE IF NOT EXISTS saved_players (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "game_id INTEGER NOT NULL, " +
                "player_name TEXT NOT NULL, " +
                "player_index INTEGER NOT NULL, " +
                "planet_type TEXT NOT NULL, " +
                "health INTEGER NOT NULL, " +
                "max_health INTEGER NOT NULL, " +
                "missiles INTEGER NOT NULL, " +
                "max_missiles INTEGER NOT NULL, " +
                "is_alive BOOLEAN NOT NULL, " +
                "FOREIGN KEY (game_id) REFERENCES saved_games(id) ON DELETE CASCADE)";
            
            Statement stmt = conn.createStatement();
            stmt.execute(createSavedGamesTable);
            stmt.execute(createPlayersTable);
        }
    }
    
    public void connect() throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
    }
    
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
    
    public int saveGame(String saveName, List<Player> players, int currentTurn) {
        int gameId = -1;
        
        try {
            connect();
            connection.setAutoCommit(false);
            
            // Insert into saved_games table
            String insertGameSql = "INSERT INTO saved_games (save_name, current_turn) VALUES (?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(insertGameSql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, saveName);
                pstmt.setInt(2, currentTurn);
                pstmt.executeUpdate();
                
                // Get the generated game ID
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    gameId = rs.getInt(1);
                }
            }
            
            // Insert players data
            String insertPlayerSql = 
                "INSERT INTO saved_players (game_id, player_name, player_index, planet_type, " +
                "health, max_health, missiles, max_missiles, is_alive) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement pstmt = connection.prepareStatement(insertPlayerSql)) {
                for (int i = 0; i < players.size(); i++) {
                    Player player = players.get(i);
                    Planet planet = player.getPlanet();
                    
                    pstmt.setInt(1, gameId);
                    pstmt.setString(2, player.getName());
                    pstmt.setInt(3, i);
                    pstmt.setString(4, planet.getType());
                    pstmt.setInt(5, planet.getHealth());
                    pstmt.setInt(6, planet.getMaxHealth());
                    pstmt.setInt(7, planet.getMissiles());
                    pstmt.setInt(8, planet.getMaxMissiles());
                    pstmt.setBoolean(9, player.isAlive());
                    
                    pstmt.executeUpdate();
                }
            }
            
            connection.commit();
            System.out.println("Game saved successfully with ID: " + gameId);
            
        } catch (SQLException e) {
            System.err.println("Error saving game: " + e.getMessage());
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                System.err.println("Error rolling back transaction: " + ex.getMessage());
            }
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                System.err.println("Error resetting auto-commit: " + e.getMessage());
            }
            disconnect();
        }
        
        return gameId;
    }
    
    public GameSaveData loadGame(int gameId) {
        GameSaveData saveData = null;
        
        try {
            connect();
            
            // Get game data
            String selectGameSql = "SELECT id, save_name, current_turn FROM saved_games WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(selectGameSql)) {
                pstmt.setInt(1, gameId);
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    String saveName = rs.getString("save_name");
                    int currentTurn = rs.getInt("current_turn");
                    
                    List<PlayerSaveData> playerDataList = new ArrayList<>();
                    
                    // Get players data
                    String selectPlayersSql = 
                        "SELECT player_name, player_index, planet_type, health, max_health, " +
                        "missiles, max_missiles, is_alive FROM saved_players " +
                        "WHERE game_id = ? ORDER BY player_index";
                    
                    try (PreparedStatement playerStmt = connection.prepareStatement(selectPlayersSql)) {
                        playerStmt.setInt(1, gameId);
                        ResultSet playerRs = playerStmt.executeQuery();
                        
                        while (playerRs.next()) {
                            String playerName = playerRs.getString("player_name");
                            int playerIndex = playerRs.getInt("player_index");
                            String planetType = playerRs.getString("planet_type");
                            int health = playerRs.getInt("health");
                            int maxHealth = playerRs.getInt("max_health");
                            int missiles = playerRs.getInt("missiles");
                            int maxMissiles = playerRs.getInt("max_missiles");
                            boolean isAlive = playerRs.getBoolean("is_alive");
                            
                            PlayerSaveData playerData = new PlayerSaveData(
                                playerName, playerIndex, planetType, health, maxHealth, 
                                missiles, maxMissiles, isAlive
                            );
                            
                            playerDataList.add(playerData);
                        }
                    }
                    
                    saveData = new GameSaveData(gameId, saveName, currentTurn, playerDataList);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error loading game: " + e.getMessage());
        } finally {
            disconnect();
        }
        
        return saveData;
    }
    
    public List<GameSaveInfo> getSavedGamesList() {
        List<GameSaveInfo> savedGames = new ArrayList<>();
        
        try {
            connect();
            
            String selectSql = "SELECT id, save_name, save_date FROM saved_games ORDER BY save_date DESC";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(selectSql)) {
                
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("save_name");
                    String date = rs.getString("save_date");
                    
                    savedGames.add(new GameSaveInfo(id, name, date));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting saved games list: " + e.getMessage());
        } finally {
            disconnect();
        }
        
        return savedGames;
    }
    
    public boolean deleteGame(int gameId) {
        try {
            connect();
            
            String deleteSql = "DELETE FROM saved_games WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(deleteSql)) {
                pstmt.setInt(1, gameId);
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error deleting game: " + e.getMessage());
            return false;
        } finally {
            disconnect();
        }
    }
    
    // Helper classes for storing game data
    public static class GameSaveInfo {
        private int id;
        private String name;
        private String date;
        
        public GameSaveInfo(int id, String name, String date) {
            this.id = id;
            this.name = name;
            this.date = date;
        }
        
        public int getId() { return id; }
        public String getName() { return name; }
        public String getDate() { return date; }
        
        @Override
        public String toString() {
            return name + " (" + date + ")";
        }
    }
    
    public static class GameSaveData {
        private int id;
        private String name;
        private int currentTurn;
        private List<PlayerSaveData> players;
        
        public GameSaveData(int id, String name, int currentTurn, List<PlayerSaveData> players) {
            this.id = id;
            this.name = name;
            this.currentTurn = currentTurn;
            this.players = players;
        }
        
        public int getId() { return id; }
        public String getName() { return name; }
        public int getCurrentTurn() { return currentTurn; }
        public List<PlayerSaveData> getPlayers() { return players; }
    }
    
    public static class PlayerSaveData {
        private String name;
        private int index;
        private String planetType;
        private int health;
        private int maxHealth;
        private int missiles;
        private int maxMissiles;
        private boolean alive;
        
        public PlayerSaveData(String name, int index, String planetType, int health, 
                             int maxHealth, int missiles, int maxMissiles, boolean alive) {
            this.name = name;
            this.index = index;
            this.planetType = planetType;
            this.health = health;
            this.maxHealth = maxHealth;
            this.missiles = missiles;
            this.maxMissiles = maxMissiles;
            this.alive = alive;
        }
        
        public String getName() { return name; }
        public int getIndex() { return index; }
        public String getPlanetType() { return planetType; }
        public int getHealth() { return health; }
        public int getMaxHealth() { return maxHealth; }
        public int getMissiles() { return missiles; }
        public int getMaxMissiles() { return maxMissiles; }
        public boolean isAlive() { return alive; }
    }
} 