package swingy.view;

import java.lang.ModuleLayer.Controller;
import java.util.ArrayList;
import java.util.Scanner;

import swingy.controller.GameController;
import swingy.schema.Hero;

public class GameView {

	boolean isGui = false;
	GameController gameController;
	Hero currHero = null;

	public void charSelect()
	{
		if (!isGui)
		{
			while (currHero == null) {
				// tell view to enable prompt to select or create
				Scanner sc= new Scanner(System.in);
				System.out.print("select/create >");  
				String str= sc.nextLine();
				
				//reads string 
				if (str.equals("select"))
				{
					ArrayList<Hero> heroes = gameController.getHeroesList();
					for (Hero hero : heroes) {
						// display all heroes in view 
						System.out.println(hero.getId());
					}
					Hero selected = null;
					while (selected == null) {

						// get input from view 
						System.out.print(">");  
						str = sc.nextLine();              //reads string
						int id = Integer.parseInt(str);
						selected = gameController.handleSelect(id);
						if (selected == null)
							System.out.println("Invalid");
						else
							currHero = selected;
					}
				}
				else if (str.equals("create"))
				{
					Hero selected = null;
					while (selected == null) {

						// tell view to input name and class
						System.out.print("name >");  
						String name_str= sc.nextLine(); 

						System.out.print("class (JIMIN/ JUNGKOOK/ VI/ JHOPE/ KIM_JUNG_UN) >");  
						String class_str= sc.nextLine();

						selected = gameController.handleCreate(name_str, class_str);
						if (selected == null)
							System.out.println("Invalid");
						else
							currHero = selected;
					}
				}
			}
			
		}

		System.out.print("selected hero: " + currHero.getId() + ", " + currHero.getName());
	}

	public void start()
	{
		this.charSelect();
	}

	public GameView(GameController controller)
	{
		this.gameController = controller;
	}
}
