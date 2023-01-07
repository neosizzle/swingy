package swingy.model;

import java.sql.Statement;

import swingy.schema.Hero;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Model {
	final private int STR_LIMIT = 50;

	private Connection _connection;
	private Statement _statement;
	
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

	// Adds hero to database (returns id of new hero), -1 on err
	public int addHero(Hero hero)
	{

		String query = "INSERT INTO HEROES (name, class, level, exp, atk, def, hp, maxExp, maxhp, id) VALUES (?, ?,?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement stmt = this._connection.prepareStatement(query);
			int id = _getMaxId("HEROES") + 1;

			// TODO : Validate and sanitize data
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
