package uk.co.marshmallow_zombies.libtiledload.framework;

import java.awt.Rectangle;

/**
 * A rectangle object.
 * 
 * @author Oliver Davenport
 */
public class RectangleObject extends MapObject {

	private Rectangle m_Rectangle; // Object rectangle

	/**
	 * Initialises a new {@code RectangleObject}.
	 * 
	 * @param name
	 *            The object name.
	 * @param type
	 *            The object type.
	 * @param rectangle
	 *            The object rectangle.
	 */
	RectangleObject(String name, String type, Rectangle rectangle) {
		// Call to super constructor
		super(name, type, (int) rectangle.getX(), (int) rectangle.getY());

		// Store values
		this.m_Rectangle = rectangle;
	}

	/**
	 * Gets the object rectangle.
	 */
	public Rectangle getRectangle() {
		// Return value
		return this.m_Rectangle;
	}

};