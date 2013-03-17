package uk.co.marshmallow_zombies.libtiledload.interfaces;

import java.awt.Dimension;

/**
 * Interface for all sizable objects.
 * 
 * @author Oliver Davenport
 */
public interface ISizable {

	/**
	 * Sets the size of this {@code ISizeable}.
	 * 
	 * @param size
	 *            The size to set.
	 */
	void setSize(Dimension size);

	/**
	 * Gets the size of this {@code ISizeable}.
	 */
	Dimension getSize();

};
