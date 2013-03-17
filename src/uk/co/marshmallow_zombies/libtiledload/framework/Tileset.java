package uk.co.marshmallow_zombies.libtiledload.framework;

import java.awt.Dimension;

/**
 * Holds information about a tileset.
 * 
 * @author Oliver Davenport
 */
public class Tileset {

	private String m_sName; // Tileset name
	private int m_iFirstGID; // First GID
	private Dimension m_TileSize; // The size of each tile
	private byte[] m_bDataArray; // Byte data

	/**
	 * Initialises a new {@code Tileset}.
	 * 
	 * @param name
	 *            The name of the tileset.
	 * @param firstGid
	 *            The first GID in use by the tileset.
	 * @param tileSize
	 *            The dimension of each tile
	 * @param data
	 *            The tileset's image byte data.
	 */
	Tileset(String name, int firstGid, Dimension tileSize, byte[] data) {
		// Initialise values
		this.m_sName = name;
		this.m_iFirstGID = firstGid;
		this.m_TileSize = tileSize;
		this.m_bDataArray = data;
	}

	/**
	 * Get the byte array data for the tileset.
	 */
	public byte[] getData() {
		// Return byte data
		return this.m_bDataArray;
	}

	/**
	 * Gets the tileset's name.
	 */
	public String getName() {
		// Return name
		return this.m_sName;
	}

	/**
	 * Gets the tileset's first GID.
	 */
	public int getFirstGID() {
		// Return first GID
		return this.m_iFirstGID;
	}

	/**
	 * Gets the tile size.
	 */
	public Dimension getTileSize() {
		// Return tile size
		return this.m_TileSize;
	}

};
