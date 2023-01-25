package swingy.view.console;

import java.util.ArrayList;
import java.util.Map;

import swingy.interfaces.Command;
import swingy.schema.Schema;

public class ConsoleUtils {
	// prints a list of schemas in a table with the columns supplied
	public void printSchemaTable(ArrayList<? extends Schema> list, String[] columns)
	{
		// print first row
		for (String column : columns) {
			System.out.print(column.replace("get", "") + "\t\t");
		}
		System.out.println();

		for (int rowsIdx = 0; rowsIdx < list.size(); rowsIdx++) {
			Schema item = list.get(rowsIdx);
			Map<String, Command> fn_map = item.fn_map;

			// loop through each column of object
			for (String column : columns) {
				System.out.print(fn_map.get(column).runCommand() + "\t\t");
			}
			System.out.println();
		}
	}
}
