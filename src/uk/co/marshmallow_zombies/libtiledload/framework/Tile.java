package uk.co.marshmallow_zombies.libtiledload.framework;

/**
 * An individual tile.
 * 
 * @author Oliver Davenport
 */
public class Tile {

	private byte[] m_DataArray; // Image byte data
	private int m_nGID; // GID

	/**
	 * Initialises a new {@code Tile}.
	 * 
	 * @param gid
	 *            The tile GID.
	 * @param data
	 *            The tile image data.
	 */
	Tile(int gid, byte[] data) {
		this.m_nGID = gid;
		this.m_DataArray = data;
	}

	/**
	 * Gets the tile GID.
	 */
	public int getGID() {
		// Return GID
		return this.m_nGID;
	}

	/**
	 * Gets the image byte data for this tile.
	 */
	public byte[] getData() {
		// Return byte data
		return this.m_DataArray;
	}

	/**
	 * Represents an empty tile.
	 */
	public static Tile EMPTY = new Tile(-1, null); // Emptiness!

};
