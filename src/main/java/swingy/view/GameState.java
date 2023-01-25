package swingy.view;

import java.util.ArrayList;

import swingy.schema.Enemy;
import swingy.schema.Game;
import swingy.schema.Hero;

public class GameState {
	private Hero _currHero;
	private Game _currGame;
	private ArrayList<Enemy> _enemies;

	public Hero getCurrHero() {
		return _currHero;
	}
	public void setCurrHero(Hero _currHero) {
		this._currHero = _currHero;
	}
	public Game getCurrGame() {
		return _currGame;
	}
	public void setCurrGame(Game _currGame) {
		this._currGame = _currGame;
	}
	public ArrayList<Enemy> getEnemies() {
		return _enemies;
	}
	public void setEnemies(ArrayList<Enemy> _enemies) {
		this._enemies = _enemies;
	}
}
