package swingy.view.gui.components;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import swingy.interfaces.Command;
import swingy.view.GameState;

public class SwitchButton {
	private JFrame _window;
	private JButton _button;
	private Command _isGuiSwitch;

	public void destroy()
	{
		_window.remove(_button);
	}
	
	public void disable()
	{
		_button.setEnabled(false);
	}

	public void enable()
	{
		_button.setEnabled(true);
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
		this._button = switchButton;
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
