package swingy.schema;

import java.util.HashMap;
import java.util.Map;

import swingy.enums.ClassName;
import swingy.interfaces.Command;

public class Hero extends Schema{
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
	// public Map<String, Command>fn_map;

	// function mapping
	private Map<String, Command> generateFnMap()
	{
		HashMap<String, Command> res = new HashMap<String, Command>();
		
		res.put("getName", new Command<String>() {
			public String runCommand(){return getName();}
		});
		res.put("getClassName", new Command<ClassName>() {
			public ClassName runCommand(){return getClassName();}
		});
		res.put("getLevel", new Command<Integer>() {
			public Integer runCommand(){return getLevel();}
		});
		res.put("getExp", new Command<Integer>() {
			public Integer runCommand(){return getExp();}
		});
		res.put("getAtk", new Command<Integer>() {
			public Integer runCommand(){return getAtk();}
		});
		res.put("getDef", new Command<Integer>() {
			public Integer runCommand(){return getDef();}
		});
		res.put("getHp", new Command<Integer>() {
			public Integer runCommand(){return getHp();}
		});
		res.put("getMaxHp", new Command<Integer>() {
			public Integer runCommand(){return getMaxHp();}
		});
		res.put("getId", new Command<Integer>() {
			public Integer runCommand(){return getId();}
		});
		res.put("getMaxExp", new Command<Integer>() {
			public Integer runCommand(){return getMaxExp();}
		});
		
		return res;
	}

	public String toString() {
		return "Sex";
	}
	
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
		super();
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
		this.fn_map = generateFnMap();
	}

	public Hero(String name, ClassName className)
	{
		
		this.id = -1;
		this.name = name;
		this.className = className;
		this.exp = 0;
		this.level = 0;
		this.fn_map = generateFnMap();
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
