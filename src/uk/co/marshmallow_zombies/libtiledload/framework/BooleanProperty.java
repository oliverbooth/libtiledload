package uk.co.marshmallow_zombies.libtiledload.framework;

/**
 * A boolean property belonging to a {@code Map}, {@code Layer}, {@code ObjectGroup} or
 * {@code MapObject}
 * 
 * @author Oliver Davenport
 */
public class BooleanProperty extends Property {

	private boolean m_bValue; // Property value

	/**
	 * Initialises a new {@code BooleanProperty} with the name and value specified.
	 * 
	 * @param name
	 *            The name of the property.
	 * 
	 * @param value
	 *            The value of the property.
	 */
	BooleanProperty(String name, boolean value) {
		// Call super constructor
		super(name);

		// Save value
		this.m_bValue = value;
	}

	/**
	 * Gets the value of the property.
	 */
	public boolean getValue() {
		// Return the value
		return this.m_bValue;
	}

};
