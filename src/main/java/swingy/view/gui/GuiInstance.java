package swingy.view.gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import swingy.controller.GameController;
import swingy.interfaces.Command;
import swingy.schema.Hero;
import swingy.view.GameState;
import swingy.view.gui.charSelect.CharSelectATM;

public class GuiInstance {
	private final TableUtils _tableUtils = new TableUtils();

	private Command<Number> _isGuiSwitch;
	private GameController _gameControllerRef;
	private GameState _gamestateRef;
	private JFrame _f_charSelect;


	// run character select window
	public void charSelect(JFrame f_charSelect)
	{
		// create jframe table
		this._f_charSelect = f_charSelect;
		final ArrayList<Hero> heroes = _gameControllerRef.getHeroesList();
		final String[] colNames = {"id", "Name", "Level"};
		final String[] colFunctions = {"getId", "getName", "getLevel"};
		final Object[][] heroes_arr  = _tableUtils.ArrayListToObjRows(heroes , colFunctions);
		final CharSelectATM atm = new CharSelectATM(colNames, heroes_arr);
		final JTable table = new JTable(atm);
		final JScrollPane scrollPane = new JScrollPane(table);

		scrollPane.setBounds(50, 50, 600, 500);
		table.setFillsViewportHeight(true);
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		
		// create select button
		final JButton selectButton=new JButton("Select");
		selectButton.setBounds(50, 550, 100, 40); 
		
		final JLabel selectErrMsg = new JLabel("");
		selectErrMsg.setBounds(50, 605, 600, 50);
		selectErrMsg.setVisible(false);

		selectButton.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
						final int selectedRowNum = table.getSelectedRow();

						if (selectedRowNum == - 1)
						{
							selectErrMsg.setVisible(true);
							selectErrMsg.setText("Please select a hero.");
							return;
						}
						_gamestateRef.setCurrHero(heroes.get(selectedRowNum));
						return ;
					}  
				});

		f_charSelect.add(selectErrMsg);
		f_charSelect.add(selectButton);
		f_charSelect.add(scrollPane); 

		// create hero creation form
		final JLabel nameLabel = new JLabel("Name:");
		nameLabel.setBounds(700, 50, 50, 50);

		final JTextField nameInput = new JTextField("sexmaster1234");
		nameInput.setBounds(750, 50, 300, 50);

		f_charSelect.add(nameInput);
		f_charSelect.add(nameLabel);

		final JLabel classLabel = new JLabel("Class:");
		classLabel.setBounds(700, 100, 50, 50);
		final String[] classes = {
			"JHOPE",
			"JIMIN",
			"JUNGKOOK",
			"VI",
			"TAEYUNG",
			"KIM_JUNG_UN"
		};
		final JComboBox<String> classList = new JComboBox<String>(classes);
		classList.setBounds(750, 100, 300, 50);

		f_charSelect.add(classList);
		f_charSelect.add(classLabel);

		final JButton createButton=new JButton("Create new");
		createButton.setBounds(750, 150, 150, 40);

		final JLabel createErrMsg = new JLabel("");
		createErrMsg.setBounds(750, 200, 300, 50);
		createErrMsg.setVisible(false);

		createButton.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
						try {
							Hero created = _gameControllerRef.handleCreate(nameInput.getText(), classList.getSelectedItem().toString());
							if (created == null)
								throw new Exception("Invalid cretion");
							_gamestateRef.setCurrHero(created);
						} catch (Exception err) {
							createErrMsg.setVisible(true);
							createErrMsg.setText(err.getMessage());
							return;
						}
					}  
				});

		f_charSelect.add(createErrMsg);
		f_charSelect.add(createButton);

		final JButton switchButton=new JButton("Switch to console");
		switchButton.setBounds(750, 350, 350, 40);
		switchButton.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
						_isGuiSwitch.runCommand();
						_f_charSelect.dispose();
					}  
				});
		f_charSelect.add(switchButton);

		f_charSelect.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f_charSelect.setSize(1360,768);  
		f_charSelect.setLayout(null);//using no layout managers  
		f_charSelect.setVisible(true);//making the frame visible

	}

	public GuiInstance(Command<Number> isGuiSwitch, GameController gameControllerRef, GameState gameStateRef)
	{
		this._isGuiSwitch = isGuiSwitch;
		this._gameControllerRef = gameControllerRef;
		this._gamestateRef = gameStateRef;
	}
}
