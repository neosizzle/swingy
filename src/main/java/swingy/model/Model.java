package swingy.model;

import java.sql.Statement;
import java.util.ArrayList;

import swingy.enums.ArtifactQuality;
import swingy.enums.ArtifactType;
import swingy.enums.ClassName;
import swingy.schema.Artifact;
import swingy.schema.Hero;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Model {
	final private int STR_LIMIT = 50;

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
		// create heroes table
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
		" explored			VARCHAR(50) 		NOT NULL, " + 
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
			System.out.println(e);
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
			System.out.print("Error getting heroes");
			System.out.println(e);
			System.exit(1);
		}

		return res;
	}

	// removes artifact attribues from hero
	private void _removeArtifactAttribsFromHero(Artifact artifact, int heroId)
	{
		String variable = "";
		int attr = artifact.getAttr();

		// weapon decreaase attack
		if (artifact.getType() == ArtifactType.WEAPON)
			variable = "atk";

		// armor decrease def
		if (artifact.getType() == ArtifactType.ARMOR)
			variable = "def";

		// helm decrease max hp
		if (artifact.getType() == ArtifactType.HELM)
			variable = "maxHp";

		String query = "UPDATE HEROES SET " + variable + " = " + variable + " - " + attr + " WHERE id = " + artifact.getId();
		try {
			this._statement.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	// TODO test this
	// unequips artifact on hero
	public void unequipArtifactOnHero(int artifactId, int heroId) {
		// check if artifact is owned by heroid and is equipped
		String checkQuery = "SELECT * FROM ARTIFACTS WHERE id = " + artifactId  +
							" AND ownedBy = " + heroId + 
							" AND isEquipped = true";

		String updateArtifactQuery = "UPDATE ARTIFACTS SET isEquipped = false "  +
									" WHERE id = " + artifactId;
		

		try {
			ResultSet checkRs = this._statement.executeQuery(checkQuery);
			if (!checkRs.next())
			{
				System.out.println("Artifact not equipped on user");
				return ;
			}

			// get artifact object
			Artifact artifact = _getArtifactFromRs(checkRs);

			// modify artifact to be unequipped by user
			this._statement.executeUpdate(updateArtifactQuery);

			// undo artifact upgrades to user
			_removeArtifactAttribsFromHero(artifact, heroId);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
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
