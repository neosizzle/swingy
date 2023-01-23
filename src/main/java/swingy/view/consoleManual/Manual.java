package swingy.view.consoleManual;

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
		"artifacts list - list all artifacts\n" +
		"artifacts equip [id] - equips artifact [id]\n" +
		"map - displays map\n"+
		"help - display this section.\n" 
		;

		System.out.println(str);
	}
}
