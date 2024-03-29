package swingy.view.gui.components;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;	

import swingy.controller.GameController;
import swingy.interfaces.Coordinate;
import swingy.schema.Artifact;
import swingy.schema.Enemy;
import swingy.schema.Game;
import swingy.schema.Hero;
import swingy.view.GameState;
import swingy.view.Map;

public class ControlPanel {
	private JFrame _window;
	private GameController _gameControllerRef;
	private GameState _gamestateRef;
	private JPanel _contentPane;
	private MapPanel _mapPanel;
	private MessagePanel _msgPanel;
	private StatusPanel _statPanel;
	private SwitchButton _switchBtn;
	private ArtifactsPanel _artifactsPanel;
	private JButton _fight;
	private JButton _run;
	private Coordinate _desiredCoord;
	private boolean _inCombat = false;
	private final int PANE_WIDTH = 200;
	private final int PANE_HEIGHT = 800;

	// initiate combat sequence
	private void _initCombat(Coordinate desiredCoord)
	{
		_msgPanel.appendText("You chose violence.\n");
		Enemy enemy = _gameControllerRef.getEnemyFromGameIdAndPos(
			_gamestateRef.getCurrGame().getId(),
			desiredCoord.row,
			desiredCoord.col);
		Hero hero = _gamestateRef.getCurrHero();

		_msgPanel.appendText(enemy.getName() + ", " + enemy.getMaxHp() + "HP\n");
		int levelSave = hero.getLevel();

		while (enemy.getHp() > 0 && hero.getHp() > 0) {

			int dodge = new Random().nextInt((10 - 0) + 1);

			// your turn
			if (dodge < 2)
			{
				_msgPanel.appendText("Enemy dodged your attack.\n");
				continue;
			}
			int damage = hero.getAtk();
			enemy.setHp(enemy.getHp() - damage);
			_msgPanel.appendText("you hit enemy with " + damage + " damage\n");

			// enemy turn
			if (enemy.getHp() <= 0) break;
			if (dodge < 3)
			{
				_msgPanel.appendText("You dodged enemies attack\n");
				continue;
			}
			damage = enemy.getAtk();
			hero.setHp(hero.getHp() - damage);
			_msgPanel.appendText("enemy hit you with " + damage + " damage\n");
		}
		if (hero.getHp() <= 0)
		{
			// if lose, die.
			_msgPanel.appendText("\n\n\n you die\n\n\n");
			this._gameControllerRef.handleGameOVer(
				_gamestateRef.getCurrGame().getId(),
				hero
				);
			// System.exit(0);
		}
		else
		{
			// winning state
			_msgPanel.appendText("u win\n");

			// if win, remove enemy from db and gamestate
			_gameControllerRef.handleVictory(
				enemy,
				_gamestateRef.getEnemies(),
				hero,
				_gamestateRef.getCurrGame());
			
			if (levelSave < hero.getLevel())
				_msgPanel.appendText("You leveled up\n");

			// resetmap
			Map newMap = new Map(_gamestateRef.getCurrGame(), _gamestateRef.getEnemies());
			_gamestateRef.setMap(newMap);

			// update stat and map panel
			_mapPanel.update(_gamestateRef);
			_statPanel.update(_gamestateRef);

			// reset event listeners for window
			_window.requestFocusInWindow();

			// roll for artifact
			Artifact newArtifact = _gameControllerRef.rollNewArtifact(enemy, hero);
			if (newArtifact != null)
			{
				_msgPanel.appendText("You have found a new artifact!\n");
				_msgPanel.appendText(newArtifact.toString() + "\n");
				_msgPanel.appendText("you can check at the artifacts panel\n");
				_artifactsPanel.update();
			}
		}
	}

	// reset buttons based on combatstate
	private void _setButtons(boolean combatmode)
	{
		if (combatmode)
		{
			_inCombat = true;
			_switchBtn.disable();

			_fight.setEnabled(true);
			_run.setEnabled(true);
		}
		else
		{
			_inCombat = false;
			_switchBtn.enable();

			_fight.setEnabled(false);
			_run.setEnabled(false);
		}
	}

	// handle movement action 
	private void _handleMove(String direction)
	{
		// save current coords
		Coordinate prevCoord = new Coordinate(_gamestateRef.getCurrGame().getPosRow(), _gamestateRef.getCurrGame().getPosCol());

		// get desired coordinates
		Coordinate desiredCoord = new Coordinate(prevCoord.row, prevCoord.col);
		this._desiredCoord = desiredCoord;

		if (direction.equalsIgnoreCase("n"))
			desiredCoord.row -= 1;
		if (direction.equalsIgnoreCase("s"))
			desiredCoord.row += 1;
		if (direction.equalsIgnoreCase("e"))
			desiredCoord.col += 1;
		if (direction.equalsIgnoreCase("w"))
			desiredCoord.col -= 1;

		// get desired space entity
		char desiredEntity;
		desiredEntity = _gamestateRef.getMap().getEntityAt(desiredCoord.row, desiredCoord.col, true);

		// if its a wall or null, update game and map, u win
		if (desiredEntity == '=' || desiredEntity == 0)
		{
			// update message
			_msgPanel.appendText("\n\n\n you win gg \n\n\n");

			// disable all buttons and key listeners
			KeyListener listener[] = _window.getKeyListeners();
			for (KeyListener keyListener : listener) {
				_window.removeKeyListener(keyListener);
			}
			_switchBtn.disable();


			this._gameControllerRef.handleGameOVer(
				_gamestateRef.getCurrGame().getId(),
				_gamestateRef.getCurrHero()
				);
			return ;
		}

		// if its enemy, fight or run and lock movements
		if (desiredEntity == 'E')
		{
			// append explored
			// update game and map
			Game newGame = _gameControllerRef.handleExplore(
				_gamestateRef.getCurrGame(),
				direction,
				desiredCoord
				);
			if (newGame != null)
			{
				_gamestateRef.setCurrGame(newGame);
				_gamestateRef.setMap(new Map(newGame, _gamestateRef.getEnemies()));
				// update map panel
				_mapPanel.update(_gamestateRef);
			}


			// in combat
			_setButtons(true);
			_msgPanel.appendText("You have touched an enemy without consent. \nYou may choose to fight or run.\n\n");

			return ;
		}

		// update game and map
		Game newGame = _gameControllerRef.handleMove(
			_gamestateRef.getCurrGame(),
			direction,
			prevCoord
			);
		if (newGame != null)
		{
			_gamestateRef.setCurrGame(newGame);
			_gamestateRef.setMap(new Map(newGame, _gamestateRef.getEnemies()));
			// update map panel
			_mapPanel.update(_gamestateRef);
		}
	}

	public void destroy()
	{
		_window.remove(_contentPane);
	}

	public void create()
	{
		final int BTN_HEIGHT = 50;
		final int BTN_WIDTH = 50;

		// setup content pane
		_contentPane.setBounds(1300, 0, PANE_WIDTH, PANE_HEIGHT);
		_contentPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		_contentPane.setLayout(null);

		_window.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (_inCombat) return;
				if (arg0.getKeyCode() == KeyEvent.VK_W)
					_handleMove("N");
				else if (arg0.getKeyCode() == KeyEvent.VK_A)
					_handleMove("W");
				else if (arg0.getKeyCode() == KeyEvent.VK_S)
					_handleMove("S");
				else if (arg0.getKeyCode() == KeyEvent.VK_D)
					_handleMove("E");
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
			
		});

		JButton fight = new JButton("Fight");
		fight.setBounds(50, 500, BTN_WIDTH + 50, BTN_HEIGHT);
		fight.setEnabled(false);
		fight.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
						_initCombat(_desiredCoord);

						// check for death
						if (_gamestateRef.getCurrHero().getHp() <= 0)
						{
							_switchBtn.disable();
							_fight.setEnabled(false);
							_run.setEnabled(false);
							return ;
						}

						_setButtons(false);
					}  
				});
		this._fight = fight;

		JButton run = new JButton("Retreat");
		run.setBounds(50, 600, BTN_WIDTH + 50, BTN_HEIGHT);
		run.setEnabled(false);
		run.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){
						if (new Random().nextInt(10) < 5)
						{
							// append message
							_msgPanel.appendText("U ran, no ballz\n");

							// reset buttons
							_setButtons(false);

							return ;
						}
						_msgPanel.appendText("u cant run hahahahahah get rekt\n");
						_initCombat(_desiredCoord);

						// check for death
						if (_gamestateRef.getCurrHero().getHp() <= 0)
						{
							_switchBtn.disable();
							_fight.setEnabled(false);
							_run.setEnabled(false);
							return ;
						}

						_setButtons(false);

					}  
				});
		this._run = run;

		// set buttons unfocusable so window always remains focus
		run.setFocusable(false);
		fight.setFocusable(false);

		_contentPane.add(run);
		_contentPane.add(fight);
		_window.add(_contentPane);
	}

	public ControlPanel(JFrame window, GameState gamestate, GameController controller, MapPanel mapPanel, MessagePanel msgpanel, SwitchButton switchBtn, StatusPanel stat, ArtifactsPanel artifactsPanel)
	{
		this._window = window;
		this._gameControllerRef = controller;
		this._gamestateRef = gamestate;
		this._contentPane = new JPanel();
		this._mapPanel = mapPanel;
		this._msgPanel = msgpanel;
		this._switchBtn = switchBtn;
		this._statPanel = stat;
		this._artifactsPanel = artifactsPanel;
	}
}
