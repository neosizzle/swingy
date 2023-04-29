package swingy.view.gui.components;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;	

import swingy.controller.GameController;
import swingy.enums.ArtifactType;
import swingy.schema.Artifact;
import swingy.schema.Hero;
import swingy.view.GameState;

public class ArtifactsPanel {
	private JFrame _window;
	private GameState _gamestateref;
	private GameController _gamecontrollerref;
	private JPanel _contentPane;
	private StatusPanel _statPanel;
	private MessagePanel _msgPanel;
	private JComboBox<String> _helmCombo;
	private JComboBox<String> _weaponCombo;
	private JComboBox<String> _armourCombo;
	private final int PANE_WIDTH = 440;
	private final int PANE_HEIGHT = 800;

	private void _performChanges()
	{
		Hero hero = _gamestateref.getCurrHero();
		
		try {
			
			// helm
			String helmArtifactStr = _helmCombo.getSelectedItem().toString();
			if (helmArtifactStr.equals("- none -"))
			{
				// find equipped helm
				ArrayList<Artifact> helms = _gamecontrollerref.getArtifactsByHeroAndType(hero.getId(), ArtifactType.HELM);
				Artifact quippedHelm = null;
				for (Artifact helm : helms) {
					if (helm.isEquipped)
					{
						quippedHelm = helm;
						break;
					}
				}

				if (quippedHelm != null)
					_gamecontrollerref.unequipArtifactOnHero(quippedHelm.getId(), hero);
			}
			else
			{
				String helmIdStr = helmArtifactStr.split(" ")[0];
				_gamecontrollerref.equipArtifactOnHero(Integer.parseInt(helmIdStr), hero);
			}

			// weapon
			hero = _gamecontrollerref.getHeroById(hero.getId());
			String weaponArtifactStr = _weaponCombo.getSelectedItem().toString();
			hero = _gamecontrollerref.getHeroById(hero.getId());
			
			if (weaponArtifactStr.equals("- none -"))
			{
				// find equipped weapon
				ArrayList<Artifact> weapons = _gamecontrollerref.getArtifactsByHeroAndType(hero.getId(), ArtifactType.WEAPON);
				Artifact quippedWeapons = null;
				for (Artifact weapon : weapons) {
					if (weapon.isEquipped)
					{
						quippedWeapons = weapon;
						break;
					}
				}

				if (quippedWeapons != null)
					_gamecontrollerref.unequipArtifactOnHero(quippedWeapons.getId(), hero);

			}
			else
			{
				String weaponIdStr = weaponArtifactStr.split(" ")[0];
				_gamecontrollerref.equipArtifactOnHero(Integer.parseInt(weaponIdStr), hero);
			}

			// armour
			hero = _gamecontrollerref.getHeroById(hero.getId());
			String armourArtifactStr = _armourCombo.getSelectedItem().toString();
			if (armourArtifactStr.equals("- none -"))
			{
				// find equipped armour
				ArrayList<Artifact> armour = _gamecontrollerref.getArtifactsByHeroAndType(hero.getId(), ArtifactType.ARMOR);
				Artifact quippedarmour = null;
				for (Artifact weapon : armour) {
					if (weapon.isEquipped)
					{
						quippedarmour = weapon;
						break;
					}
				}

				if (quippedarmour != null)
					_gamecontrollerref.unequipArtifactOnHero(quippedarmour.getId(), hero);
			}
			else
			{
				String armourIdStr = armourArtifactStr.split(" ")[0];
				_gamecontrollerref.equipArtifactOnHero(Integer.parseInt(armourIdStr), hero);
			}

			// get hero and update gamestate
			Hero newHero = _gamecontrollerref.getHeroById(hero.getId());
			_gamestateref.setCurrHero(newHero);

			// update hero stat
			_statPanel.update(_gamestateref);

			// notify user
			_msgPanel.appendText("Changes saved \n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String _findEquippedString(ArrayList<Artifact> artifacts)
	{
		for (int index = 0; index < artifacts.size(); index++) {
			if (artifacts.get(index).isEquipped) return artifacts.get(index).toString();
		}
		return "- none -";
	}

	private String[] _artifactListToArr(ArrayList<Artifact> list)
	{
		String[] res = new String[list.size() + 1];

		for (int index = 0; index < list.size(); index++) {
			res[index] = list.get(index).toString();
		}

		res[list.size()] = "- none -";
		return res;
	}

	public void update()
	{
		Hero hero = _gamestateref.getCurrHero();

		// get helm artifacts
		ArrayList<Artifact> helmArtifcatsList = _gamecontrollerref.
		getArtifactsByHeroAndType(hero.getId(), ArtifactType.HELM);
		String[] helmArtifacts = _artifactListToArr(helmArtifcatsList);
		
		//create new helm model
		DefaultComboBoxModel<String> helmModel =
		new DefaultComboBoxModel<String>(helmArtifacts);

		// set new model 
		_helmCombo.setModel(helmModel);

		// set selected
		_helmCombo.setSelectedItem(_findEquippedString(helmArtifcatsList));


		// get armour artifacts
		ArrayList<Artifact> armourArtifcatsList = _gamecontrollerref.
		getArtifactsByHeroAndType(hero.getId(), ArtifactType.ARMOR);
		String[] armourArtifacts = _artifactListToArr(armourArtifcatsList);
		
		//create new helm model
		DefaultComboBoxModel<String> armourModel =
		new DefaultComboBoxModel<String>(armourArtifacts);

		// set new model 
		_armourCombo.setModel(armourModel);

		// set selected
		_armourCombo.setSelectedItem(_findEquippedString(armourArtifcatsList));

		// get weapon artifacts
		ArrayList<Artifact> weaponArtifcatsList = _gamecontrollerref.
		getArtifactsByHeroAndType(hero.getId(), ArtifactType.WEAPON);
		String[] weaponArtifacts = _artifactListToArr(weaponArtifcatsList);
		
		//create new helm model
		DefaultComboBoxModel<String> weaponModel =
		new DefaultComboBoxModel<String>(weaponArtifacts);

		// set new model 
		_weaponCombo.setModel(weaponModel);

		// set selected
		_weaponCombo.setSelectedItem(_findEquippedString(weaponArtifcatsList));
	}

	public void destroy()
	{
		_window.remove(_contentPane);
	}

	public void create()
	{
		// setup content pane
		_contentPane.setBounds(1500, 0, PANE_WIDTH, PANE_HEIGHT);
		_contentPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		_contentPane.setLayout(null);

		JLabel header = new JLabel("Artifacts");
		header.setFont(new Font("Serif", Font.BOLD, 14));
		header.setBounds(0, 0, 100, 50);

		Hero hero = this._gamestateref.getCurrHero();

		// helm 
		JLabel helmLabel = new JLabel("Helmet");
		helmLabel.setBounds(0, 50, 100, 25);

		ArrayList<Artifact> helmArtifcatsList = _gamecontrollerref.
		getArtifactsByHeroAndType(hero.getId(), ArtifactType.HELM);
		String[] helmArtifacts = _artifactListToArr(helmArtifcatsList);

		JComboBox<String> helmCombo = new JComboBox<String>(helmArtifacts);
		helmCombo.setBounds(0, 75, PANE_WIDTH, 50);

		// set default selected
		helmCombo.setSelectedItem(_findEquippedString(helmArtifcatsList));
		helmCombo.setFocusable(false);
		this._helmCombo = helmCombo;

		// armour
		JLabel armorLabel = new JLabel("Armour");
		armorLabel.setBounds(0, 125, 100, 25);

		ArrayList<Artifact> armourArtifactsList = _gamecontrollerref.
		getArtifactsByHeroAndType(hero.getId(), ArtifactType.ARMOR);
		String[] armorArtifacts = _artifactListToArr(armourArtifactsList);

		JComboBox<String> armorCombo = new JComboBox<String>(armorArtifacts);
		armorCombo.setBounds(0, 150, PANE_WIDTH, 50);

		// set default selected
		armorCombo.setSelectedItem(_findEquippedString(armourArtifactsList));
		armorCombo.setFocusable(false);
		this._armourCombo = armorCombo;

		// weapn
		JLabel weaponLabel = new JLabel("Weapon");
		weaponLabel.setBounds(0, 200, 100, 25);

		ArrayList<Artifact> weaponArtifactList = _gamecontrollerref.
		getArtifactsByHeroAndType(hero.getId(), ArtifactType.WEAPON);
		String[] weaponArtifacts = _artifactListToArr(weaponArtifactList);

		JComboBox<String> weaponCombo = new JComboBox<String>(weaponArtifacts);
		weaponCombo.setBounds(0, 225, PANE_WIDTH, 50);

		// set default selected
		weaponCombo.setSelectedItem(_findEquippedString(weaponArtifactList));
		weaponCombo.setFocusable(false);

		this._weaponCombo = weaponCombo;

		// save changes button
		JButton saveBtn = new JButton("Save changes");
		saveBtn.setBounds(0, 450, PANE_WIDTH, 50);
		saveBtn.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){ 
						_performChanges();
					}  
				});
		saveBtn.setFocusable(false);

		_contentPane.add(helmLabel);
		_contentPane.add(helmCombo);
		_contentPane.add(armorLabel);
		_contentPane.add(armorCombo);
		_contentPane.add(weaponLabel);
		_contentPane.add(weaponCombo);
		_contentPane.add(header);
		_contentPane.add(saveBtn);

		_window.add(_contentPane);
	}

	public ArtifactsPanel(JFrame window, GameState gameState, GameController gameController, StatusPanel statpanel, MessagePanel messagePanel)
	{
		this._window = window;
		this._gamestateref = gameState;
		this._contentPane = new JPanel();
		this._gamecontrollerref = gameController;
		this._statPanel = statpanel;
		this._msgPanel = messagePanel;
	}
}
