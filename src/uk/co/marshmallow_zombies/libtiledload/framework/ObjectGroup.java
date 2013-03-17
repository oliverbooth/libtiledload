package uk.co.marshmallow_zombies.libtiledload.framework;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

/**
 * An object group layer.
 * 
 * @author Oliver Davenport
 */
public class ObjectGroup extends Layer {

	private List<MapObject> m_ObjectList; // List of objects

	/**
	 * Initialises a new {@code ObjectGroup}
	 * 
	 * @param name
	 *            The layer name.
	 * @param size
	 *            The layer size.
	 */
	ObjectGroup(String name, Dimension size) {
		// Call to super constructor
		super(name, size);

		// Initialise variables
		this.m_ObjectList = new ArrayList<MapObject>();
	}

	/**
	 * Adds an object to the layer.
	 * 
	 * @param mapobject
	 *            The {@code MapObject} to add.
	 */
	public void addObject(MapObject mapobject) {
		// Add the tile to the list
		this.m_ObjectList.add(mapobject);
	}

	/**
	 * Gets an array of all the {@code MapObject}s on the map.
	 */
	public MapObject[] getObjects() {
		// Clone the object list
		List<MapObject> objectList = this.m_ObjectList;

		// Get the objectList of the list
		int size = objectList.size();

		// Create a new array and go!
		MapObject[] objects = new MapObject[size];

		// Convert to array
		objectList.toArray(objects);

		// Return the array
		return objects;
	}

};
