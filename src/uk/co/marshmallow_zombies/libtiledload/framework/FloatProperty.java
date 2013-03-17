package uk.co.marshmallow_zombies.libtiledload.framework;

/**
 * A floating point number property belonging to a {@code Map}, {@code Layer},
 * {@code ObjectGroup} or {@code MapObject}
 * 
 * @author Oliver Davenport
 */
public class FloatProperty extends Property {

	private float m_fValue; // Property value

	/**
	 * Initialises a new {@code FloatProperty} with the name and value
	 * specified.
	 * 
	 * @param name
	 *            The name of the property.
	 * 
	 * @param value
	 *            The value of the property.
	 */
	FloatProperty(String name, float value) {
		// Call super constructor
		super(name);

		// Save value
		this.m_fValue = value;
	}

	/**
	 * Gets the value of the property.
	 */
	public float getValue() {
		// Return the value
		return this.m_fValue;
	}

};
