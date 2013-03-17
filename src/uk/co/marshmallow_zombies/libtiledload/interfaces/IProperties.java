package uk.co.marshmallow_zombies.libtiledload.interfaces;

import uk.co.marshmallow_zombies.libtiledload.framework.Property;

/**
 * Interface for all property-holding objects.
 * 
 * @author Oliver Davenport
 */
public interface IProperties {

	/**
	 * Adds a property to the object.
	 * 
	 * @param property
	 *            The {@code Property} to add.
	 */
	void addProperty(Property property);

	/**
	 * Gets all the properties of that are contained in this object.
	 * 
	 * @return Returns an array of type {@code Property} containing all the
	 *         properties of the object.
	 */
	Property[] getProperties();

	/**
	 * Gets a property by its name.
	 * 
	 * @param name
	 *            The name of the property.
	 * @return Returns the first {@code Property} found whose name is
	 *         {@code name}.
	 */
	Property getPropertyByName(String name);

};
