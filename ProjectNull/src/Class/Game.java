package Class;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Class.TypePlanets.Abyss;
import Class.TypePlanets.Glitch;

public class Game {
	private List<Player> players;
	private List<Planet> availablePlanets;
	
	public Game (List<Player> p) {
		availablePlanets =  new ArrayList<Planet>();
		players =  p;
	}
	
	private void loadPlanets() {
		
		Abyss abyss =  new Abyss();
		Glitch glitch =  new Glitch();
		
		availablePlanets.add(abyss);
		availablePlanets.add(glitch);
	}
	
	public void startGame() {
		loadPlanets();
		Collections.shuffle(availablePlanets);
		int x = 0;
		for(Player l : players) {		
			l.setPlanet(availablePlanets.get(x));
			x++;
		}
	}

	public List<Player> getPlayers() {
		return players;
	}

	
	
}
