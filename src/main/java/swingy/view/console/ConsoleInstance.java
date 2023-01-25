package swingy.view.console;

import java.util.ArrayList;
import java.util.Scanner;

import swingy.controller.GameController;
import swingy.interfaces.Command;
import swingy.schema.Artifact;
import swingy.schema.Hero;
import swingy.view.GameState;
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

	public void artifactEquipId(int artifactId)
	{
		try {
			_gameControllerRef.equipArtifactOnHero(artifactId, _gamestateRef.getCurrHero());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

	public void artifactUnequipId(int artifactId)
	{
		try {
			_gameControllerRef.unequipArtifactOnHero(artifactId, _gamestateRef.getCurrHero());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

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
