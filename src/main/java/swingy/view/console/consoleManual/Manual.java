package swingy.view.console.consoleManual;

public class Manual {
	public void printHelpCharSelect () {
		System.out.print(
						"select - select from a list of existing heroes\n" +
						"create - create a new hero on the spot\n" +
						"switch - switch to gui mode\n\n>"
					); 
	}

	public void printHelpMain() {
		final String str =
		"======COMMANDS======\n" + 
		"help - display this section.\n\n" +
		"stat - display hero stats\n" +
		"artifacts list | a l - list all artifacts\n" +
		"artifacts equip [id] | a e [id] - equips artifact [id]\n" +
		"artifacts unequip [id] | a u [id] - unequips artifact [id]\n" +
		"move N | S | E | W - move North | South | East | West\n" +
		"map - displays map\n" +
		"switch - switch to gui mode\n" + 
		"exit - exit the game\n"
		;

		System.out.println(str);
	}
}
