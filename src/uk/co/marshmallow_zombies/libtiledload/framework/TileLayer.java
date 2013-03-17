package uk.co.marshmallow_zombies.libtiledload.framework;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

/**
 * A tile layer.
 * 
 * @author Oliver Davenport
 */
public class TileLayer extends Layer {

	private List<Tile> m_TileList; // List of tiles

	/**
	 * Initialises a new {@code TileLayer}
	 * 
	 * @param name
	 *            The layer name.
	 * @param size
	 *            The layer size.
	 */
	TileLayer(String name, Dimension size) {
		// Call to super constructor
		super(name, size);

		// Initialise variables
		this.m_TileList = new ArrayList<Tile>();
	}

	/**
	 * Adds a tile to the layer.
	 * 
	 * @param tile
	 *            The {@code Tile} to add.
	 */
	public void addTile(Tile tile) {
		// Add the tile to the list
		this.m_TileList.add(tile);
	}

	/**
	 * Gets an array of all the {@code Tile}s on the map.
	 */
	public Tile[] getTiles() {
		// Clone the tile list
		List<Tile> tileList = this.m_TileList;

		// Get the size of the list
		int size = tileList.size();

		// Create a new array and go!
		Tile[] tiles = new Tile[size];

		// Convert to array
		tileList.toArray(tiles);

		// Return the array
		return tiles;
	}

};
