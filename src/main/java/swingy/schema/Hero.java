package swingy.schema;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import swingy.enums.ClassName;
import swingy.interfaces.Command;

public class Hero extends Schema{

	private String name;
	private ClassName className;
	
	@Min(1)
	@Max(7)
	private int	level;
	
	@Min(0)
	private int exp;

	@Min(1000)
	private int maxExp;

	@Min(0)
	private int atk;

	@Min(0)
	private int def;

	@Min(1)
	private int hp;

	@Min(1)
	private int maxHp;

	private int id;

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
		return  this.getLevel() + ". " + this.getName();
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
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getMaxHp() {
		return maxHp;
	}
	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
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
		this.maxHp = maxHp;
		this.fn_map = generateFnMap();
	}

	public Hero(String name, ClassName className)
	{
		
		this.id = -1;
		this.name = name;
		this.className = className;
		this.exp = 0;
		this.level = 1;
		this.fn_map = generateFnMap();
		this.maxExp = (int) (this.level * 1000 + Math.pow(this.level - 1, 2) * 450);

		if (className == ClassName.JIMIN)
		{
			this.atk = 5;
			this.def = 5;
			this.hp = 100;
			this.maxHp = 100;
		}
		if (className == ClassName.JUNGKOOK)
		{
			this.atk = 8;
			this.def = 2;
			this.hp = 90;
			this.maxHp = 90;
		}
		if (className == ClassName.JHOPE)
		{
			this.atk = 3;
			this.def = 4;
			this.hp = 150;
			this.maxHp = 150;
		}
		if (className == ClassName.KIM_JUNG_UN)
		{
			this.atk = 10;
			this.def = 5;
			this.hp = 30;
			this.maxHp = 30;
		}
		if (className == ClassName.TAEYUNG)
		{
			this.atk = 1;
			this.def = 10;
			this.hp = 200;
			this.maxHp = 200;
		}
		if (className == ClassName.VI)
		{
			this.atk = 2;
			this.def = 15;
			this.hp = 90;
			this.maxHp = 90;
		}
	}
}
