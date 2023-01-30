package swingy.view.gui.components;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MessagePanel {
	private JFrame _window;
	private JPanel _contentPane;
	private JTextArea _textarea;
	private final int PANE_WIDTH = 1920;
	private final int PANE_HEIGHT = 160;
	private String _string;

	public void destroy()
	{
		_window.remove(_contentPane);
	}

	public void appendText(String str)
	{
		_textarea.append(str);
	}

	public void create()
	{
		// setup content pane
		_contentPane.setBounds(0, 840, PANE_WIDTH, PANE_HEIGHT);
		_contentPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		_contentPane.setLayout(null);


		final JTextArea textArea = new JTextArea(_string);
		textArea.setEditable(false);
		textArea.setBounds(0, 0, PANE_WIDTH, PANE_HEIGHT);
		this._textarea = textArea;
		
		final JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(0, 0, PANE_WIDTH, PANE_HEIGHT);

		_contentPane.add(scrollPane);

		_window.add(_contentPane);
	}

	public MessagePanel(JFrame window)
	{
		this._window = window;
		this._contentPane = new JPanel();
	}
}
