package swingy.model;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import swingy.enums.ArtifactQuality;
import swingy.enums.ArtifactType;
import swingy.enums.ClassName;
import swingy.interfaces.Coordinate;
import swingy.schema.Artifact;
import swingy.schema.Enemy;
import swingy.schema.Game;
import swingy.schema.Hero;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Model {
	private Connection _connection;
	private Statement _statement;
	
	/**
	 * INIT TABLES
	 */
	// init heroes table
	private void _initHeroesTable()
	{
		// create heroes table
		String query = "CREATE TABLE IF NOT EXISTS HEROES " +
		"(id			INT			AUTO_INCREMENT PRIMARY KEY     	NOT NULL ," +
		" name  VARCHAR(50)  NOT NULL, " + 
		" class  VARCHAR(50)  NOT NULL, " + 
		" level  INT  NOT NULL, " +
		" exp  INT  NOT NULL, " + 
		" atk  INT  NOT NULL, " + 
		" def  INT  NOT NULL, " + 
		" hp  INT  NOT NULL, " + 
		" maxExp  INT  NOT NULL, " + 
		" maxhp  INT  NOT NULL)" ; 

		try {
			this._statement.executeUpdate(query);
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	}

	// init artifacts table
	private void _initArtifactsTable()
	{
		// create artifacts table
		String query = "CREATE TABLE IF NOT EXISTS ARTIFACTS " +
		"(id			INT			AUTO_INCREMENT PRIMARY KEY     	NOT NULL ," +
		" name			VARCHAR(50) 			NOT NULL, " + 
		" type			VARCHAR(50)   			NOT NULL, " + 
		" quality		VARCHAR(50)				NOT NULL, " +
		" attr			INT						NOT NULL, " + 
		" isEquipped	INT						NOT NULL, " + 
		" ownedBy		INT						NOT NULL)";  

		try {
			this._statement.executeUpdate(query);
		} catch (Exception e) {
			System.out.println("Cant Execute query: ");
			System.out.println(e);
			System.exit(1);
		}
	}

	// init games table
	private void _initGamesTable()
	{
		// create heroes table
		String query = "CREATE TABLE IF NOT EXISTS GAMES " +
		"(id			INT			AUTO_INCREMENT PRIMARY KEY     	NOT NULL ," +
		" explored			VARCHAR(5000) 		NOT NULL, " + 
		" posX			INT						NOT NULL, " +
		" posY			INT						NOT NULL, " +
		" width			INT						NOT NULL, " + 
		" height		INT						NOT NULL, " + 
		" heroId		INT						NOT NULL)";  

		try {
			this._statement.executeUpdate(query);
		} catch (Exception e) {
			System.out.println("Cant Execute query: ");
			System.out.println(e);
			System.exit(1);
		}
	}

	// init enemies table
	private void _initEnemiesTable()
	{
		// create heroes table
		String query = "CREATE TABLE IF NOT EXISTS ENEMIES " +
		"(id			INT			AUTO_INCREMENT PRIMARY KEY     	NOT NULL ," +
		" name			VARCHAR(50) 			NOT NULL, " + 
		" posX			INT						NOT NULL, " +
		" posY			INT						NOT NULL, " +
		" hp			INT						NOT NULL, " + 
		" def			INT						NOT NULL, " + 
		" maxHp			INT						NOT NULL, " + 
		" atk			INT						NOT NULL, " + 
		" gameId		INT						NOT NULL, " + 
		" level			INT						NOT NULL)";  

		try {
			this._statement.executeUpdate(query);
		} catch (Exception e) {
			System.out.println("Cant Execute query: ");
			System.out.println(e);
			System.exit(1);
		}
	}

	// initialize all tables
	private void _initAllTables()
	{
		this._initHeroesTable();
		this._initArtifactsTable();
		this._initEnemiesTable();
		this._initGamesTable();
		
		System.out.println("Tables initialized");
	}

	// gets max id from table
	private int _getMaxId(String table)
	{
		String query = "SELECT MAX(id) AS LAST FROM " + table;
		try {
			ResultSet rs = this._statement.executeQuery(query);
			return rs.getInt("last");
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}
		return -1;
	}

	/**
	 * HERO
	 */
	// Adds hero to database (returns id of new hero), -1 on err
	public int addHero(Hero hero)
	{

		String query = "INSERT INTO HEROES (name, class, level, exp, atk, def, hp, maxExp, maxhp, id) VALUES (?, ?,?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement stmt = this._connection.prepareStatement(query);
			int id = _getMaxId("HEROES") + 1;

			stmt.setString(1, hero.getName());
			stmt.setString(2, hero.getClassName().name());
			stmt.setInt(3, hero.getLevel());
			stmt.setInt(4, hero.getExp());
			stmt.setInt(5, hero.getAtk());
			stmt.setInt(6, hero.getDef());
			stmt.setInt(7, hero.getHp());
			stmt.setInt(8, hero.getMaxExp());
			stmt.setInt(9, hero.getMaxHp());
			stmt.setInt(10, id);

			stmt.executeUpdate();
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return -1;
	}

	// Returns a hero from resultset
	private Hero _getHeroFromResultSet(ResultSet rs)
	{
		try {
			int	id = rs.getInt("id");
			String name = rs.getString("name");
			String _className = rs.getString("class");
			int level = rs.getInt("level");
			int exp = rs.getInt("exp");
			int atk = rs.getInt("atk");
			int def = rs.getInt("def");
			int hp = rs.getInt("hp");
			int maxExp = rs.getInt("maxExp");
			int maxHp = rs.getInt("maxHp");

			ClassName className;
			if (_className.equals("JIMIN"))
				className = ClassName.JIMIN;
			else if (_className.equals("JUNGKOOK"))
				className = ClassName.JUNGKOOK;
			else if (_className.equals("VI"))
				className = ClassName.VI;
			else if (_className.equals("JHOPE"))
				className = ClassName.JHOPE;
			else if (_className.equals("TAEYUNG"))
				className = ClassName.TAEYUNG;
			else
				className = ClassName.KIM_JUNG_UN;

			// create new hero object
			Hero newHero = new Hero(name, className, level, exp, maxExp, atk, def, hp, maxHp);
			newHero.setId(id);

			return newHero;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// get hero by heroid
	public Hero getHero(int heroId)
	{
		String query = "SELECT * FROM HEROES WHERE id = " + heroId;
		
		try {
			ResultSet rs = this._statement.executeQuery(query);

			// not found
			if (!rs.next())
				return null;
			// get all the sutff
			Hero newHero = _getHeroFromResultSet(rs);
			return newHero;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	// list all hereos
	public ArrayList<Hero> getAllHeros()
	{
		ArrayList<Hero> res = new ArrayList<Hero>();
		String query = "SELECT * FROM HEROES";

		try {
			ResultSet rs = this._statement.executeQuery(query);

			while (rs.next()) {
				// get all the sutff
				int	id = rs.getInt("id");
				String name = rs.getString("name");
				String _className = rs.getString("class");
				int level = rs.getInt("level");
				int exp = rs.getInt("exp");
				int atk = rs.getInt("atk");
				int def = rs.getInt("def");
				int hp = rs.getInt("hp");
				int maxExp = rs.getInt("maxExp");
				int maxHp = rs.getInt("maxHp");

				ClassName className;
				if (_className.equals("JIMIN"))
					className = ClassName.JIMIN;
				else if (_className.equals("JUNGKOOK"))
					className = ClassName.JUNGKOOK;
				else if (_className.equals("VI"))
					className = ClassName.VI;
				else if (_className.equals("JHOPE"))
					className = ClassName.JHOPE;
				else if (_className.equals("TAEYUNG"))
					className = ClassName.TAEYUNG;
				else
					className = ClassName.KIM_JUNG_UN;

				// create new hero object
				Hero newHero = new Hero(name, className, level, exp, maxExp, atk, def, hp, maxHp);
				newHero.setId(id);

				// append to res
				res.add(newHero);
			}
		} catch (Exception e) {
			System.out.print("Error getting heroes");
			System.out.println(e);
			System.exit(1);
		}

		return res;
	}

	// reset hp
	public void respawnHeroById(Hero hero) throws SQLException
	{
		int maxHp = hero.getMaxHp();
		String query = "UPDATE HEROES SET hp = " + maxHp + " WHERE id = " + hero.getId();
		this._statement.executeUpdate(query);
	}

	// update hero exp
	public void updateHeroExp(Hero hero) throws SQLException
	{
		String query = "UPDATE HEROES SET exp = " + hero.getExp() + " WHERE id = " + hero.getId();
		this._statement.executeUpdate(query);
	}

	// update hero hp
	public void updateHeroHp(Hero hero) throws SQLException
	{
		String query = "UPDATE HEROES SET hp = " + hero.getHp() + " WHERE id = " + hero.getId();
		this._statement.executeUpdate(query);
	}

	public void levelUpHero(Hero hero) throws SQLException
	{
		if (hero.getLevel() == 7) return;
		hero.setLevel(hero.getLevel() + 1);
		hero.setMaxExp((int) (hero.getLevel() * 1000 + Math.pow(hero.getLevel() - 1, 2) * 450));
		hero.setExp(0);
		hero.setDef(hero.getDef() + 2);
		hero.setAtk(hero.getAtk() + 2);
		hero.setMaxHp(hero.getMaxHp() + 10);
		hero.setHp(hero.getMaxHp());

		String query = "UPDATE HEROES SET level = " + hero.getLevel() + 
					", maxExp = " + hero.getMaxExp() + 
					", exp = "+  hero.getExp() +
					", def = " + hero.getDef() + 
					", atk = " + hero.getAtk() + 
					", maxHp = " + hero.getMaxHp() + 
					", hp = " + hero.getHp() +
					" WHERE id = " + hero.getId();

		this._statement.executeUpdate(query);
	}

	public Hero getHeroById(int _id)
	{
		Hero res;

		res = null;
		String query = "SELECT * FROM HEROES WHERE ID = " + _id;

		try {
			ResultSet rs = this._statement.executeQuery(query);

			if (rs.next()) {
				// get all the sutff
				int	id = rs.getInt("id");
				String name = rs.getString("name");
				String _className = rs.getString("class");
				int level = rs.getInt("level");
				int exp = rs.getInt("exp");
				int atk = rs.getInt("atk");
				int def = rs.getInt("def");
				int hp = rs.getInt("hp");
				int maxExp = rs.getInt("maxExp");
				int maxHp = rs.getInt("maxHp");

				ClassName className;
				if (_className.equals("JIMIN"))
					className = ClassName.JIMIN;
				else if (_className.equals("JUNGKOOK"))
					className = ClassName.JUNGKOOK;
				else if (_className.equals("VI"))
					className = ClassName.VI;
				else if (_className.equals("JHOPE"))
					className = ClassName.JHOPE;
				else if (_className.equals("TAEYUNG"))
					className = ClassName.TAEYUNG;
				else
					className = ClassName.KIM_JUNG_UN;

				// create new hero object
				Hero newHero = new Hero(name, className, level, exp, maxExp, atk, def, hp, maxHp);
				newHero.setId(id);

				// append to res
				res = newHero;
			}
		} catch (Exception e) {
			System.out.print("Error getting heroes");
			System.out.println(e);
			System.exit(1);
		}


		return res;
	}

	/**
	 * GAME
	 */

	// transforms rs into game object
	private Game _getGameFromRs(ResultSet rs)
	{
		Game res;

		res = null;
		try {
			int id = rs.getInt("id");
			int posX = rs.getInt("posX");
			int posY = rs.getInt("posY");
			int width = rs.getInt("width");
			int height = rs.getInt("height");
			int heroId = rs.getInt("heroId");
			String explored = rs.getString("explored");

			res = new Game(heroId, width, height, posX, posY, explored);
			res.setId(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		return res;
	}

	// gets game from heroid
	public Game getGameByHeroId(int heroId)
	{
		String query = "SELECT * FROM GAMES WHERE heroId = " + heroId;
		Game res;

		res = null;
		try {
			ResultSet rs = this._statement.executeQuery(query);
			if (!rs.next())
				return null;
			res = _getGameFromRs(rs);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return res;
	}

	// add game to db
	public int addGame(Game game)
	{
		String query = "INSERT INTO GAMES (id, explored, posX, posY, width, height, heroId) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement stmt = this._connection.prepareStatement(query);
			int id = _getMaxId("GAMES") + 1;


			stmt.setInt(1, id);
			stmt.setString(2, game.getExplored());
			stmt.setInt(3, game.getPosCol());
			stmt.setInt(4, game.getPosRow());
			stmt.setInt(5, game.getWidth());
			stmt.setInt(6, game.getHeight());
			stmt.setInt(7, game.getHeroId());

			stmt.executeUpdate();
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return -1;
	}

	// generate new game and add enemies
	public Game generateNewGame(Hero hero)
	{
		int dim = (hero.getLevel() - 1) * 5 + 10;
		Game res = new Game(hero.getId(), dim, dim, 20, 20, "(20,20);");
		int id = addGame(res);

		res.setId(id);
		return res;
	}

	// handles movement
	public Game moveDirection(Game game, String direction, Coordinate prevCoord) throws SQLException
	{
		String query;
		String directionVariable;
		String exploredVariable;
		Game res;

		res = null;
		directionVariable = "";
		if (direction.equalsIgnoreCase("n"))
			directionVariable = "posY = " + (prevCoord.row - 1);
		if (direction.equalsIgnoreCase("s"))
			directionVariable = "posY = " + (prevCoord.row + 1);
		if (direction.equalsIgnoreCase("e"))
			directionVariable = "posX = " + (prevCoord.col + 1);
		if (direction.equalsIgnoreCase("w"))
			directionVariable = "posX = " + (prevCoord.col - 1);

		exploredVariable = "explored = \"" + game.getExplored() + prevCoord.toString() + ";\"";

		query = "UPDATE GAMES SET " + directionVariable + ", " + exploredVariable + " WHERE id = " + game.getId();
		this._statement.executeUpdate(query);
		res = getGameByHeroId(game.getHeroId());
		return res;
	}

	// handles explore but not move
	public Game exploreDirection(Game game, String direction, Coordinate prevCoord) throws SQLException
	{
		String query;
		String exploredVariable;
		Game res;

		res = null;

		exploredVariable = "explored = \"" + game.getExplored() + prevCoord.toString() + ";\"";

		query = "UPDATE GAMES SET " +  exploredVariable + " WHERE id = " + game.getId();
		this._statement.executeUpdate(query);
		res = getGameByHeroId(game.getHeroId());
		return res;
	}

	public void deleteGameById(int id) throws SQLException
	{
		String query = "DELETE FROM GAMES WHERE id = " + id;
		this._statement.executeUpdate(query);
	}

	// expand game by id
	public void expandGame(Game game) throws SQLException
	{
		String query = "UPDATE GAMES SET width = " + game.getWidth() + ", height = " + game.getHeight() + " WHERE id = " + game.getId();

		this._statement.executeUpdate(query);
	}
	/**
	 * ENEMIES
	 */

	// get enemy object from rs
	public Enemy getEnemyFromRs(ResultSet rs)
	{
		Enemy res;
		res = null;

		try {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			int posX = rs.getInt("posX");
			int posY = rs.getInt("posY");
			int hp = rs.getInt("hp");
			int def = rs.getInt("def");
			int maxhp = rs.getInt("maxhp");
			int atk = rs.getInt("atk");
			int gameId = rs.getInt("gameId");
			int level = rs.getInt("level");

			res = new Enemy(name, hp, def, atk, maxhp, gameId, posX, posY, level);
			res.setId(id);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		return res;
	}

	// add enemy to db
	public int addEnemy(Enemy enemy)
	{
		String query = "INSERT INTO ENEMIES (id, name, posX, posY, hp, def, maxhp, atk, gameId, level) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement stmt = this._connection.prepareStatement(query);
			int id = _getMaxId("ENEMIES") + 1;


			stmt.setInt(1, id);
			stmt.setString(2, enemy.getName());
			stmt.setInt(3, enemy.getPosX());
			stmt.setInt(4, enemy.getPosY());
			stmt.setInt(5, enemy.getHp());
			stmt.setInt(6, enemy.getDef());
			stmt.setInt(7, enemy.getMaxHp());
			stmt.setInt(8, enemy.getAtk());
			stmt.setInt(9, enemy.getGameId());
			stmt.setInt(10, enemy.getLevel());

			stmt.executeUpdate();
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return -1;
	}

	// buff enemy
	public void buffEnemy(Enemy enemy) throws SQLException
	{
		if (enemy.getLevel() == 7) return;

		int levelToAdd = new Random().nextInt(3 - 0) + 1;

		enemy.setLevel(enemy.getLevel() + levelToAdd);
		enemy.setDef(enemy.getDef() + (int)(1.5 * levelToAdd));
		enemy.setAtk(enemy.getAtk() + (int)(1.5 * levelToAdd));
		enemy.setMaxHp(enemy.getMaxHp() + (int)(1.5 * levelToAdd));
		enemy.setHp(enemy.getMaxHp());

		String query = "UPDATE ENEMIES SET level = " + enemy.getLevel() + 
					", def = " + enemy.getDef() + 
					", atk = " + enemy.getAtk() + 
					", maxHp = " + enemy.getMaxHp() + 
					", hp = " + enemy.getHp() +
					" WHERE id = " + enemy.getId();

		this._statement.executeUpdate(query);
	}

	// get enemies from gameid and position
	public Enemy getEnemyFromGameIdAndPos(int gameId, int posX, int posY)
	{
		Enemy res = null;
		String query = "SELECT * FROM ENEMIES WHERE gameId = " + gameId + " AND posX = " + posX + " AND posY = " + posY;

		try {
			ResultSet rs = this._statement.executeQuery(query);

			if (rs.next())
				res = getEnemyFromRs(rs);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		return res;
	}

	// get enemies from gameid
	public ArrayList<Enemy> getEnemiesFromGameId(int gameId)
	{
		ArrayList<Enemy> res = new ArrayList<Enemy>();
		String query = "SELECT * FROM ENEMIES WHERE gameId = " + gameId;

		try {
			ResultSet rs = this._statement.executeQuery(query);

			while (rs.next()) {
				res.add(getEnemyFromRs(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		return res;
	}

	// delete enemy with gameid
	public void deleteEnemiesByGameId(int gameId) throws SQLException
	{
		String query = "DELETE FROM ENEMIES WHERE gameId = " + gameId;
		this._statement.executeUpdate(query);
	}

	// delete enemy with enemyid
	public void deleteEnemiesByEnemyId(int id) throws SQLException
	{
		String query = "DELETE FROM ENEMIES WHERE id = " + id;
		this._statement.executeUpdate(query);
	}


	/**
	 * ARTIFACT
	 */
	// adds an artifact into the database
	public int addArtifact(Artifact artifact)
	{

		String query = "INSERT INTO ARTIFACTS (name, quality, type, isEquipped, ownedBy, attr, id) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement stmt = this._connection.prepareStatement(query);
			int id = _getMaxId("ARTIFACTS") + 1;

			stmt.setString(1, artifact.getName());
			stmt.setString(2, artifact.getQuality().name());			
			stmt.setString(3, artifact.getType().name());
			stmt.setBoolean(4, artifact.getIsEquipped());
			stmt.setInt(5, artifact.getOwnedBy());
			stmt.setInt(6, artifact.getAttr());
			stmt.setInt(7, id);

			stmt.executeUpdate();
			return id;
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}
		return -1;
	}

	// transform resultset object to artifact object
	private Artifact _getArtifactFromRs(ResultSet rs)
	{
		// get all the sutff
		try {
			int	id = rs.getInt("id");
			String name = rs.getString("name");
			String _quality = rs.getString("quality");
			String _type = rs.getString("type");
			int attr = rs.getInt("attr");
			int ownedBy = rs.getInt("ownedBy");
			boolean isEquipped = rs.getBoolean("isEquipped");

			// dervrive enums
			ArtifactQuality quality;
			quality = null;
			if (_quality.equals(ArtifactQuality.HARD.name()))
				quality = ArtifactQuality.HARD;
			else if (_quality.equals(ArtifactQuality.LIMP.name()))
				quality = ArtifactQuality.LIMP;
			else if (_quality.equals(ArtifactQuality.SOFT.name()))
				quality = ArtifactQuality.SOFT;
			else if (_quality.equals(ArtifactQuality.VIGIROUS.name()))
				quality = ArtifactQuality.VIGIROUS;

			ArtifactType type;
			type = null;
			if (_type.equals(ArtifactType.ARMOR.name()))
				type = ArtifactType.ARMOR;
			else if (_type.equals(ArtifactType.HELM.name()))
				type = ArtifactType.HELM;
			else if (_type.equals(ArtifactType.WEAPON.name()))
				type = ArtifactType.WEAPON;

			// create new artifact object
			Artifact artifact = new Artifact(name, quality, type, attr, isEquipped, ownedBy);
			artifact.setId(id);
			return artifact;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// get all artifacts owned by heroid
	public ArrayList<Artifact> getAllArtifactsByHeroId(int heroId)
	{
		ArrayList<Artifact> res = new ArrayList<Artifact>();
		String query = "SELECT * FROM ARTIFACTS WHERE ownedBy = " + heroId;

		try {
			ResultSet rs = this._statement.executeQuery(query);

			while (rs.next()) {
				Artifact artifact = _getArtifactFromRs(rs);
				// append to res
				res.add(artifact);
			}
		} catch (Exception e) {
			System.out.print("Error getting artifacts");
			System.out.println(e);
			System.exit(1);
		}

		return res;
	}

	// get alll artifacts owned by heroId and type
	public ArrayList<Artifact> getAllArtifactsByHeroIdType(int heroId, ArtifactType type)
	{
		ArrayList<Artifact> res = new ArrayList<Artifact>();
		String query = "SELECT * FROM ARTIFACTS WHERE ownedBy = " + heroId + " AND type = \"" + type.name() + "\"";

		try {
			ResultSet rs = this._statement.executeQuery(query);

			while (rs.next()) {
				Artifact artifact = _getArtifactFromRs(rs);
				// append to res
				res.add(artifact);
			}
		} catch (Exception e) {
			System.out.println("Error getting artifacts");
			System.out.println(e);
			System.exit(1);
		}

		return res;
	}

	private void _changeArtifactAttribsFromHero(Artifact artifact, Hero hero, boolean isAdd)
	{
		String variable = "";
		int attr = artifact.getAttr() * (isAdd ? 1 : -1);

		// weapon decreaase attack
		if (artifact.getType() == ArtifactType.WEAPON)
			variable = "atk";

		// armor decrease def
		if (artifact.getType() == ArtifactType.ARMOR)
			variable = "def";

		// helm decrease max hp
		if (artifact.getType() == ArtifactType.HELM)
			variable = "maxHp";

		String query = "UPDATE HEROES SET " + variable + " = " + variable + " + " + attr + " WHERE id = " + hero.getId();
		try {
			this._statement.executeUpdate(query);
			if (variable.equals("atk"))
				hero.setAtk(hero.getAtk() + attr);
			if (variable.equals("def"))
				hero.setDef(hero.getDef() + attr);
			if (variable.equals("maxHp"))
				hero.setMaxHp(hero.getMaxHp() + attr);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	// unequips artifact on hero
	public void unequipArtifactOnHero(int artifactId, Hero hero) throws Exception {
		// check if artifact is owned by heroid and is equipped
		String checkQuery = "SELECT * FROM ARTIFACTS WHERE id = " + artifactId  +
							" AND ownedBy = " + hero.getId() + 
							" AND isEquipped = true";

		String updateArtifactQuery = "UPDATE ARTIFACTS SET isEquipped = false "  +
									" WHERE id = " + artifactId;
		

		try {
			ResultSet checkRs = this._statement.executeQuery(checkQuery);
			if (!checkRs.next())
				throw new Exception("Artifact not found");

			// get artifact object
			Artifact artifact = _getArtifactFromRs(checkRs);

			// modify artifact to be unequipped by user
			this._statement.executeUpdate(updateArtifactQuery);

			// undo artifact upgrades to user
			_changeArtifactAttribsFromHero(artifact, hero, false);

		} catch (Exception e) {
			throw new Exception(e);
		}
		
	}

	public void equipArtifactOnHero(int artifactId, Hero hero) throws Exception
	{
		String query = "SELECT * FROM ARTIFACTS WHERE id = " + artifactId ;
		String updateArtifactQuery = "UPDATE ARTIFACTS SET isEquipped = true "  +
									" WHERE id = " + artifactId;

		try {
			// get artifact object
			ResultSet rs = this._statement.executeQuery(query);

			if (!rs.next())
				throw new Exception("Artifact not found");
		
			Artifact artifact = _getArtifactFromRs(rs);

			// check that artifact is owned by hero and not equipped
			if (artifact.getIsEquipped() || (artifact.getOwnedBy() != hero.getId()))
				return ;

			// if hero already has artifact of same type equipped, unequip said artifact
			String artifactEquippedQuery = "SELECT * FROM ARTIFACTS WHERE ownedBy = " + hero.getId() + " AND isEquipped = true AND type = \"" + artifact.getType().name() + "\""; 
			ResultSet rsEquipped = this._statement.executeQuery(artifactEquippedQuery);
			if (rsEquipped.next())
			{
				Artifact confirm = _getArtifactFromRs(rsEquipped);
				System.out.println("Unequipping " + confirm.toString());
				unequipArtifactOnHero(confirm.getId(), hero);
			}

			// modify artifact to be equipped by user
			this._statement.executeUpdate(updateArtifactQuery);

			// add artifact upgrades to user
			_changeArtifactAttribsFromHero(artifact, hero, true);

		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	// Init conenction and connect to db
	public Model()
	{
		try {
			Class.forName("org.sqlite.JDBC");
			this._connection = DriverManager.getConnection("jdbc:sqlite:swingy.db");
			this._statement = this._connection.createStatement();
			this._initAllTables();
		} catch (Exception e) {
			System.out.println("Cant connect to db: ");
			System.out.println(e);
			System.exit(1);
		}
	}


	protected void finalize()
	{
		try {
			_connection.close();
			_statement.close();
			} catch (Exception e) {
			System.out.println("Cant close db: ");
			System.out.println(e);
			System.exit(1);
		}
	}
}
