package swingy.view.gui;

import java.util.ArrayList;
import java.util.Map;

import swingy.interfaces.Command;
import swingy.schema.Schema;

public class TableUtils {
	// function to convert array list of heroes to table columns for swing
	public Object[][] ArrayListToObjRows(ArrayList<? extends Schema> objects, String[] columms) {

		// initialize rows in array form
		Object [][] rows = new Object[objects.size()][columms.length];

		// loop through each object
		for (int rowsIdx = 0; rowsIdx < objects.size(); rowsIdx++) {
			// create row
			Object[] row = new Object[columms.length];
			Map<String, Command> fn_map = objects.get(rowsIdx).fn_map;

			// loop through each column of object
			for (int colIdx = 0; colIdx < columms.length; colIdx++) {
				row[colIdx] = fn_map.get(columms[colIdx]).runCommand();
			}
			rows[rowsIdx] = row;
		}
		return rows;
	}
}
