# Space Battle Game

This is a turn-based strategy game where players control planets and battle against each other.

## Game Features

- Turn-based combat system (Player → Bot 1 → Bot 2 → Bot 3 → Player)
- Different types of planets with unique abilities
- Attack with missiles, heal, defend, or regenerate missiles
- Save and load game functionality using SQLite database

## Setup Instructions

### SQLite JDBC Driver

For the save/load functionality to work, you need to add the SQLite JDBC driver to your project:

1. Download the latest SQLite JDBC driver jar from https://github.com/xerial/sqlite-jdbc/releases
2. Create a "lib" folder in your project root if it doesn't exist
3. Place the downloaded jar file in the "lib" folder
4. Add the jar to your project's classpath:
   - In NetBeans: Right-click on your project → Properties → Libraries → Add JAR/Folder
   - In Eclipse: Right-click on your project → Build Path → Configure Build Path → Libraries → Add External JARs
   - In IntelliJ IDEA: File → Project Structure → Libraries → + (Add Library) → Java

### Running the Game

1. Compile and run the `main.java` file
2. Choose "New Game" to start a new game or "Load Game" to load a saved game
3. During gameplay, use the "SAVE GAME" button to save your progress

## Troubleshooting

If you encounter database errors, ensure:
1. The SQLite JDBC driver is properly added to your classpath
2. Your user account has write permissions in the directory where the game is running

## Game Controls

- Attack: Select number of missiles and target
- Defend: Increase defense (partial implementation)
- Heal: Restore health points
- Regenerate Missiles: Add more missiles to your arsenal
- Save Game: Save the current game state to continue later 