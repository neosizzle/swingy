package swingy.view.gui.charSelect;

import javax.swing.table.AbstractTableModel;

public class CharSelectATM extends AbstractTableModel {
	private String[] colNames;
	private Object[][] rows;

	public int getRowCount()
	{
		return rows.length;
	}

	public int getColumnCount()
	{
		return colNames.length;
	}

	public Object getValueAt(int row, int column)
	{
		return rows[row][column];
	}

	public boolean isCellEditable(int row, int col)
	{
		return false;
	}

	public String getColumnName(int column)
	{
		return colNames[column];
	}

	public CharSelectATM(String[] colNames, Object[][] heroes_arr)
	{
		this.colNames = colNames;
		this.rows = heroes_arr;
	}
}
