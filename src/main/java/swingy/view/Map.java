package swingy.view;

import java.util.ArrayList;

import swingy.interfaces.Coordinate;
import swingy.schema.Enemy;
import swingy.schema.Game;

public class Map {
	private Coordinate _topLeftCorner;
	private Coordinate _topRightCorner;
	private Coordinate _btmLeftCorner;
	private Coordinate _btmRightCorner;
	private Coordinate _playerPos;
	private ArrayList<Coordinate> _enemiesPos;
	private ArrayList<Coordinate> _exploredPos;
	private ArrayList<String> _entities;
	// private ArrayList<String> _visualEntities;
	private final Coordinate MAP_BORDERS = new Coordinate(40, 40);
	private final Coordinate MAP_CENTER = new Coordinate(20, 20);

	public Coordinate getBtmLeftCorner() {
		return _btmLeftCorner;
	}

	public Coordinate getBtmRightCorner() {
		return _btmRightCorner;
	}

	public Coordinate getTopLeftCorner() {
		return _topLeftCorner;
	}

	public Coordinate getTopRightCorner() {
		return _topRightCorner;
	}

	public String toString()
	{
		String res;
		
		res = "";
		for (int row = 0; row < MAP_BORDERS.row; row++) {
			for (int col = 0; col < MAP_BORDERS.col; col++) {
				if (row == _playerPos.row && col == _playerPos.col)
				{
					res += "P";
					continue;
				}
				// if (_findCoordinateWithPositions(_enemiesPos, row, col) != null)
				// {
				// 	res += "E";
				// 	continue ;
				// }
				if (
					(row >= _topLeftCorner.row && row < _btmLeftCorner.row) &&
					(col >= _topLeftCorner.col && col < _topRightCorner.col))
				{
					if (_findCoordinateWithPositions(_exploredPos, row, col) != null)
					{
						if (_findCoordinateWithPositions(_enemiesPos, row, col) != null)
						{
							res += "E";
							continue ;
						}
						res += "-";
						continue;
					}
					res += "#";
				}
				else
					res += "=";
			}
			res += "\n";
		}
		return res;
	}

	public char getEntityAt(int row, int col, boolean showHiddenEnemies)
	{
		if (row >= MAP_BORDERS.row || col >= MAP_BORDERS.col || row < 0 || col < 0)
			return 0;
		// check for hidden enemies
		if (_findCoordinateWithPositions(_enemiesPos, row, col) != null && showHiddenEnemies)
			return 'E';
		return this._entities.get(row).charAt(col);
	}

	private ArrayList<String> _extractEntities(String str)
	{
		ArrayList<String> res = new ArrayList<String> ();
		String[] rows = str.split("\n", 0);

		for (String row : rows) {
			res.add(row);
		}
		return res;
	}

	private ArrayList<String> _extractVisualEntities(String str)
	{
		ArrayList<String> res = new ArrayList<String> ();
		String[] rows = str.split("\n", 0);

		for (String row : rows) {
			res.add(row);
		}
		return res;
	}

	private Coordinate _findCoordinateWithPositions (ArrayList<Coordinate> coords, int row, int col)
	{
		for (Coordinate coordinate : coords) {
			if (coordinate.row == row && coordinate.col == col)
				return coordinate;
		}
		return null;
	}

	private ArrayList<Coordinate> _extractEnemyPos(ArrayList<Enemy> enemies)
	{
		ArrayList<Coordinate> res = new ArrayList<Coordinate>();

		for (Enemy enemy : enemies) {
			res.add(new Coordinate(enemy.getPosY(), enemy.getPosX()));
		}

		return res;
	}

	private ArrayList<Coordinate> _extractExploredPos(String explored)
	{
		ArrayList<Coordinate> res = new ArrayList<Coordinate>();
		String[] coordStrs = explored.split(";", 0);
		
		for (String coordStr : coordStrs) {
			String trimmed = coordStr.substring(1, coordStr.length() - 1);
			String[] coordValues = trimmed.split(",", 0);

			if(coordValues.length != 2)
			{
				System.err.println("Corrupted map data: explored");
				System.exit(1);
			}

			res.add(new Coordinate(Integer.valueOf(coordValues[0]), Integer.valueOf(coordValues[1])));
		}

		return res;
	}

	public Map(Game sample, ArrayList<Enemy> enemies)
	{
		_topLeftCorner = new Coordinate(MAP_CENTER.row - sample.getWidth() / 2, MAP_CENTER.row - sample.getHeight() / 2);
		_topRightCorner = new Coordinate(MAP_CENTER.row - sample.getWidth() / 2, MAP_CENTER.row + sample.getHeight() / 2);
		_btmLeftCorner = new Coordinate(MAP_CENTER.row + sample.getWidth() / 2, MAP_CENTER.row - sample.getHeight() / 2);
		_btmRightCorner = new Coordinate(MAP_CENTER.row + sample.getWidth() / 2, MAP_CENTER.row + sample.getHeight() / 2);
		_playerPos = new Coordinate(sample.getPosRow(), sample.getPosCol());
		_enemiesPos = _extractEnemyPos(enemies);
		_exploredPos = _extractExploredPos(sample.getExplored());
		_entities = _extractEntities(this.toString());
	}
}
