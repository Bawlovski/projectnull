# Space Battle Game

This is a turn-based strategy game where players control planets and battle against each other.

## Game Features

- Turn-based combat system (Player → Bot 1 → Bot 2 → Bot 3 → Player)
- Different types of planets with unique abilities
- Attack with missiles, heal, defend, or regenerate missiles
- Save and load game functionality using SQLite database

## Database Setup

### Step 1: Download SQLite JDBC Driver

1. Go to the SQLite JDBC GitHub releases page: https://github.com/xerial/sqlite-jdbc/releases
2. Download the latest version JAR file (e.g., `sqlite-jdbc-3.42.0.0.jar`)

### Step 2: Add the Driver to Your Project

#### In NetBeans:
1. Right-click on your project in the Projects panel
2. Select "Properties"
3. Select "Libraries" from the left panel
4. Click on "Add JAR/Folder"
5. Navigate to the downloaded SQLite JDBC JAR file and select it
6. Click "Open" and then "OK"

#### In Eclipse:
1. Right-click on your project in the Package Explorer
2. Select "Build Path" → "Configure Build Path"
3. Select the "Libraries" tab
4. Click "Add External JARs"
5. Navigate to the downloaded SQLite JDBC JAR file and select it
6. Click "Open" and then "OK"

#### In IntelliJ IDEA:
1. Go to File → Project Structure (or press Ctrl+Alt+Shift+S)
2. Select "Libraries" from the left panel
3. Click the "+" button and select "Java"
4. Navigate to the downloaded SQLite JDBC JAR file and select it
5. Click "OK" and then "Apply"

### Step 3: Verify Database Connectivity

When you first save a game, the application will automatically:
1. Create a `game_data.db` file in the root directory of your project
2. Set up the necessary tables for storing game data

To verify that your database is working correctly:
1. Run the game
2. Start a new game
3. Make some moves and then click the "SAVE GAME" button
4. Enter a name for your save
5. Check that the save completes successfully

### Database Structure

The game uses two main tables:

#### saved_games
- `id`: Unique identifier for each saved game
- `save_name`: Name given to the saved game
- `save_date`: Timestamp when the game was saved
- `current_turn`: Which player's turn it was when saved

#### saved_players
- `id`: Unique identifier for each player record
- `game_id`: Reference to the saved game
- `player_name`: Name of the player
- `player_index`: Position in the turn order
- `planet_type`: Type of planet ("Dark", "Glitch", or "Lost")
- `health`: Current health points
- `max_health`: Maximum health points
- `missiles`: Current missile count
- `max_missiles`: Maximum missile capacity
- `is_alive`: Whether the player is still alive

### Troubleshooting Database Issues

#### File Permission Errors
If you encounter file permission errors:
1. Ensure your user account has write permissions in the project directory
2. Try running the application as administrator
3. Check if any antivirus software is blocking file access

#### Driver Not Found Error
If you see "SQLite JDBC driver not found" error:
1. Double-check that you've correctly added the JAR file to your classpath
2. Verify you're using a compatible JDK version (Java 8 or higher recommended)
3. Try downloading a different version of the SQLite JDBC driver

#### Database Locked Errors
If you see database locked errors:
1. Ensure you don't have the database open in another program
2. Check if multiple instances of the game are running simultaneously
3. Restart your IDE and try again

## Game Controls

- Attack: Select number of missiles and target
- Defend: Increase defense (partial implementation)
- Heal: Restore health points
- Regenerate Missiles: Add more missiles to your arsenal
- Save Game: Save the current game state to continue later

## Exploring the Database

If you want to explore the database file:

1. Download and install DB Browser for SQLite: https://sqlitebrowser.org/
2. Open the `game_data.db` file using DB Browser
3. You can view tables, execute SQL queries, and explore your saved games 