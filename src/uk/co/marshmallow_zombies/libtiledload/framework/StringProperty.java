package uk.co.marshmallow_zombies.libtiledload.framework;

/**
 * A string property belonging to a {@code Map}, {@code Layer},
 * {@code ObjectGroup} or {@code MapObject}
 * 
 * @author Oliver Davenport
 */
public class StringProperty extends Property {

	private String m_sValue; // Property value

	/**
	 * Initialises a new {@code StringProperty} with the name and value
	 * specified.
	 * 
	 * @param name
	 *            The name of the property.
	 * 
	 * @param value
	 *            The value of the property.
	 */
	StringProperty(String name, String value) {
		// Call super constructor
		super(name);

		// Save value
		this.m_sValue = value;
	}

	/**
	 * Gets the value of the property.
	 */
	public String getValue() {
		// Return the value
		return this.m_sValue;
	}

};
