package swingy.schema;

import java.util.Map;

import swingy.interfaces.Command;

public abstract class Schema {
	public Map<String, Command>fn_map;

	public abstract String toString();
	public abstract String getName();

	public Schema()
	{
	}
}
