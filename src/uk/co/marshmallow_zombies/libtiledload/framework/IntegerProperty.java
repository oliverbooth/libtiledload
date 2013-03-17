package uk.co.marshmallow_zombies.libtiledload.framework;

/**
 * An integer property belonging to a {@code Map}, {@code Layer},
 * {@code ObjectGroup} or {@code MapObject}
 * 
 * @author Oliver Davenport
 */
public class IntegerProperty extends Property {

	private int m_nValue; // Property value

	/**
	 * Initialises a new {@code IntegerProperty} with the name and value
	 * specified.
	 * 
	 * @param name
	 *            The name of the property.
	 * 
	 * @param value
	 *            The value of the property.
	 */
	IntegerProperty(String name, int value) {
		// Call super constructor
		super(name);

		// Save value
		this.m_nValue = value;
	}

	/**
	 * Gets the value of the property.
	 */
	public int getValue() {
		// Return the value
		return this.m_nValue;
	}

};
