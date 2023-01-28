package swingy.view.gui.components;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import swingy.controller.GameController;
import swingy.view.GameState;

public class ControlPanel {
	private JFrame _window;
	private GameState _gamestateref;
	private GameController _gamecontrollerref;
	private JPanel _contentPane;
	private final int PANE_WIDTH = 200;
	private final int PANE_HEIGHT = 800;

	public void destroy()
	{
		_window.remove(_contentPane);
	}

	public void create()
	{
		final int BTN_HEIGHT = 64;
		final int BTN_WIDTH = 64;

		// setup content pane
		_contentPane.setBounds(1400, 0, PANE_WIDTH, PANE_HEIGHT);
		_contentPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		_contentPane.setLayout(null);

		// TODO create control
	}

	public ControlPanel(JFrame window, GameState gamestate, GameController controller)
	{
		this._window = window;
		this._gamecontrollerref = controller;
		this._gamestateref = gamestate;
	}
}
