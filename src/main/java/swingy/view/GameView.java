package swingy.view;

import java.util.Scanner;

import javax.swing.JFrame;
import swingy.controller.GameController;
import swingy.interfaces.Command;
import swingy.schema.Hero;
import swingy.view.console.ConsoleInstance;
import swingy.view.gui.GuiInstance;

public class GameView {

	private GameController _controller;
	private boolean _isGui = false;
	private GameState _gameState;
	private ConsoleInstance _consoleInstance;
	private GuiInstance _guiInstance;
	private final Scanner sc = new Scanner(System.in);
	private final Command<Number> _guiSwitch = new Command<Number>() {
		public Number runCommand(){
			_isGui = !_isGui;
			return 0;
		}
	};

	public void charSelect()
	{
		
		// create frame for charselect
		final JFrame f_charSelect= new JFrame();
		
		Boolean isProcessing;
		isProcessing = true;

		while (isProcessing) {
			// console mode
			if (!_isGui)
				isProcessing = _consoleInstance.charSelect();
			// gui mode
			else
			{
				_guiInstance.charSelect(f_charSelect);

				// wait for hero to get selected (thread safe??)
				while (_gameState.getCurrHero() == null) {
					try {
						if (_isGui == true)
							Thread.sleep(10);
						else break;
					} catch (Exception e) {
					}
				}
				if (_gameState.getCurrHero() != null) 
					isProcessing = false;
			}
		}
		
		// destroy jframe
		f_charSelect.dispose();

	}

	private void gameStart()
	{
		
		boolean isRunning;
		JFrame f_mainGame = new JFrame();;

		isRunning = true;
		if (!this._isGui)
			_consoleInstance.printHelpMain();
		while (isRunning) {
			// console game
			if (!this._isGui)
			{
				// get input
				System.out.print(">");
				String line = sc.nextLine();
				
				// run insstructions based on line
				if (line.startsWith("help"))
					_consoleInstance.printHelpMain();
				else if (line.equals("stat"))
					_consoleInstance.displayStats();
				else if (line.startsWith("artifacts list") || line.startsWith("a l"))
					_consoleInstance.artifactList(_gameState.getCurrHero().getId());
				else if (line.startsWith("artifacts equip ") || line.startsWith("a e "))
					_consoleInstance.artifactEquipId(Integer.parseInt(line.split(" ")[2]));
				else if (line.startsWith("artifacts unequip ") || line.startsWith("a u "))
					_consoleInstance.artifactUnequipId(Integer.parseInt(line.split(" ")[2]));
				else if (line.startsWith("move "))
					_consoleInstance.handleMove(line);
				else if (line.startsWith("map"))
					_consoleInstance.printMap();
				else if (line.equals("switch"))
					this._isGui = true;
				else if (line.equals("exit"))
					isRunning = false;
				else 
					System.out.println("Unknown command \""+line+"\"");
			}
			// gui game
			else
			{
				f_mainGame = new JFrame();
				f_mainGame.setBounds(0, 0, 1360, 768);
				this._guiInstance.setGamestateRef(_gameState);
				this._guiInstance.startGame(f_mainGame);
				while (this._isGui) {
					try {
						Thread.sleep(500);
					} catch (Exception e) {
					}
				}
			}
		}

		// close scanner
		// sc.close();
		f_mainGame.dispose();
	}

	public void start()
	{
		this.charSelect();
		System.out.println("selected hero: " + _gameState.getCurrHero().getId() + ", " + _gameState.getCurrHero().getName());
		// generate or get game
		this._gameState.setCurrGame(_controller.getOrAddGame(_gameState.getCurrHero()));

		// generate or add enemies
		this._gameState.setEnemies(this._controller.getOrAddEnemies(_gameState.getCurrGame()));

		// set map
		this._gameState.setMap(new Map(this._gameState.getCurrGame(), this._gameState.getEnemies()));

		this.gameStart();

		sc.close();
	}

	public GameView(GameController controller, String mode)
	{
		this._controller = controller;
		this._isGui = mode.equals("gui");
		this._gameState = new GameState();
		this._consoleInstance = new ConsoleInstance(_guiSwitch, controller, this._gameState);
		this._guiInstance = new GuiInstance(_guiSwitch, controller, this._gameState);

		// initialize window stuff here?
	}
}
