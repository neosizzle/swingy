package swingy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import swingy.enums.ClassName;
import swingy.interfaces.Coordinate;
import swingy.model.Model;
import swingy.schema.Artifact;
import swingy.schema.Enemy;
import swingy.schema.Game;
import swingy.schema.Hero;
import swingy.view.Map;

public class GameController {
	Model model;
	private static Validator validator;

	public static void setUpValidator() {
	   ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	   validator = factory.getValidator();
   }

	// gets all herores form db
	public ArrayList<Hero> getHeroesList() {
		return model.getAllHeros();
	}

	
	// handles getting artifacts
	public ArrayList<Artifact> getArtifactsByHero(int heroId) 
	{
		ArrayList<Artifact> res = model.getAllArtifactsByHeroId(heroId);
		return res;
	}

	// handles equipping artifact on hero
	public void equipArtifactOnHero(int artifactId, Hero hero) throws Exception
	{
		model.equipArtifactOnHero(artifactId, hero);
	}

	// handles unequipping artifact on hero
	public void unequipArtifactOnHero(int artifactId, Hero hero) throws Exception
	{
		model.unequipArtifactOnHero(artifactId, hero);
	}

	// get enemies for game or add enemies
	public ArrayList<Enemy> getOrAddEnemies(Game game)
	{
		ArrayList<Enemy> res = new ArrayList<Enemy>();
		res = model.getEnemiesFromGameId(game.getId());
		if (res.size() == 0)
		{
			for (int index = 0; index < 80; index++) {
				Random rand = new Random();
				int x = rand.nextInt((40 - 0) + 1);
				int y = rand.nextInt((40 - 0) + 1);

				// spawn position
				if (x == game.getPosCol() && y == game.getPosRow()) continue;

				Enemy newEnemy = new Enemy("someenemy", 5, 5, 5, 10, game.getId(),x, y, 1);

				// check if position is occupied
				if (model.getEnemiesFromGameIdAndPos(game.getId(), x, y).size() > 0)
					continue;

				int id = model.addEnemy(newEnemy);

				newEnemy.setId(id);
				res.add(newEnemy);

			}
		}

		return res;
	}

	// get game or generate game
	public Game getOrAddGame(Hero hero)
	{
		Game res;

		res = model.getGameByHeroId(hero.getId());
		if (res == null)
			res = model.generateNewGame(hero);
		return res;
	}

	// handle movement and generates new map
	public Game handleMove(Game game, String direction, Coordinate prevCoords)
	{
		try {
			// attempt to update game in db
			Game newGame = model.moveDirection(game, direction, prevCoords);
			
			// return newgame
			return newGame;
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Move error");
			return null;
		}
	}

	// handles hero selection action
	public Hero handleSelect(int id){
		Hero selected;

		selected = model.getHero(id);
		return selected;
	}

	// handles hero craetion action
	public Hero handleCreate(String name, String class_str)
	{
		Hero newHero = null;

		java.util.Map<String, ClassName> classMap = new HashMap<String, ClassName>();
		classMap.put("JIMIN", ClassName.JIMIN);
		classMap.put("JUNGKOOK", ClassName.JUNGKOOK);
		classMap.put("JHOPE", ClassName.JHOPE);
		classMap.put("VI", ClassName.VI);
		classMap.put("KIM_JUNG_UN", ClassName.KIM_JUNG_UN);
		classMap.put("TAEYUNG", ClassName.TAEYUNG);

		if (!classMap.keySet().contains(class_str))
			throw new ConstraintViolationException("Invalid className", null);
		
		newHero = new Hero(name, classMap.get(class_str));

		Set<ConstraintViolation<Hero>> constraintSet = validator.validate(newHero);
		if (constraintSet.size() > 0) throw new ConstraintViolationException(constraintSet);
		newHero.setId(model.addHero(newHero));
		return newHero; 
	}

	public GameController(Model m)
	{
		setUpValidator();
		this.model = m;
	}
}
