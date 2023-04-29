package swingy.view.gui.components;

import java.awt.Color;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.io.File;

import swingy.view.GameState;
import swingy.view.Map;

public class MapPanel {
	private JFrame _window;
	private GameState _gamestateRef;
	private JPanel _contentPane;
	private final int PANE_WIDTH = 1200;
	private final int PANE_HEIGHT = 800;
	private Image _enemyPic;
	private Image _heroPic;
	private Image _exploredPic;
	private Image _wallPic;
	private Image _unexploredPic;
	private JLabel[][] _grid = new JLabel[40][40];
	private Map _map;

	private ImageIcon getIconFor(char entity)
	{
		if (entity == '=')
			return (new ImageIcon(_wallPic));
		if (entity == '#')
			return (new ImageIcon(_unexploredPic));
		if (entity == '-')
			return (new ImageIcon(_exploredPic));
		if (entity == 'E')
			return (new ImageIcon(_enemyPic));
		if (entity == 'P')
			return (new ImageIcon(_heroPic));
		return null;
	}

	public void destroy()
	{
		_window.remove(_contentPane);
	}

	public void create()
	{
		// setup content pane
		_contentPane.setBounds(100, 0, PANE_WIDTH, PANE_HEIGHT);
		_contentPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		_contentPane.setLayout(null);

		final int ASSET_WIDTH = 30;
		final int ASSET_HEIGHT = 20;

		Map map = _map;

		for (int row = 0; row < 40; row++) {
			for (int col = 0; col < 40; col++) {
				char entity = map.getEntityAt(row, col, false);
				JLabel picLabel;

				picLabel = new JLabel(getIconFor(entity));
				picLabel.setBounds(col * ASSET_WIDTH, row * ASSET_HEIGHT, ASSET_WIDTH, ASSET_HEIGHT);
				_grid[row][col] = picLabel;
				_contentPane.add(picLabel);
			}
		}
		_window.add(_contentPane);
	}

	public void update(GameState gameState)
	{
		this._gamestateRef = gameState;
		
		Map newMap = _gamestateRef.getMap();
		for (int row = 0; row < 40; row++) {
			for (int col = 0; col < 40; col++) {
				final char newEntity = newMap.getEntityAt(row, col, false);
				if (_map.getEntityAt(row, col, false) == newEntity)
					continue ;
				_grid[row][col].setIcon(getIconFor(newEntity));
			}
		}
		this._map = newMap;
	}

	public MapPanel(JFrame window, GameState gamestate, ArtifactsPanel artifactsPanel)
	{
		this._window = window;
		this._gamestateRef = gamestate;
		this._contentPane = new JPanel();
		this._map = gamestate.getMap();

		// load pictures
		try {
			_enemyPic = ImageIO.read(new File("src/main/java/swingy/assets/img/enemy.png")).getScaledInstance(40, 20, Image.SCALE_DEFAULT);
			_heroPic = ImageIO.read(new File("src/main/java/swingy/assets/img/hero.png")).getScaledInstance(40, 20, Image.SCALE_DEFAULT);
			_exploredPic = ImageIO.read(new File("src/main/java/swingy/assets/img/explored.png")).getScaledInstance(40, 20, Image.SCALE_DEFAULT);
			_unexploredPic = ImageIO.read(new File("src/main/java/swingy/assets/img/unexplored.png")).getScaledInstance(40, 20, Image.SCALE_DEFAULT);
			_wallPic = ImageIO.read(new File("src/main/java/swingy/assets/img/wall.png")).getScaledInstance(40, 20, Image.SCALE_DEFAULT);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
