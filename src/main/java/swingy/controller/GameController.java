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

import swingy.enums.ArtifactQuality;
import swingy.enums.ArtifactType;
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

	// roll new artifact
	public Artifact rollNewArtifact(Enemy enemy, Hero hero)
	{
		int rollChance = new Random().nextInt((10 - 0) + 1);
		int levelDiff = Math.abs(enemy.getLevel() - hero.getLevel());

		if (rollChance < 4) return null;

		ArtifactQuality quality = ArtifactQuality.SOFT;

		if (levelDiff == 1) quality = ArtifactQuality.LIMP;
		else if (new Random().nextInt(2) != 0) quality = ArtifactQuality.HARD;
		else quality = ArtifactQuality.VIGIROUS;


		// attr =(base * qualitymult) + herolevel
		int qualitymult = quality == ArtifactQuality.LIMP ? 2 :
			quality == ArtifactQuality.SOFT ? 3 :
			quality == ArtifactQuality.HARD ? 4 :
			5;

		int attr = (2 * qualitymult) + hero.getLevel();

		ArtifactType type = ArtifactType.ARMOR;
		int typeRandomSeed = new Random().nextInt(3);
		if (typeRandomSeed == 0)
			type = ArtifactType.HELM;
		if (typeRandomSeed == 1)
			type = ArtifactType.WEAPON;
		
		Artifact newArtifact = new Artifact("some artifact name", quality, type, attr, false, hero.getId());
		int id = model.addArtifact(newArtifact);
		newArtifact.setId(id);
		return newArtifact;
	}

	// get enemies for game or add enemies
	public ArrayList<Enemy> getOrAddEnemies(Game game)
	{
		ArrayList<Enemy> res = new ArrayList<Enemy>();
		res = model.getEnemiesFromGameId(game.getId());
		if (res.size() == 0)
		{
			for (int index = 0; index < 360; index++) {
				Random rand = new Random();
				int x = rand.nextInt((40 - 0) + 1);
				int y = rand.nextInt((40 - 0) + 1);

				// spawn position
				if (x == game.getPosCol() && y == game.getPosRow()) continue;

				Enemy newEnemy = new Enemy("someenemy", 20, 5, 5, 20, game.getId(),x, y, 1);

				// check if position is occupied
				if (model.getEnemyFromGameIdAndPos(game.getId(), x, y) != null)
					continue;

				int id = model.addEnemy(newEnemy);

				newEnemy.setId(id);
				res.add(newEnemy);

			}
		}

		return res;
	}

	// get enemy from gameid and pos
	public Enemy getEnemyFromGameIdAndPos(int gameId, int row, int col)
	{
		return model.getEnemyFromGameIdAndPos(gameId, col, row);
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

	// handle hero death
	public void handleGameOVer(int gameId, Hero hero)
	{
		try {
			// delete enemies
			model.deleteEnemiesByGameId(gameId);

			// delete game
			model.deleteGameById(gameId);

			// reset hp
			model.respawnHeroById(hero);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error respawing");
		}
	}

	// handle victory and move
	public void handleVictory(Enemy enemy, ArrayList<Enemy> enemies, Hero hero, Game game)
	{
		try {
			// remove enemy from db
			model.deleteEnemiesByEnemyId(enemy.getId());

			// remove enemy from gamestate
			int indexToRm = -1;
			for (int i = 0; i < enemies.size(); i++) {
				if (enemies.get(i).getId() == enemy.getId())
				{
					indexToRm = i;
					break ;
				}
			}
			if (indexToRm >= 0)
				enemies.remove(indexToRm);
			
			// update hp
			int levelDiff = enemy.getLevel() - hero.getLevel();
			int expGain = (int) ((int) hero.getMaxExp() * ((levelDiff + 1) * 0.25)) + 1;

			if (expGain + hero.getExp() >= hero.getMaxExp())
			{
				// lv up
				model.levelUpHero(hero);

				// map expand
				game.setWidth(game.getWidth() + 5);
				game.setHeight(game.getHeight() + 5);
				model.expandGame(game);

				//upgrade enemies
				for (Enemy _enemy : enemies) {
					model.buffEnemy(_enemy);
				}
				return;
			}

			// no lv up, update exp and hp
			hero.setExp(expGain + hero.getExp());
			model.updateHeroHp(hero);
			model.updateHeroExp(hero);

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Cant handle victory");
		}
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
