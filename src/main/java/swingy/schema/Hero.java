package swingy.schema;

import swingy.enums.ClassName;

public class Hero {
	private String name;
	private ClassName className;
	private int	level;
	private int exp;
	private int maxExp;
	private int atk;
	private int def;
	private int hp;
	private int maxHp;
	private int id;

	/** Getter and setter  */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ClassName getClassName() {
		return className;
	}
	
	public void setClassName(ClassName className) {
		this.className = className;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
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

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getMaxExp() {
		return maxExp;
	}

	public void setMaxExp(int maxExp) {
		this.maxExp = maxExp;
	}

	public int getHp() {
		return hp;
	}
	public int getMaxHp() {
		return maxHp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**constructor */
	public Hero(String name, ClassName className, int level, int exp, int maxExp, int atk, int def, int hp, int maxHp)
	{
		this.id = -1;
		this.name = name;
		this.className = className;
		this.level = level;
		this.exp = exp;
		this.maxExp = maxExp;
		this.atk = atk;
		this.def = def;
		this.hp = hp;
		this.maxExp = maxExp;
	}

	public Hero(String name, ClassName className)
	{
		this.id = -1;
		this.name = name;
		this.className = className;
		this.exp = 0;
		this.level = 0;
		// todo, calculate maxexp

		if (className == ClassName.JIMIN)
		{
			this.atk = 5;
			this.def = 5;
			this.hp = 10;
			this.maxHp = 10;
		}
		if (className == ClassName.JUNGKOOK)
		{
			this.atk = 8;
			this.def = 2;
			this.hp = 9;
			this.maxHp = 9;
		}
		if (className == ClassName.JHOPE)
		{
			this.atk = 3;
			this.def = 4;
			this.hp = 15;
			this.maxHp = 15;
		}
		if (className == ClassName.KIM_JUNG_UN)
		{
			this.atk = 10;
			this.def = 5;
			this.hp = 3;
			this.maxHp = 3;
		}
		if (className == ClassName.TAEYUNG)
		{
			this.atk = 1;
			this.def = 10;
			this.hp = 20;
			this.maxHp = 20;
		}
		if (className == ClassName.VI)
		{
			this.atk = 2;
			this.def = 15;
			this.hp = 9;
			this.maxHp = 9;
		}
	}
}
