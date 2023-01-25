package swingy.view;

import swingy.schema.Hero;

public class GameState {
	private Hero _currHero;
	//map and game go here....

	public Hero getCurrHero() {
		return _currHero;
	}
	public void setCurrHero(Hero _currHero) {
		this._currHero = _currHero;
	}
}
