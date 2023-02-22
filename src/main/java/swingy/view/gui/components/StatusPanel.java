package swingy.view.gui.components;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import swingy.schema.Hero;
import swingy.view.GameState;

public class StatusPanel {
	private JFrame _window;
	private GameState _gamestateref;
	private JPanel _contentPane;
	private final int PANE_WIDTH = 100;
	private final int PANE_HEIGHT = 800;

	private JLabel _nameLabel;
	private JLabel _classLabel;
	private JLabel _levelLabel;
	private JLabel _expLabel;
	private JLabel _atkLabel;
	private JLabel _defLabel;
	private JLabel _hpLabel;
	private JLabel _maxHpLabel;
	private JLabel _maxExpLabel;

	public void destroy()
	{
		_window.remove(_contentPane);
	}

	public void create()
	{

		final int ITEM_HEIGHT = 60;
		final int ITEM_WIDTH = 100;

		// setup content pane
		_contentPane.setBounds(0, 0, PANE_WIDTH, PANE_HEIGHT);
		_contentPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		_contentPane.setLayout(null);

		Hero hero = _gamestateref.getCurrHero();

		// create components here...
		final JLabel nameLabel = new JLabel(String.format("<html>Name:<br/>%s</html>", hero.getName()));
		nameLabel.setBounds(0, ITEM_HEIGHT, ITEM_WIDTH, ITEM_HEIGHT);
		this._nameLabel = nameLabel;

		final JLabel classLabel = new JLabel(String.format("<html>Class:<br/>%s</html>", hero.getClassName().name()));
		classLabel.setBounds(0, ITEM_HEIGHT * 2, ITEM_WIDTH, ITEM_HEIGHT);
		this._classLabel = classLabel;

		final JLabel levelLabel = new JLabel(String.format("<html>Level:<br/>%d</html>", hero.getLevel()));
		levelLabel.setBounds(0, ITEM_HEIGHT * 3, ITEM_WIDTH, ITEM_HEIGHT);
		this._levelLabel = levelLabel;

		final JLabel expLabel = new JLabel(String.format("<html>Exp:<br/>%d</html>", hero.getExp()));
		expLabel.setBounds(0, ITEM_HEIGHT * 4, ITEM_WIDTH, ITEM_HEIGHT);
		this._expLabel = expLabel;

		final JLabel atkLabel = new JLabel(String.format("<html>Atk:<br/>%d</html>", hero.getAtk()));
		atkLabel.setBounds(0, ITEM_HEIGHT * 5, ITEM_WIDTH, ITEM_HEIGHT);
		this._atkLabel = atkLabel;

		final JLabel defLabel = new JLabel(String.format("<html>Def:<br/>%d</html>", hero.getDef()));
		defLabel.setBounds(0, ITEM_HEIGHT * 6, ITEM_WIDTH, ITEM_HEIGHT);
		this._defLabel = defLabel;

		final JLabel hpLabel = new JLabel(String.format("<html>Hp:<br/>%d</html>", hero.getHp()));
		hpLabel.setBounds(0, ITEM_HEIGHT * 7, ITEM_WIDTH, ITEM_HEIGHT);
		this._hpLabel = hpLabel;

		final JLabel maxHpLabel = new JLabel(String.format("<html>MaxHp:<br/>%d</html>", hero.getMaxHp()));
		maxHpLabel.setBounds(0, ITEM_HEIGHT * 8, ITEM_WIDTH, ITEM_HEIGHT);
		this._maxHpLabel = maxHpLabel;
			
		final JLabel maxExpLabel = new JLabel(String.format("<html>MaxExp:<br/>%d</html>", hero.getMaxExp()));
		maxExpLabel.setBounds(0, ITEM_HEIGHT * 9, ITEM_WIDTH, ITEM_HEIGHT);
		this._maxExpLabel = maxExpLabel;

		_contentPane.add(classLabel);
		_contentPane.add(nameLabel);
		_contentPane.add(expLabel);
		_contentPane.add(maxExpLabel);
		_contentPane.add(atkLabel);
		_contentPane.add(defLabel);
		_contentPane.add(hpLabel);
		_contentPane.add(maxHpLabel);
		_contentPane.add(levelLabel);

		_window.add(_contentPane);
	}

	public void update(GameState gamestate)
	{
		this._gamestateref = gamestate;
		
		Hero hero = gamestate.getCurrHero();

		this._nameLabel.setText(String.format("<html>Name:<br/>%s</html>", hero.getName()));
		this._classLabel.setText(String.format("<html>Class:<br/>%s</html>", hero.getClassName().name()));
		this._levelLabel.setText(String.format("<html>Level:<br/>%d</html>", hero.getLevel()));
		this._expLabel.setText(String.format("<html>Exp:<br/>%d</html>", hero.getExp()));
		this._atkLabel.setText(String.format("<html>Atk:<br/>%d</html>", hero.getAtk()));
		this._defLabel.setText(String.format("<html>Def:<br/>%d</html>", hero.getDef()));
		this._hpLabel.setText(String.format("<html>Hp:<br/>%d</html>", hero.getHp()));
		this._maxHpLabel.setText(String.format("<html>MaxHp:<br/>%d</html>", hero.getMaxHp()));
		this._maxExpLabel.setText(String.format("<html>MaxExp:<br/>%d</html>", hero.getMaxExp()));

	}

	public StatusPanel(JFrame window, GameState gamestate)
	{
		this._window = window;
		this._gamestateref = gamestate;
		this._contentPane = new JPanel();
	}
}
