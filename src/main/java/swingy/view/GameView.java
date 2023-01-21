package swingy.view;

import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.JTextComponent;
import javax.validation.ConstraintViolationException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import swingy.controller.GameController;
import swingy.enums.ClassName;
import swingy.schema.Hero;
import swingy.schema.Schema;
import swingy.view.charSelect.CharSelectATM;

// TODO FIX SC CLOSE
public class GameView {

	private boolean _isGui = false;
	private GameController _gameController;
	private Hero _currHero = null;
	private TableUtils _tableUtils = new TableUtils();

	public void charSelect()
	{
		// create frame for charselect
		final JFrame f_charSelect= new JFrame();
		final ArrayList<Hero> heroes = _gameController.getHeroesList();
		boolean isProcessing;

		isProcessing = true;
		while (isProcessing) {
			// console mode
			if (!_isGui)
			{
				while (_currHero == null) {
					// tell view to enable prompt to select or create
					Scanner sc= new Scanner(System.in);
					System.out.print("select/create >");  
					String str= sc.nextLine();
					
					//reads string 
					if (str.equals("select"))
					{
						Hero selected = null;
						String []columns = {"getId"};

						Object[][] heroes_arr  = _tableUtils.ArrayListToObjRows(heroes , columns);

						for (Object[] row : heroes_arr) {
							for (Object col : row) {
								System.out.println(col);
							}
						}


						while (selected == null) {

							// get input from view 
							System.out.print(">");  
							str = sc.nextLine();       //reads string
							int id = Integer.parseInt(str);
							selected = _gameController.handleSelect(id);
							if (selected == null)
								System.out.println("Invalid");
							else
							{
								isProcessing = false;
								_currHero = selected;
							}
						}
					}
					else if (str.equals("create"))
					{
						Hero selected = null;
						while (selected == null) {

							// tell view to input name and class
							System.out.print("name >");  
							String name_str= sc.nextLine(); 

							System.out.print("class (JIMIN/ JUNGKOOK/ VI/ JHOPE/ TAEYUNG/ KIM_JUNG_UN) >");  
							String class_str= sc.nextLine();

							try {
								selected = _gameController.handleCreate(name_str, class_str);
							} catch (Exception e) {
								// sc.close();
								System.err.println(e.getMessage());
							}
							if (selected == null)
								System.err.println("Invalid hero");
							else
							{
								// sc.close();
								isProcessing = false;
								_currHero = selected;
							}
						}
					}
					else if (str.equals("switch"))
					{
						// sc.close();
						this._isGui = true;
						break;
					}
				}
			}
			// gui mode
			else
			{
				// create jframe table
				final String[] colNames = {"id", "Name"};
				final String[] colFunctions = {"getId", "getName"};
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
								_currHero = heroes.get(selectedRowNum);
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
									Hero created = _gameController.handleCreate(nameInput.getText(), classList.getSelectedItem().toString());
									if (created == null)
										throw new Exception("Invalid cretion");
									_currHero = created;
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
								_isGui = false;
								f_charSelect.dispose();
							}  
						});
				f_charSelect.add(switchButton);
	
				f_charSelect.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				f_charSelect.setSize(1360,768);  
				f_charSelect.setLayout(null);//using no layout managers  
				f_charSelect.setVisible(true);//making the frame visible

				// wait for hero to get selected (thread safe??)
				while (_currHero == null) {
					try {
						if (_isGui == true)
							Thread.sleep(10);
						else break;
					} catch (Exception e) {
					}
				}
				if (_currHero != null) 
					isProcessing = false;
			}
		}
		
		// destroy jframe
		f_charSelect.dispose();
	}

	public void start()
	{
		this.charSelect();
		System.out.print("selected hero: " + _currHero.getId() + ", " + _currHero.getName());

	}

	public GameView(GameController controller, String mode)
	{
		this._gameController = controller;
		this._isGui = mode.equals("gui");

		// initialize window stuff here?
	}
}
