package uk.co.marshmallow_zombies.libtiledload.framework;

import java.awt.geom.Ellipse2D;

/**
 * An ellipse object.
 * 
 * @author Oliver Davenport
 */
public class EllipseObject extends MapObject {

	private Ellipse2D m_Ellipse; // Object ellipse

	/**
	 * Initialises a new {@code EllipseObject}.
	 * 
	 * @param name
	 *            The object name.
	 * @param type
	 *            The object type.
	 * @param ellipse
	 *            The object ellipse.
	 */
	EllipseObject(String name, String type, Ellipse2D ellipse) {
		// Call to super constructor
		super(name, type, (int) ellipse.getX(), (int) ellipse.getY());

		// Store values
		this.m_Ellipse = ellipse;
	}

	/**
	 * Gets the object ellipse.
	 */
	public Ellipse2D getEllipse() {
		// Return value
		return this.m_Ellipse;
	}

};