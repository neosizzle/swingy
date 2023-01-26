package swingy.view.console;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import swingy.controller.GameController;
import swingy.interfaces.Command;
import swingy.interfaces.Coordinate;
import swingy.schema.Artifact;
import swingy.schema.Enemy;
import swingy.schema.Game;
import swingy.schema.Hero;
import swingy.view.GameState;
import swingy.view.Map;
import swingy.view.console.consoleManual.Manual;

public class ConsoleInstance {
	private Command<Number> _isGuiSwitch;
	private GameController _gameControllerRef;
	private GameState _gamestateRef;
	private final Scanner sc = new Scanner(System.in);
	private final Manual _man = new Manual();
	private final ConsoleUtils _consoleUtils = new ConsoleUtils();

	// only returns when false when hero is selected
	public boolean charSelect()
	{
		final ArrayList<Hero> heroes = _gameControllerRef.getHeroesList();
		Hero selected = null;

		while (selected == null) {
			// tell view to enable prompt to select or create

			_man.printHelpCharSelect();
			String str= sc.nextLine();
			
			//reads string 
			if (str.equals("select"))
			{
				
				String []columns = {"getId", "getName", "getLevel"};

				_consoleUtils.printSchemaTable(heroes, columns);


				while (selected == null) {

					// get input from view 
					System.out.print(">");  
					str = sc.nextLine();       //reads string
					int id = -1;

					try {
						id = Integer.parseInt(str);
					} catch (Exception e) {
						
					}
					selected = _gameControllerRef.handleSelect(id);
					if (selected == null)
						System.out.println("Invalid");
					else
					{
						_gamestateRef.setCurrHero(selected);
						return false;
					}
				}
			}
			else if (str.equals("create"))
			{
				while (selected == null) {

					// tell view to input name and class
					System.out.print("name >");  
					String name_str= sc.nextLine(); 

					System.out.print("class (JIMIN/ JUNGKOOK/ VI/ JHOPE/ TAEYUNG/ KIM_JUNG_UN) >");  
					String class_str= sc.nextLine();

					try {
						selected = _gameControllerRef.handleCreate(name_str, class_str);
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
					if (selected == null)
						System.err.println("Invalid hero");
					else
					{
						_gamestateRef.setCurrHero(selected);
						return false;
					}
				}
			}
			else if (str.equals("switch"))
			{
				_isGuiSwitch.runCommand();
				break;
			}
			else if (str.equals("exit"))
				System.exit(0);
		}
		return true;
	}

	public void artifactList(int currHeroId)
	{
		// get all artifacts with currhero id from controller
		ArrayList<Artifact> artifacts = _gameControllerRef.getArtifactsByHero(currHeroId);
		if (artifacts.size() == 0) System.out.println("No artifacts.");
		else
		{
			// display the artifacts in the list
			String []cols = {"getId", "getName", "getType", "getAttr", "getIsEquipped"};
			this._consoleUtils.printSchemaTable(artifacts, cols);
		}
	}

	public void test()
	{
		System.out.println(this._gamestateRef.getMap().toString());
	}

	public void artifactEquipId(int artifactId)
	{
		try {
			_gameControllerRef.equipArtifactOnHero(artifactId, _gamestateRef.getCurrHero());
			System.out.println("Artifact " + artifactId + " equipped.");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

	public void artifactUnequipId(int artifactId)
	{
		try {
			_gameControllerRef.unequipArtifactOnHero(artifactId, _gamestateRef.getCurrHero());
			System.out.println("Artifact " + artifactId + " unequipped.");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

	public void handleMove(String command)
	{
		String [] tokens = command.split(" ", 0);
		
		if (tokens.length < 2)
		{
			System.out.println("Invalid usage");
			return;
		}
		String direction = tokens[1];
		if (
			!direction.equalsIgnoreCase("n") &&
			!direction.equalsIgnoreCase("s") &&
			!direction.equalsIgnoreCase("w") &&
			!direction.equalsIgnoreCase("e") )
		{
			System.out.println("Invalid usage");
			return;
		}

		// save current coords
		Coordinate prevCoord = new Coordinate(_gamestateRef.getCurrGame().getPosRow(), _gamestateRef.getCurrGame().getPosCol());

		// get desired space entity
		char desiredEntity;

		desiredEntity = 0;
		if (direction.equalsIgnoreCase("n"))
			desiredEntity = _gamestateRef.getMap().getEntityAt(prevCoord.row - 1, prevCoord.col);
		if (direction.equalsIgnoreCase("s"))
			desiredEntity = _gamestateRef.getMap().getEntityAt(prevCoord.row + 1, prevCoord.col);
		if (direction.equalsIgnoreCase("e"))
			desiredEntity = _gamestateRef.getMap().getEntityAt(prevCoord.row, prevCoord.col + 1);
		if (direction.equalsIgnoreCase("w"))
			desiredEntity = _gamestateRef.getMap().getEntityAt(prevCoord.row, prevCoord.col - 1);

		// if its a wall or null, update game and map, u win
		if (desiredEntity == '=' || desiredEntity == 0)
		{
			// winning actions
			// delete enemies and game
			// exit program
		}

		// if its enemy, you fight / run
		if (desiredEntity == 'E')
		{
			// prompt to fight or run. 
			System.out.println("\n" +
			"#     # ### #    # #######  ##### \n" +
			" #   #   #  #   #  #       #     # \n" +
			"  # #    #  #  #   #       #       \n" +
			"   #     #  ###    #####    #####  \n" +
			"   #     #  #  #   #             # \n" +
			"   #     #  #   #  #       #     # \n" +
			"   #    ### #    # #######  #####  \n" +
											  "\n");
			System.out.println("You have touched an enemy without consent. \nType N to run, any other key to fight.");
			String line = sc.nextLine();
			Random random = new Random();

			// if run, return.
			if (line.startsWith("N"))
			{
				if (random.nextInt(10) < 5)
				{
					System.out.println("U ran, no ballz");
					return ;
				}
				System.out.println("u cant run hahahahahah get rekt");
			}

			// if fight, initiate combat
			System.out.println("You chose violence.");
				// if lose, game over delete enemies and game and exit

				// if win, remove enemy from db and gamestate
				// roll for artifact prompt
				// if user accepts, add to artifact inventory
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
		}
		// Map newMap = _gameControllerRef.handleMove(
		// 	_gamestateRef.getCurrGame(),
		// 	direction,
		// 	prevCoord,
		// 	_gamestateRef.getMap(),
		// 	_gamestateRef.getEnemies()
		// 	);
		// if (newMap != null)
		// 	_gamestateRef.setMap(newMap);
		System.out.println(_gamestateRef.getMap().toString());
	}

	public void displayStats()
	{
		System.out.println("====STATS====");
		System.out.println("Level: " + _gamestateRef.getCurrHero().getLevel());
		System.out.println("Exp: " + _gamestateRef.getCurrHero().getExp());
		System.out.println("MaxExp: " + _gamestateRef.getCurrHero().getMaxExp());
		System.out.println("Atk: " + _gamestateRef.getCurrHero().getAtk());
		System.out.println("Def: " + _gamestateRef.getCurrHero().getDef());
		System.out.println("Hp: " + _gamestateRef.getCurrHero().getHp());
		System.out.println("MaxHp: " + _gamestateRef.getCurrHero().getMaxHp());
		System.out.println("Id: " + _gamestateRef.getCurrHero().getId());
	}

	public void printMap()
	{
		System.out.println(this._gamestateRef.getMap().toString());
		System.out.println("= - walls");
		System.out.println("# - undiscovered area");
		System.out.println("- - discovered area");
		System.out.println("E - enemy");
		System.out.println("P - player");
	}

	public void printHelpMain()
	{
		_man.printHelpMain();
	}

	public ConsoleInstance(Command<Number> isGui,  GameController gameController, GameState gameState)
	{
		this._isGuiSwitch = isGui;
		this._gameControllerRef = gameController;
		this._gamestateRef = gameState;
	}
}
