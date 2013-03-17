package uk.co.marshmallow_zombies.libtiledload.interfaces;

/**
 * Interface for all nameable objects.
 * 
 * @author Oliver Davenport
 */
public interface INameable {

	/**
	 * Sets the object's name.
	 * 
	 * @param name
	 *            The name.
	 */
	void setName(String name);

	/**
	 * Gets the object's name.
	 */
	String getName();

};