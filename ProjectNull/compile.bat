@echo off
echo Compiling project...
javac --release 17 src/classes/Player.java src/classes/Planet.java src/classes/planets/*.java src/classes/Bot.java src/database/DatabaseManager.java src/swing/GameMenu.java src/swing/TeamMenu.java src/swing/SaveGameDialog.java src/swing/LoadGameDialog.java src/swing/BattleScreen.java src/swing/LanguageManager.java src/swing/LanguageDialog.java
if errorlevel 1 (
    echo Compilation failed!
    pause
    exit /b 1
)
echo Compilation successful!
echo Starting game...
java -cp src;lib/sqlite-jdbc-3.49.1.0.jar swing.GameMenu
pause 