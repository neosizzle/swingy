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
	private final int PANE_WIDTH = 200;
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

		final int ITEM_HEIGHT = 20;
		final int ITEM_WIDTH = 100;

		// setup content pane
		_contentPane.setBounds(0, 0, PANE_WIDTH, PANE_HEIGHT);
		_contentPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		_contentPane.setLayout(null);

		Hero hero = _gamestateref.getCurrHero();

		// create components here...
		final JLabel nameLabel = new JLabel("Name: " + hero.getName());
		nameLabel.setBounds(0, ITEM_HEIGHT, ITEM_WIDTH, ITEM_HEIGHT);
		this._nameLabel = nameLabel;

		final JLabel classLabel = new JLabel("Class: " + hero.getClassName().name());
		classLabel.setBounds(0, ITEM_HEIGHT * 2, ITEM_WIDTH, ITEM_HEIGHT);
		this._classLabel = classLabel;

		final JLabel levelLabel = new JLabel("Level: " + hero.getLevel());
		levelLabel.setBounds(0, ITEM_HEIGHT * 3, ITEM_WIDTH, ITEM_HEIGHT);
		this._levelLabel = levelLabel;

		final JLabel expLabel = new JLabel("Exp: " + hero.getExp());
		expLabel.setBounds(0, ITEM_HEIGHT * 4, ITEM_WIDTH, ITEM_HEIGHT);
		this._expLabel = expLabel;

		final JLabel atkLabel = new JLabel("Atk: " + hero.getAtk());
		atkLabel.setBounds(0, ITEM_HEIGHT * 5, ITEM_WIDTH, ITEM_HEIGHT);
		this._atkLabel = atkLabel;

		final JLabel defLabel = new JLabel("Def: " + hero.getDef());
		defLabel.setBounds(0, ITEM_HEIGHT * 6, ITEM_WIDTH, ITEM_HEIGHT);
		this._defLabel = defLabel;

		final JLabel hpLabel = new JLabel("Hp: " + hero.getHp());
		hpLabel.setBounds(0, ITEM_HEIGHT * 7, ITEM_WIDTH, ITEM_HEIGHT);
		this._hpLabel = hpLabel;

		final JLabel maxHpLabel = new JLabel("MaxHp: " + hero.getMaxHp());
		maxHpLabel.setBounds(0, ITEM_HEIGHT * 8, ITEM_WIDTH, ITEM_HEIGHT);
		this._maxHpLabel = maxHpLabel;
			
		final JLabel maxExpLabel = new JLabel("MaxExp: " + hero.getMaxExp());
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

		this._nameLabel.setText("Name: " + hero.getName());
		this._classLabel.setText("Class: " + hero.getClassName().name());
		this._levelLabel.setText("Level: " + hero.getLevel());
		this._expLabel.setText("Exp: " + hero.getExp());
		this._atkLabel.setText("Atk: " + hero.getAtk());
		this._defLabel.setText("Def: " + hero.getDef());
		this._hpLabel.setText("Hp: " + hero.getHp());
		this._maxHpLabel.setText("MaxHp: " + hero.getMaxHp());
		this._maxExpLabel.setText("MaxExp: " + hero.getMaxExp());

	}

	public StatusPanel(JFrame window, GameState gamestate)
	{
		this._window = window;
		this._gamestateref = gamestate;
		this._contentPane = new JPanel();
	}
}
