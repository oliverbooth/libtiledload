package uk.co.marshmallow_zombies.libtiledload.framework;

import java.util.ArrayList;
import java.util.List;

import uk.co.marshmallow_zombies.libtiledload.interfaces.IProperties;

/**
 * A map object.
 * 
 * @author Oliver Davenport
 */
public class MapObject implements IProperties {

	private List<Property> m_PropertiesList; // List of properties

	private String m_sName, m_sType; // Object name and type
	private int m_nX, m_nY; // Object position

	/**
	 * Initialises a new {@code MapObject} with the specified properties.
	 * 
	 * @param name
	 *            The object name.
	 * @param type
	 *            The object type.
	 * @param x
	 *            The object x-position.
	 * @param y
	 *            The object y-position.
	 */
	MapObject(String name, String type, int x, int y) {
		// Initialise variables
		this.m_sName = name;
		this.m_sType = type;
		this.m_nX = x;
		this.m_nY = y;

		// Initialise list
		this.m_PropertiesList = new ArrayList<Property>();
	}

	/**
	 * Gets the object name.
	 */
	public String getName() {
		// Return value
		return this.m_sName;
	}

	/**
	 * Gets the object type.
	 */
	public String getType() {
		// Return value
		return this.m_sType;
	}

	/**
	 * Gets the object x-position.
	 */
	public int getX() {
		// Return value
		return this.m_nX;
	}

	/**
	 * Gets the object y-position.
	 */
	public int getY() {
		// Return value
		return this.m_nY;
	}

	@Override
	public void addProperty(Property property) {
		// Add the property to the list
		this.m_PropertiesList.add(property);
	}

	@Override
	public Property[] getProperties() {
		// Clone the properties list
		List<Property> propertiesList = this.m_PropertiesList;

		// Get the size of the list
		int size = propertiesList.size();

		// Create a new array and go!
		Property[] properties = new Property[size];

		// Convert to array
		propertiesList.toArray(properties);

		// Return the array
		return properties;
	}

	@Override
	public Property getPropertyByName(String name) {
		// Iterate through the array
		for (int i = 0; i < this.m_PropertiesList.size(); i++) {
			// Does the name match?
			if (this.m_PropertiesList.get(i).getName().equals(name)) {
				// Return the property found in the array at the current index
				return this.m_PropertiesList.get(i);
			}
		}

		// No property matched
		return null;
	}

};
