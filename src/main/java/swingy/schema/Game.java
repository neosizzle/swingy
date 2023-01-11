package swingy.schema;

public class Game {
	private	int	id;
	private int heroId;
	private int width;
	private int height;
	private int posRow;
	private int posCol;
	private String explored;

	/**Getters and setters */
	public int getHeroId() {
		return heroId;
	}
	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getExplored() {
		return explored;
	}
	public void setExplored(String explored) {
		this.explored = explored;
	}
	public int getPosCol() {
		return posCol;
	}
	public void setPosRow(int posRow) {
		this.posRow = posRow;
	}
	public int getPosRow() {
		return posRow;
	}
	public void setPosCol(int posCol) {
		this.posCol = posCol;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public Game(int heroId, int width, int height, int posCol, int posRow, String explored)
	{
		this.id = -1;
		this.heroId = heroId;
		this.width = width;
		this.height = height;
		this.posCol = posCol;
		this.posRow = posRow;
		this.explored = explored;
	}
}
