package uk.co.marshmallow_zombies.libtiledload.framework;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import uk.co.marshmallow_zombies.libtiledload.interfaces.INameable;
import uk.co.marshmallow_zombies.libtiledload.interfaces.IProperties;
import uk.co.marshmallow_zombies.libtiledload.interfaces.ISizable;

/**
 * A superclass which {@code TileLayer} and {@code ObjectGroup} can extend.
 * 
 * @author Oliver Davenport
 * 
 */
public class Layer implements INameable, IProperties, ISizable {

	private List<Property> m_PropertiesList; // List of properties

	private Dimension m_Size; // Layer size
	private String m_sName; // Layer name

	/**
	 * Initialises a new {@code Layer}.
	 * 
	 * @param name
	 *            The layer name.
	 * @param size
	 *            The layer size (in tiles).
	 */
	Layer(String name, Dimension size) {
		// Initialise variables
		this.m_sName = name;
		this.m_Size = size;

		// Initialise list
		this.m_PropertiesList = new ArrayList<Property>();
	}

	/**
	 * Sets the layer name.
	 * 
	 * @param name
	 *            The name.
	 */
	@Override
	public void setName(String name) {
		// Store value
		this.m_sName = name;
	}

	/**
	 * Gets the layer name.
	 */
	@Override
	public String getName() {
		// Return value
		return this.m_sName;
	}

	/**
	 * Sets the layer size.
	 * 
	 * @param size
	 *            The size.
	 */
	@Override
	public void setSize(Dimension size) {
		// Store value
		this.m_Size = size;
	}

	/**
	 * Gets the layer size.
	 */
	@Override
	public Dimension getSize() {
		// Return value
		return this.m_Size;
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