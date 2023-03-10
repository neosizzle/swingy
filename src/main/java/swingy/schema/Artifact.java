package swingy.schema;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import swingy.enums.ArtifactQuality;
import swingy.enums.ArtifactType;
import swingy.interfaces.Command;

public class Artifact extends Schema{
	private int	id;
	private String name;
	private ArtifactQuality quality;
	private ArtifactType type;
	private int attr;
	public boolean isEquipped;
	private int		ownedBy;

	private Map<String, Command> generateFnMap()
	{
		HashMap<String, Command> res = new HashMap<String, Command>();

		res.put("getName", new Command<String>() {
			public String runCommand(){return getName();}
		});
		res.put("getQuality", new Command<ArtifactQuality>() {
			public ArtifactQuality runCommand(){return getQuality();}
		});
		res.put("getType", new Command<ArtifactType>() {
			public ArtifactType runCommand(){return getType();}
		});
		res.put("getId", new Command<Number>() {
			public Number runCommand(){return getId();}
		});
		res.put("getAttr", new Command<Number>() {
			public Number runCommand(){return getAttr();}
		});
		res.put("getOwnedBy", new Command<Number>() {
			public Number runCommand(){return getOwnedBy();}
		});
		res.put("getIsEquipped", new Command<Boolean>() {
			public Boolean runCommand(){return getIsEquipped();}
		});


		return res;
	}

	public String toString()
	{
		return this.getId() + " - " + this.quality + " " + this.name + " - " + this.type.name() + " + " + this.attr;
	}
	/** Getter and setter */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArtifactQuality getQuality() {
		return quality;
	}
	public void setQuality(ArtifactQuality quality) {
		this.quality = quality;
	}
	public ArtifactType getType() {
		return type;
	}
	public void setType(ArtifactType type) {
		this.type = type;
	}
	public int getAttr() {
		return attr;
	}
	public void setAttr(int attr) {
		this.attr = attr;
	}
	public int getOwnedBy() {
		return ownedBy;
	}
	public void setOwnedBy(int ownedBy) {
		this.ownedBy = ownedBy;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean getIsEquipped() {
		return isEquipped;
	}
	public void setIsEquipped(boolean isEquipped) {
		this.isEquipped = isEquipped;
	}

	/**Constructor */
	public Artifact( ArtifactQuality artifactQuality, ArtifactType artifactType, int attr, boolean isEquipped, int ownedBy)
	{
		this.id = -1;
		this.quality = artifactQuality;
		this.type = artifactType;
		this.attr = attr;
		this.isEquipped = isEquipped;
		this.ownedBy = ownedBy;
		this.fn_map = generateFnMap();

		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/main/java/swingy/assets/text/artifactnames.txt"));
			String line = reader.readLine();
			ArrayList<String> names = new ArrayList<String>();

			while (line != null) {
				names.add(line);
				line = reader.readLine();
			}

			reader.close();
			this.name = names.get(new Random().nextInt(names.size()));
		} catch (Exception e) {
			this.name = "some artifact";
			e.printStackTrace();
		}
	}

	public Artifact(String nameString, ArtifactQuality artifactQuality, ArtifactType artifactType, int attr, boolean isEquipped, int ownedBy)
	{
		this.id = -1;
		this.name = nameString;
		this.quality = artifactQuality;
		this.type = artifactType;
		this.attr = attr;
		this.isEquipped = isEquipped;
		this.ownedBy = ownedBy;
		this.fn_map = generateFnMap();
	}
}
