package swingy.schema;

public class Enemy extends Schema {
	private	int	id;
	private String name;
	private int hp;
	private int def;
	private int atk;
	private int maxHp;
	private int gameId;
	private int posX;
	private int posY;
	private int level;

	public String toString()
	{
		return "enemy " + name;
	}

	/**Getters and setters */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getAtk() {
		return atk;
	}
	public void setAtk(int atk) {
		this.atk = atk;
	}
	public int getDef() {
		return def;
	}
	public void setDef(int def) {
		this.def = def;
	}
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getMaxHp() {
		return maxHp;
	}
	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	/**Constructor */
	public Enemy(String name, int hp, int def, int atk, int maxHp, int gameId, int posX, int posY, int level)
	{
		this.id = -1;
		this.name = name;
		this.hp = hp;
		this.def = def;
		this.atk = atk;
		this.maxHp = maxHp;
		this.gameId = gameId;
		this.posX = posX;
		this.posY = posY;
		this.level = level;
	}
}
