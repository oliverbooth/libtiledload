package uk.co.marshmallow_zombies.libtiledload.framework;

import java.awt.Polygon;

/**
 * A polygon object.
 * 
 * @author Oliver Davenport
 */
public class PolygonObject extends MapObject {

	private Polygon m_Polygon;

	/**
	 * Initialises a new {@code PolygonObject}.
	 * 
	 * @param name
	 *            The object name.
	 * @param type
	 *            The object type.
	 * @param x
	 *            The object x-position.
	 * @param y
	 *            The object y-position.
	 * @param polygon
	 *            The object polygon.
	 */
	PolygonObject(String name, String type, int x, int y, Polygon polygon) {
		// Call to super constructor
		super(name, type, x, y);

		// Store values
		this.m_Polygon = polygon;
	}

	/**
	 * Gets the object polygon.
	 */
	public Polygon getPolygon() {
		// Return value
		return this.m_Polygon;
	}

};