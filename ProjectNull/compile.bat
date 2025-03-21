@echo off
echo Compiling project...
javac -source 17 -target 17 src/classes/Player.java src/classes/Planet.java src/classes/planets/*.java src/classes/Bot.java src/swing/GameMenu.java src/swing/TeamMenu.java src/swing/BattleScreen.java
if errorlevel 1 (
    echo Compilation failed!
    pause
    exit /b 1
)
echo Compilation successful!
echo Starting game...
java -cp src swing.GameMenu
pause 