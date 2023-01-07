package swingy.schema;

import swingy.enums.ArtifactQuality;
import swingy.enums.ArtifactType;

public class Artifact {
	private String name;
	private ArtifactQuality quality;
	private ArtifactType type;
	private int attr;
	public boolean isEquipped;
	private int		ownedBy;

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

	/**Constructor */
	public Artifact(String nameString, ArtifactQuality artifactQuality, ArtifactType artifactType, int attr, boolean isEquipped, int ownedBy)
	{
		this.name = nameString;
		this.quality = artifactQuality;
		this.type = artifactType;
		this.attr = attr;
		this.isEquipped = isEquipped;
		this.ownedBy = ownedBy;
	}
}
