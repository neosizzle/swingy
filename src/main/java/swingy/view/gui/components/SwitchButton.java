package swingy.view.gui.components;

import java.awt.Color;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import swingy.interfaces.Command;
import swingy.schema.Hero;
import swingy.view.GameState;

public class SwitchButton {
	private JFrame _window;
	private JButton _button;
	private Command _isGuiSwitch;

	public void destroy()
	{
		_window.remove(_button);
	}

	public void create()
	{
		JButton switchButton=new JButton("Switch to console");
		switchButton.setBounds(550, 800, 350, 40);
		switchButton.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
						_isGuiSwitch.runCommand();
						_window.dispose();
					}  
				});
		_window.add(switchButton);
	}

	public void update(GameState gamestate)
	{
		
	}

	public SwitchButton(JFrame window, Command isGuiSwitch)
	{
		this._window = window;
		this._isGuiSwitch = isGuiSwitch;
	}
}
