package uk.co.marshmallow_zombies.libtiledload.framework;

/**
 * A tile object.
 * 
 * @author Oliver Davenport
 */
public class TileObject extends MapObject {

	private int m_iGid;
	private byte[] m_DataArray;

	/**
	 * Initialises a new {@code EllipseObject}.
	 * 
	 * @param name
	 *            The object name.
	 * @param type
	 *            The object type.
	 * @param gid
	 *            The object gid.
	 * @param data
	 *            The object image data.
	 */
	TileObject(String name, String type, int x, int y, int gid, byte[] data) {
		// Call to super constructor
		super(name, type, x, y);

		// Store values
		this.m_iGid = gid;
		this.m_DataArray = data;
	}

	/**
	 * Gets the object GID.
	 */
	public int getGID() {
		// Return value
		return this.m_iGid;
	}

	/**
	 * Gets the object image data.
	 */
	public byte[] getData() {
		// Return value
		return this.m_DataArray;
	}

};