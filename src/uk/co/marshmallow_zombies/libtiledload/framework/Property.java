package uk.co.marshmallow_zombies.libtiledload.framework;

/**
 * A property belonging to a {@code Map}, {@code Layer}, {@code ObjectGroup} or
 * {@code MapObject}
 * 
 * @author Oliver Davenport
 */
public class Property {

	private String m_sName; // Property name

	/**
	 * Initialises a new {@code Property} with the name specified.
	 * 
	 * @param name
	 *            The name of the property.
	 */
	Property(String name) {
		// Store values
		this.m_sName = name;
	}

	/**
	 * Gets the name of the property.
	 */
	public String getName() {
		// Return the name
		return this.m_sName;
	}

};
